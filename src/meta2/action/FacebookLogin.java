package meta2.action;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class FacebookLogin extends ActionSupport implements SessionAware
{
    private static final long serialVersionUID = 4L;
    private  String authorizationUrl;
    private Map<String, Object> session;
    private static final String NETWORK_NAME = "Facebook";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    private static final Token EMPTY_TOKEN = null;
    private String code,eleicao;
    OAuth20Service service;


    public String postFacebook() throws Exception {
        this.eleicao = this.getMeta2Bean().getEleicao();
        System.out.println("[POST FACEBOOK]");
        String id = (String ) session.get("fbid");
        String accessTokenString = (String)  session.get("accessTokenString");
        OAuth2AccessToken accessToken = (OAuth2AccessToken) session.get("accessToken");
        if(id == null){
            System.out.println("Não está nenhuma conta de facebook associada!");
            return NONE;
        }else if(accessToken==null || accessTokenString==null){
            System.out.println("Erro a postar no facebook");
            return NONE;
        }
        OAuth20Service service1 = (OAuth20Service) session.get("service");
        //String message = "vote";
        String message = "VOTEI http://localhost:8080/eleicoes?eleicao="+this.eleicao;
        System.out.println("msg-"+message);
        message= message.replace(" ","%20");
        String postit = "https://graph.facebook.com/"+id+"/feed?message="+message+"&access_token="+accessTokenString;
        System.out.println(postit);
        OAuthRequest post = new OAuthRequest(Verb.POST,postit);
        System.out.println(post);
        service1.signRequest(accessToken, post);
        final Response response2 = service1.execute(post);
        System.out.println("Post partilhado!");
        return SUCCESS;
    }


    public String executeAddLogin() throws RemoteException{
        //Quando é para associar a conta tipo = 0
        System.out.println("Adicionar conta");
        session.put("fb",false);
        String aux = login();
        return "redirect";
    }

    private String login(){
        String apiKey = "403906196694892";
        String apiSecret = "8f9cc5be57ef69be7925472215d8e0cb";
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(apiKey)
                .apiSecret(apiSecret)
                .state(secretState)
                .scope("publish_actions")
                .callback("http://localhost:8080/FBAction")
                .build(FacebookApi.instance());
        authorizationUrl = service.getAuthorizationUrl();
        System.out.println(authorizationUrl);
        session.put("url",authorizationUrl);
        session.put("service",service);

        return "redirect";
    }

    public String executeFbLogin() throws RemoteException {
        //Quando é para login o fb=true
        //fb a true quando é para o login
        session.put("fb",true);
        System.out.println("Login com Facebook");
        String aux = login();
        return "redirect";
    }

    public String execute1(){
        service = (OAuth20Service) session.get("service");
        // Trade the Request Token and Verfier for the Access Token
        System.out.println(this.code);
        OAuth2AccessToken accessToken = null;
        try {
            accessToken = service.getAccessToken(code);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        JSONObject tokena = (JSONObject) JSONValue.parse(accessToken.getRawResponse());
        String accessTockenString = (String) tokena.get("access_token");
        session.put("accessTokenString", accessTockenString);
        session.put("accessToken",accessToken);


        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = null;
        try {
            response = service.execute(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject tokenID = null;
        try {
            tokenID = (JSONObject) JSONValue.parse(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }


        String idString=(String) tokenID.get("id");
        boolean tipo = (boolean) session.get("fb");
        session.put("fbid",idString);
        this.getMeta2Bean().setFacebookId(idString);
        System.out.println("->>"+this.getMeta2Bean().getFacebookId());

        if(tipo == false){
            boolean admin = (boolean) session.get("admin");
            System.out.println("Admin="+admin);
            if(this.getMeta2Bean().setFacebookId()){
                System.out.println("Adicionou com sucesso!");
                if(admin){
                    System.out.println("Admin ");
                    return "addAd";
                }else{
                    System.out.println("Normal");
                    return "add";
                }

            }else{
                if(admin){
                    return "noAd";
                }else{
                    return "no";
                }

            }
        }else if(tipo){
            String res = null;
            try {
                res = this.getMeta2Bean().getLoginFb();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println(res);
            if(res.equals("FALSE")){
                return LOGIN;
            }
            String[] result = res.split("-");
            int cc = Integer.parseInt(result[1]);
            if(result[0].equals("ADMIN")) {
                System.out.println("admin");
                System.out.println("Setting user "+cc);
                this.getMeta2Bean().setUser(cc);
                session.put("loggedin", true); // this marks the user as logged in
                session.put("admin", true);
                return "ADMIN";
            }
            else if(result[0].equals("PESSOA")){
                System.out.println("pessoa");
                this.getMeta2Bean().setUser(cc);
                session.put("loggedin", true); // this marks the user as logged in
                session.put("admin", false);
                System.out.println("Setting user "+cc);
                return "PESSOA";
            }
            else{
                return LOGIN;
            }


        }


        return NONE;
    }

    public String desassociarFb() throws Exception {
        String faceId = (String) session.get("fbid");
        this.getMeta2Bean().setFacebookId(faceId);
        if(this.getMeta2Bean().desassociarFb()){
            System.out.println("Conta desassociada!");
            return SUCCESS;
        }else{
            return NONE;
        }
    }

    public String getEleicao() {
        return eleicao;
    }

    public void setEleicao(String eleicao) {
        this.eleicao = eleicao;
    }

    public static String getNetworkName() {
        return NETWORK_NAME;
    }

    public static String getProtectedResourceUrl() {
        return PROTECTED_RESOURCE_URL;
    }

    public static Token getEmptyToken() {
        return EMPTY_TOKEN;
    }


    public OAuth20Service getService() {
        return service;
    }

    public void setService(OAuth20Service service) {
        this.service = service;
    }

    public Meta2Bean getMeta2Bean() {
        if(!session.containsKey("meta2Bean"))
            this.setMeta2Bean(new Meta2Bean());
        return (Meta2Bean) session.get("meta2Bean");
    }

    public void setMeta2Bean(Meta2Bean bean) {
        this.session.put("meta2Bean", bean);
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