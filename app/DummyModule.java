import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import services.*;

public class DummyModule extends AbstractModule {

    @Override
    public void configure() {

        bind(AbstractComponent.class).to(DummyComponent.class);
        bind(CacheComponent.class).asEagerSingleton();
    }

}
