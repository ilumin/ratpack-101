package pocratpack;

import ratpack.exec.Promise;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;

public class App {

    public static void main(String... args) throws Exception {
        RatpackServer.start(serverSpec -> serverSpec
            .registry(Guice.registry(bindingsSpec -> bindingsSpec
                .module(HikariModule.class, hikariConfig -> {
                    hikariConfig.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
                    hikariConfig.addDataSourceProperty(
                        "URL",
                        "jdbc:h2:mem:todo;INIT=RUNSCRIPT FROM 'classpath:/init.sql'");
                })
                .module(TodoModule.class)
            ))
            .handlers(chain -> chain
                .all(new CORSHandler())
                .get(ctx -> {
                    TodoRepository repository = ctx.get(TodoRepository.class);
                    repository.getAll()
                        .map(Jackson::json)
                        .then(ctx::render);
                })
                .post(ctx -> {
                    TodoRepository repository = ctx.get(TodoRepository.class);
                    Promise<TodoModel> todo = ctx.parse(Jackson.fromJson(TodoModel.class));
                    todo.flatMap(repository::add)
                        .map(Jackson::json)
                        .then(ctx::render);
                })
            ));
    }

}
