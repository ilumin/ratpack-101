package pocratpack;

import ratpack.http.MutableHeaders;
import ratpack.server.RatpackServer;

public class App {

    public static void main(String... args) throws Exception {
        RatpackServer.start(serverSpec -> serverSpec
                .handlers(chain -> chain
                        .get(ctx -> {
                            MutableHeaders headers = ctx.getResponse().getHeaders();
                            headers.set("Access-Control-Allow-Origin", "*");
                            headers.set("Access-Control-Allow-Headers", "x-request-with, origin, content-type, accept");
                            ctx.getResponse().send();
                        })
                ));
    }

}
