package controllers;

import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import services.AbstractComponent;
import services.CacheComponent;
import views.html.profile;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


@Singleton
public class UserController extends Controller {

    private AbstractComponent httpComponent;
    private CacheComponent cacheComponent;

    @Inject
    public UserController(AbstractComponent httpComponent){
        this.httpComponent = httpComponent;
    }

    public Promise<Result> userProfile(String userId){
        AbstractComponent.APP_URI = "http://" + request().host();
        String accessToken = session().get("access_token");
        if (accessToken == null) {
            return Promise.pure(redirect(routes.LoginController.index().absoluteURL(request())));
        } else {
            httpComponent.setAccessToken(accessToken);
        }
        cacheComponent = httpComponent.getCacheComponent();

        Promise<User> promise = Promise.promise( () -> {
            User user = null;
            if(cacheComponent != null) {
                user = cacheComponent.findUser(request());
            }
            return user;
        });
        return promise.flatMap(user -> {
            if(user == null){
                return loadProfile(userId);
            } else {
                return Promise.pure(ok(profile.render("", user)));
            }
        });

    }

    private Promise<Result> loadProfile(String userId){
        Promise<User> promise = httpComponent.getUser(request(),userId);
        return promise.flatMap( user -> {
            if(user != null){
                if(cacheComponent != null) {
                    cacheComponent.saveUser(request(), user);
                }
                return Promise.pure(ok(profile.render("", user)));
            }
            return Promise.pure(ok(profile.render("User not found", null)));
        });
    }

    public Result search(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String id = dynamicForm.get("value");
        return redirect(routes.UserController.userProfile(id));
    }

}
