import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import services.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class FbModule extends AbstractModule {

    @Override
    public void configure() {

        bind(AbstractComponent.class).to(FacebookComponent.class);

        bind(CacheComponent.class).asEagerSingleton();
    }

}
