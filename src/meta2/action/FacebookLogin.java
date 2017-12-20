package meta2.action;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Scanner;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import uc.sd.apis.FacebookApi2;

public class FacebookLogin extends ActionSupport implements SessionAware
{
    private static final long serialVersionUID = 4L;
    private  String authorizationUrl;
    private Map<String, Object> session;
    private static final String NETWORK_NAME = "Facebook";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    private static final Token EMPTY_TOKEN = null;
    private String code;
    OAuthService service;

    public String execute() throws RemoteException {
        System.out.println( "Execute!!!");
        // Replace these with your own api key and secret
        String apiKey = "403906196694892";
        String apiSecret = "8f9cc5be57ef69be7925472215d8e0cb";


       service =  new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback("http://localhost:8080/FBAction") // Do not change this.
                .scope("publish_actions")
                .build();
        Scanner in = new Scanner(System.in);

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize Scribe here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
       return "redirect";
    }

    public String execute1(){
        System.out.println(this.code);
        String code1 = this.code+"#_=_";
        System.out.println("--"+code1 );
        Verifier verifier = new Verifier(code1);
        System.out.println(verifier.getValue());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());

        return SUCCESS;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

}