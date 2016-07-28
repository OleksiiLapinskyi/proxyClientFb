package services;

import models.User;
import play.cache.CacheApi;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alapi on 7/24/2016.
 */
@Singleton
public class CacheComponent {

    private CacheApi usersCache;

    @Inject
    public CacheComponent(CacheApi usersCache){
        this.usersCache = usersCache;
    }

    public void saveUser(Http.Request request, User user){
        usersCache.set(request.uri(), user);
    }

    public User findUser(Http.Request request){
        return usersCache.get(request.uri());
    }

}
