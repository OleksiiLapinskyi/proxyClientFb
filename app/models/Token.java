package models;

/**
 * Created by alapi on 7/26/2016.
 */
public class Token {

    private String accessToken;
    private Integer expiration;

    public Token(String accessToken, Integer expiration){
        this.accessToken = accessToken;
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = accessToken;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}
