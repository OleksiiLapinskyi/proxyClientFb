package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.Token;
import models.User;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Http;
import services.facebook.FbConnection;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alapi on 7/22/2016.
 */
@Singleton
public class FacebookComponent extends AbstractComponent {

    public final FbConnection fbConnection = new FbConnection();

    private static final int TIMEOUT = 5000;

    private WSClient ws;

    @Inject
    public FacebookComponent(WSClient ws){
        this.ws = ws;
    }

    @Override
    public String authentification(){
        return fbConnection.getFBAuthUrl();
    }

    @Override
    public Promise<Token> accessToken(String code){
        String url = fbConnection.getAccessTokenUrl(code);
        if(url != null) {
            WSRequest req = ws.url(url).setRequestTimeout(TIMEOUT);
            return req.get().map( response -> {
                Token token = null;
                if(response.getStatus() == Http.Status.OK){
                    token = FbConnection.convertAccessToken(response);
                }
                return token;
            });
        }
        return null;
    }

    @Override
    public Promise<User> getUser(Http.Request request, String userId) {
        String url = "";
        try {
            url = fbConnection.getFBGraphUrl(userId);
        } catch (NullPointerException e){
            url = authentification();
        } finally {
            WSRequest req = WS.url(url).setRequestTimeout(TIMEOUT);
            return req.get().map(response -> {
                JsonNode json = response.asJson();
                User user = null;
                try {
                    user = Json.fromJson(json, User.class);
                } catch (RuntimeException e){

                }
                return user;
            });
        }
    }

    public void setAccessToken(String token){
        fbConnection.setAccessToken(token);
    }

    @Override
    public CacheComponent getCacheComponent() {
        return null;
    }
}
