package meta2;

import java.rmi.Remote;

public interface WebSocketINT extends Remote {
    public boolean print_on_websocket(String m) throws java.rmi.RemoteException;
}
