package pocratpack;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.sql.DataSource;

public class TodoModule extends AbstractModule {
    @Override
    protected void configure() {}

    @Provides
    @Singleton
    TodoRepository todoRepository(DataSource ds) {
        return new TodoRepository(ds);
    }
}
