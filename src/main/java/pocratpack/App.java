package pocratpack;

import ratpack.exec.Promise;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.http.Response;
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
                .bindInstance(new CORSHandler())
            ))
            .handlers(chain -> chain
                .all(CORSHandler.class)
                .path(ctx -> {
                    TodoRepository repository = ctx.get(TodoRepository.class);
                    Response response = ctx.getResponse();
                    ctx.byMethod(byMethodSpec -> byMethodSpec
                        .options(() -> {
                            response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                            response.send();
                        })
                        .get(() -> {
                            repository.getAll()
                                .map(Jackson::json)
                                .then(ctx::render);
                        })
                        .post(() -> {
                            Promise<TodoModel> todo = ctx.parse(Jackson.fromJson(TodoModel.class));
                            todo.flatMap(repository::add)
                                .map(Jackson::json)
                                .then(ctx::render);
                        })
                        .delete(() -> repository.deleteAll().then(response::send))
                    );
                })
            ));
    }

}
