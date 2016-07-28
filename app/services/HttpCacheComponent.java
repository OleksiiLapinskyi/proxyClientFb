package services;

import models.User;
import play.libs.F.Promise;
import play.libs.ws.WSClient;
import play.mvc.Http;

import javax.inject.Inject;

/**
 * Created by alapi on 7/27/2016.
 */
public class HttpCacheComponent extends FacebookComponent{

    private CacheComponent cacheComponent;

    @Inject
    public HttpCacheComponent(WSClient ws, CacheComponent cacheComponent) {
        super(ws);
        this.cacheComponent = cacheComponent;
    }

    @Override
    public Promise<User> getUser(Http.Request request, String userId){
        if(cacheComponent.findUser(request) != null){
            Promise<User> promise = Promise.promise(() -> cacheComponent.findUser(request));
            return promise.map(user -> user);
        } else {
            return super.getUser(request, userId);
        }
    }

    @Override
    public CacheComponent getCacheComponent() {
        return cacheComponent;
    }
}
