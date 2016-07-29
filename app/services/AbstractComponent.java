package services;


import models.Token;
import models.User;
import play.Application;
import play.api.Play;
import play.libs.F;
import play.mvc.Http;
import play.server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by alapi on 7/22/2016.
 */
public abstract class AbstractComponent {

    public static String APP_URI = "http://localhost:9000";

    public abstract F.Promise<User> getUser(Http.Request request, String userId);

    public abstract F.Promise<Token> accessToken(String code);

    public abstract String authentification();

    public abstract void setAccessToken(String token);

    public abstract CacheComponent getCacheComponent();

}
