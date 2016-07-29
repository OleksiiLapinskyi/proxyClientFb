import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import services.*;

public class CacheModule extends AbstractModule {

    @Override
    public void configure() {

        bind(AbstractComponent.class).to(HttpCacheComponent.class);
        bind(CacheComponent.class).asEagerSingleton();

    }

}
