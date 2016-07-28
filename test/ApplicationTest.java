import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.api.routing.Router;
import play.mvc.Http;
import play.mvc.Result;
import play.routing.RoutingDsl;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;
import services.DummyComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Results.redirect;
import static play.test.Helpers.*;


public class ApplicationTest extends WithApplication {

    private final String TOKEN = "EAACEdEose0cBAFmUrqmXYWQOYrewRYjDaVYbTd8JDbFd2euRszBiKpRaowe2YCsIybVzMXN34Id7MaxbCzm8HD6jW1tHWQXO2RaeR1vK0pEZCTRmMwJQA81ZACQVXa30wKKCYj6fLNg4cqipIIh2XXP1lqWHZB3s2g5EF75bAZDZD";
    private final String APP_URI = "http://localhost:9000";
    @Override
    protected FakeApplication provideFakeApplication() {
        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
                ImmutableMap.of("play.http.router", "router.Routes"), new ArrayList<String>(), null);
    }

    @Test
    public void testIndex() {
        Result result = route(controllers.routes.LoginController.index());
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType());
        assertEquals("utf-8", result.charset());
        assertTrue(contentAsString(result).contains("Facebook"));
    }

    @Test
    public void testSignin() {

        Result result = route(controllers.routes.LoginController.signin());
        assertEquals(SEE_OTHER, result.status());
        assertTrue((result.redirectLocation().contains("redirect_uri")));

        Http.RequestBuilder request = fakeRequest();
        request.uri(result.redirectLocation());

        Router router = new RoutingDsl()
                .GET("/dialog/oauth").routeTo(() -> {
                    return redirect(DummyComponent.AUTH_URL);
                })
                .build();

        Result result2 = routeAndCall(router, request);
        assertEquals(SEE_OTHER, result2.status());
        assertTrue((result2.redirectLocation().contains("callback?code=")));
    }

    @Test
    public void testLogout() {
        Result result = route(controllers.routes.LoginController.logout());
        assertEquals(SEE_OTHER, result.status());
        assertTrue((result.redirectLocation().endsWith("/")));
    }

    @Test
    public void testProfile() {
        Http.RequestBuilder request = fakeRequest();
        request.uri(APP_URI + "/profile?id=5454");
        request.session("access_token", "access_token=" + TOKEN);

        Result result = route(request);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType());
        assertEquals("utf-8", result.charset());
        assertTrue("token is expired",contentAsString(result).contains("Alexandra Schumm"));
    }

    @Test
    public void testSearch() {
        Http.RequestBuilder request = fakeRequest();
        request.uri(APP_URI + "/search");
        request.method("POST");
        request.session("access_token", "access_token=" + TOKEN);

        Map<String, String> form = new HashMap();
        form.put("value", "5454");
        request.bodyForm(form);

        Result result = route(request);
        assertEquals(SEE_OTHER, result.status());
        assertTrue((result.redirectLocation().contains("profile?id=5454")));
    }

}
