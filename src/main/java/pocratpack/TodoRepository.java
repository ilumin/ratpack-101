package pocratpack;

import jooq.tables.Todo;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;

import javax.sql.DataSource;
import java.util.List;

public class TodoRepository {
    private final DSLContext create;

    public TodoRepository(DataSource ds) {
        this.create = DSL.using(ds, SQLDialect.H2);
    }

    public Promise<List<TodoModel>> getAll() {
        SelectJoinStep all = create.select().from(Todo.TODO);
        return Blocking.get(() -> all.fetchInto(TodoModel.class));
    }
}
