
import models.Token;
import models.User;
import org.junit.Test;
import play.libs.F;
import play.mvc.Http;
import services.FacebookComponent;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeRequest;

public class UnitTest {

    @Test
    public void testAccessToken() {

        FacebookComponent fbComponent = mock(FacebookComponent.class);
        Token t = new Token("access_token=EAACEdEose0cBAFmUrqmXYWQOYrewRYjDaVYbTd8JDbFd2euRszBiKpRaowe2YCsIybVzMXN34Id7MaxbCzm8HD6jW1tHWQXO2RaeR1vK0pEZCTRmMwJQA81ZACQVXa30wKKCYj6fLNg4cqipIIh2XXP1lqWHZB3s2g5EF75bAZDZD", 123);
        when(fbComponent.accessToken(anyString())).thenReturn(F.Promise.promise(() -> t));

        // Test
        F.Promise<Token> promise = fbComponent.accessToken("sample_code");
        promise.map(result -> {
            assertEquals(result.getExpiration().longValue(), 123);
            verify(fbComponent).accessToken("sample_code");
            return result;
        });
    }

    @Test
    public void testGetUser() {

        FacebookComponent fbComponent = mock(FacebookComponent.class);
        User user = new User();
        user.setId("12345");
        user.setName("Test User");
        when(fbComponent.getUser(any(Http.Request.class),anyString())).thenReturn(F.Promise.promise(() -> user));

        // Test
        F.Promise<User> promise = fbComponent.getUser(fakeRequest().build(),"12345");
        promise.map(result -> {
            assertEquals(result.getName(), "Test User");
            verify(fbComponent).getUser(fakeRequest().build(),"12345");
            return result;
        });
    }
}
