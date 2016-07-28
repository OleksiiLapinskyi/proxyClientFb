import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import services.*;

public class Module extends AbstractModule {

    @Override
    public void configure() {

        bind(AbstractComponent.class).annotatedWith(Names.named("httpCache")).to(HttpCacheComponent.class);
        bind(AbstractComponent.class).annotatedWith(Names.named("fb")).to(FacebookComponent.class);
        bind(AbstractComponent.class).annotatedWith(Names.named("dummy")).to(DummyComponent.class);

        bind(CacheComponent.class).asEagerSingleton();
    }

}
