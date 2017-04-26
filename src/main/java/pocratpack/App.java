package pocratpack;

import ratpack.http.MutableHeaders;
import ratpack.server.RatpackServer;

public class App {

    public static void main(String... args) throws Exception {
        RatpackServer.start(serverSpec -> serverSpec
                .handlers(chain -> chain
                        .all(ctx -> {
                            MutableHeaders headers = ctx.getResponse().getHeaders();
                            headers.set("Access-Control-Allow-Origin", "*");
                            headers.set("Access-Control-Allow-Headers", "x-request-with, origin, content-type, accept");
                            ctx.next();
                        })
                        .get(ctx -> ctx.render("Dee Jaa"))
                        .get("foo", ctx -> ctx.render("foo bar"))
                ));
    }

}
