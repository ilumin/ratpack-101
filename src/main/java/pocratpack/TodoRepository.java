package pocratpack;

import jooq.tables.Todo;
import jooq.tables.records.TodoRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import ratpack.exec.Blocking;
import ratpack.exec.Operation;
import ratpack.exec.Promise;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class TodoRepository {
    private final DSLContext create;

    public TodoRepository(DataSource ds) {
        this.create = DSL.using(ds, SQLDialect.H2);
    }

    public Promise<List<TodoModel>> getAll() {
        SelectJoinStep all = create.select().from(Todo.TODO);
        return Blocking.get(() -> all.fetchInto(TodoModel.class));
    }

    public Promise<TodoModel> getById(Long id) {
        SelectConditionStep where = create.select().from(Todo.TODO).where(Todo.TODO.ID.eq(id));
        return Blocking.get(() -> where.fetchOne().into(TodoModel.class));
    }

    public Promise<TodoModel> add(TodoModel todo) {
        TodoRecord record = create.newRecord(Todo.TODO, todo);
        return Blocking.op(record::store)
                .next(Blocking.op(record::refresh))
                .map(() -> record.into(TodoModel.class));
    }

    public Promise<TodoModel> update(Map<String, Object> todo) {
        TodoRecord record = create.newRecord(Todo.TODO, todo);
        return Blocking.op(() -> create.executeUpdate(record))
                .next(Blocking.op(record::refresh))
                .map(() -> record.into(TodoModel.class));
    }

    public Operation delete(Long id) {
        DeleteConditionStep<TodoRecord> deleteWhereId = create.deleteFrom(Todo.TODO).where(Todo.TODO.ID.eq(id));
        return Blocking.op(deleteWhereId::execute);
    }

    public Operation deleteAll() {
        DeleteWhereStep<TodoRecord> delete = create.deleteFrom(Todo.TODO);
        return Blocking.op(delete::execute);
    }
}
