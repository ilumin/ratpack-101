package pocratpack;

import ratpack.server.RatpackServer;

public class HelloWorld {
    public static void main(String... args) throws Exception {
        RatpackServer.start(serverSpec -> serverSpec
                .handlers(chain -> chain
                        .get(ctx -> ctx.render("Dee Jaa"))
                ));
    }
}
