package services.facebook;

import models.Token;
import play.libs.ws.WSResponse;
import services.AbstractComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by alapi on 7/23/2016.
 */
public class FbConnection {
    private final String FB_APP_ID = "897579820347543";
    private final String FB_APP_SECRET = "55ee1209b6561e96f3ba96c0081e48e7";
    private final String REDIRECT_URI = AbstractComponent.APP_URI + "/callback";
    private final String PERMISSIONS = "public_profile";
    private final String FIELDS = "id,name,picture,link";

    private String accessToken;

    public void setAccessToken(String token){
        accessToken = token;
    }

    public String getFBAuthUrl() {
        String fbLoginUrl = "";
        try {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?client_id="
                    + FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                    + "&scope=" + PERMISSIONS;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbLoginUrl;
    }

    public String getAccessTokenUrl(String code){
        String tokenUrl = "";
        try {
            tokenUrl = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                    + "&client_secret=" + FB_APP_SECRET + "&code=" + code;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tokenUrl;
    }

    public String getFBGraphUrl(String userId){
        if(accessToken != null) {
            return "https://graph.facebook.com/" + userId + "?fields=" + FIELDS + "&" + accessToken;
        } else {
            throw new NullPointerException();
        }
    }



    public static Token convertAccessToken(WSResponse response) {
        StringBuffer b = null;
        try {
            BufferedReader  in = new BufferedReader(new InputStreamReader(response.getBodyAsStream()));
            String inputLine;
            b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine + "\n");
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect with Facebook "
                    + e);
        }

        String accessToken = b.toString();
        if (accessToken.startsWith("{")) {
            throw new RuntimeException("ERROR: Access Token Invalid: "
                    + accessToken);
        } else {
            return stringToToken(accessToken);
        }
    }

    public static Token stringToToken(String accessToken){
        try {
            String token = accessToken.substring(0, accessToken.indexOf("&"));
            Integer expiration = Integer.parseInt(accessToken.substring(accessToken.lastIndexOf("=") + 1, accessToken.length()-1));
            return new Token(token, expiration);
        }catch (StringIndexOutOfBoundsException e){
            return null;
        }
    }

}
