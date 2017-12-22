package meta2;

import javax.websocket.Session;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class WebSocketConnection extends UnicastRemoteObject implements WebSocketINT {

    private WebSocketAnnotation web;
    public WebSocketConnection() throws RemoteException {
        super();
    }
    public WebSocketConnection(WebSocketAnnotation w) throws RemoteException {
        super();
        this.web = w;
    }

    private Session session;
    private String username;


    public boolean print_on_websocket(String m) throws RemoteException {
        System.out.println("Entrei em print_on_we");
        try {
            this.web.print_on_websocket(m);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public WebSocketAnnotation getWeb() {
        return web;
    }

    public void setWeb(WebSocketAnnotation web) {
        this.web = web;
    }
}
