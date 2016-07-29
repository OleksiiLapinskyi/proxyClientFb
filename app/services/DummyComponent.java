package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.Token;
import models.User;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Http;
import services.facebook.FbConnection;

/**
 * Created by alapi on 7/27/2016.
 */
public class DummyComponent extends AbstractComponent {

    //public static final String AUTH_URL = "http://localhost:9000/callback?code=AQDNI2_YLgavmuyXsqrTc6I7vHfe2wwrmHEsLzlXSrYjN7v2v162L0HZTHEnCgjk0ykVigxyd7kQ1iiOLvWlrzYbsFYyEK81gw6S2QqhtLXmXK2Lj2PRSRUQp0NVHu7Kzd_Fe0lXWumKVf9DH_l5TttD8RfSTZlAGUDyafbg7BBdipLG6pS9NBEdCuTzkdXzV793_ssfMgXzXsid3PxJKR-XibB-sdoMyvofH98K_LSsizb3YZZ2e9t1kt5be2lzcxey51-HBS6zg_uku1ke7JhllNwCMp4cCoIqSfcnQzvftMVAs7CXpvbrLpKv8MkGSUbvqWQ4SNYsVI3JGLApXJRx";
    public static final String AUTH_URL = "/callback?code=AQDNI2_YLgavmuyXsqrTc6I7vHfe2wwrmHEsLzlXSrYjN7v2v162L0HZTHEnCgjk0ykVigxyd7kQ1iiOLvWlrzYbsFYyEK81gw6S2QqhtLXmXK2Lj2PRSRUQp0NVHu7Kzd_Fe0lXWumKVf9DH_l5TttD8RfSTZlAGUDyafbg7BBdipLG6pS9NBEdCuTzkdXzV793_ssfMgXzXsid3PxJKR-XibB-sdoMyvofH98K_LSsizb3YZZ2e9t1kt5be2lzcxey51-HBS6zg_uku1ke7JhllNwCMp4cCoIqSfcnQzvftMVAs7CXpvbrLpKv8MkGSUbvqWQ4SNYsVI3JGLApXJRx";

    public static final String ACCESS_TOKEN = "access_token=EAAMwWBZAZAcJcBAG8isAihqF1JKXoEqcQL9dvKEFXf1mduF2t0NBcZAjZB54hBeFsSwO9btsCDZBquYVZBCFpKU9tdOrOGkizYxeGO7kFn9nXkCjnSuKCZBBafHtRokDAZCaEILCyKM1ZAcLBWFmgJ6d4oD12ptrRl8VmSU28dE6PQAZDZD&expires=5107003\n";

    public static final String USER_ME = "{\"id\":\"1144895972238523\",\"name\":\"Алексей Лапинский\",\"picture\":{\"data\":{\"is_silhouette\":true,\"url\":\"https://scontent.xx.fbcdn.net/v/t1.0-1/c15.0.50.50/p50x50/10354686_10150004552801856_220367501106153455_n.jpg?oh=5c43cf5cfa35da8de30688b57a56d839&oe=5824062F\"}},\"link\":\"https://www.facebook.com/app_scoped_user_id/1144895972238523/\"}";

    @Override
    public Promise<User> getUser(Http.Request request, String userId) {
        JsonNode json = Json.parse(USER_ME);
        return Promise.pure(Json.fromJson(json, User.class));
    }

    @Override
    public Promise<Token> accessToken(String code) {
        return Promise.pure(FbConnection.stringToToken(ACCESS_TOKEN));
    }

    @Override
    public String authentification() {
        return APP_URI + AUTH_URL;
    }

    @Override
    public void setAccessToken(String token) {

    }

    @Override
    public CacheComponent getCacheComponent() {
        return null;
    }
}
