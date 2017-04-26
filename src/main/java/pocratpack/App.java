package pocratpack;

import ratpack.server.RatpackServer;

public class App {

    public static void main(String... args) throws Exception {
        RatpackServer.start(serverSpec -> serverSpec
                .handlers(chain -> chain
                        .all(new CORSHandler())
                        .get(ctx -> ctx.render("Dee Jaa"))
                        .get("foo", ctx -> ctx.render("foo bar"))
                ));
    }

}
