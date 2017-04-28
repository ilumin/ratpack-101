package pocratpack;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import ratpack.func.Function;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;
import ratpack.jackson.Jackson;
import ratpack.jackson.JsonRender;

import java.util.Map;

public class Todohandler extends InjectionHandler {
    public void handle(Context ctx, TodoRepository repository, String base) throws Exception {
        Long todoId = Long.parseLong(ctx.getPathTokens().get("id"));

        Function<TodoModel, TodoModel> hostUpdater = todo -> todo.baseUrl(base);
        Function<TodoModel, JsonRender> toJson = hostUpdater.andThen(Jackson::json);

        Response response = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
            .options(() -> {
                response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, PATCH, DELETE");
                response.send();
            })
            .get(() -> repository.getById(todoId).map(toJson).then(ctx::render))
            .patch(() -> ctx
                .parse(Jackson.fromJson(new TypeToken<Map<String, Object>>() {}))
                .map(map -> {
                    Map<String, Object> m = Maps.newHashMap();
                    map.keySet().forEach(key -> m.put(key.toUpperCase(), map.get(key)));
                    m.put("ID", todoId);
                    return m;
                })
                .flatMap(repository::update)
                .map(toJson)
                .then(ctx::render)
            )
            .delete(() -> repository.delete(todoId).then(response::send))
        );
    }
}
