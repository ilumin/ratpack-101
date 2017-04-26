package pocratpack;

import jooq.tables.Todo;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String... args) throws Exception {
        RatpackServer.start(serverSpec -> serverSpec
            .registry(Guice.registry(bindingsSpec -> bindingsSpec
                .module(HikariModule.class, hikariConfig -> {
                    hikariConfig.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
                    hikariConfig.addDataSourceProperty(
                        "URL",
                        "jdbc:h2:mem:todo;INIT=RUNSCRIPT FROM 'classpath:/init.sql'");
                }))
            )
            .handlers(chain -> chain
                .all(new CORSHandler())
                .get(ctx -> {
                    DataSource ds = ctx.get(DataSource.class);
                    DSLContext create = DSL.using(ds, SQLDialect.H2);
                    List<Map<String, Object>> maps = create.select().from(Todo.TODO).fetch().intoMaps();
                    ctx.render(Jackson.json(maps));
                })
                .get("foo", ctx -> ctx.render("foo bar"))
            ));
    }

}
