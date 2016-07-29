package controllers;

import models.Token;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import services.AbstractComponent;
import views.html.signin;

import javax.inject.Inject;
import javax.inject.Named;

public class LoginController extends Controller {

    private AbstractComponent httpComponent;

    private static final String ACCESS_TOKEN = "access_token";

    @Inject
    public LoginController(AbstractComponent httpComponent){
        this.httpComponent = httpComponent;
    }

    public Result index() {
        AbstractComponent.APP_URI = "http://" + request().host();
        if(request().cookie(ACCESS_TOKEN) != null){
            String accessToken = request().cookie(ACCESS_TOKEN).value();
            httpComponent.setAccessToken(accessToken);
            session(ACCESS_TOKEN, accessToken);

            return redirect(routes.UserController.userProfile("me"));
        } else {
            return ok(signin.render("Proxy client for Facebook"));
        }
    }

    public Result signin(){
        return redirect(httpComponent.authentification());
    }

    public Result logout(){
        session().remove(ACCESS_TOKEN);
        response().discardCookie(ACCESS_TOKEN);
        return redirect(routes.LoginController.index());
    }

    public Promise<Result> callback(String code){
        if(code == null ){
            return Promise.pure(redirect(routes.LoginController.index()));
        }

        Promise<Token> promise = httpComponent.accessToken(code);
        return promise.map(token -> {
            if(token != null){
                try {
                    session(ACCESS_TOKEN, token.getAccessToken());
                    response().setCookie(ACCESS_TOKEN, token.getAccessToken(), token.getExpiration(), "/", "localhost", false, true);
                    httpComponent.setAccessToken(token.getAccessToken());
                } catch (RuntimeException e){
                    return badRequest(signin.render("Error! Incorrect authorization"));
                }
                return redirect(routes.UserController.userProfile("me"));
            } else {
                return redirect(routes.LoginController.index());
            }
        });
    }

}
