package meta2;

import rmiserver.RMIServerINT;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class WebSocketAnnotation implements Serializable, WebSocketINT {
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private String username;
    private RMIServerINT server;
    private Session session;
    private WebSocketConnection aux;
    private WebSocketAnnotation web;
    private static final Set<WebSocketAnnotation> users = new CopyOnWriteArraySet<>();

    public WebSocketAnnotation() {}

    public WebSocketAnnotation(WebSocketConnection a) {
        this.aux = a;
    }

    public WebSocketAnnotation(String a,Session s) {
        this.username = a;
        this.session = s;
    }

    @OnOpen
    public void start(Session session) throws RemoteException{
        System.out.println("At start of WebSocketAnnotation");


        this.session = session;

        try {
            this.server = (RMIServerINT) Naming.lookup("rmi://localhost:7000/iVotas");
            this.users.add(this);

            aux = new WebSocketConnection(this);
            this.server.subscribeWeb((WebSocketINT)aux);
        }
        catch(NotBoundException |MalformedURLException |RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void end() throws RemoteException{
        System.out.println("WebSocketAnnotation end()");
        // clean up once the WebSocket connection is closed
        this.server.unsubscribeWeb(aux);
        users.remove(this);
    }

    @OnMessage
    public void receiveMessage(String message) {
        // one should never trust the client, and sensitive HTML
        // characters should be replaced with &lt; &gt; &quot; &amp;
        String upperCaseMessage = message.toUpperCase();
        sendMessage("[" + username + "] " + upperCaseMessage);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    public void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        try {
            this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean print_on_websocket(String m) throws RemoteException{
        System.out.println("Entrei em print_on_websocket() WebSocketAnnotation");
        try {
            for(WebSocketAnnotation user:users){
                System.out.println("Iterating users...");
                    user.session.getBasicRemote().sendText(m);

            }
        } catch (IOException e) {
            // clean up once the WebSocket connection is closed
            try {
                this.session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }


}
