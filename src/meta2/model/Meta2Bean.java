package meta2.model;

import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;


import rmiserver.RMIServerINT;
import rmiserver.Config;


public class Meta2Bean {
    private RMIServerINT rmiserver;
    private int cc;
    private String password;

    public Meta2Bean(){
        try {
            rmiserver = (RMIServerINT) Naming.lookup("rmi://localhost:7000/iVotas");
        }
        catch(NotBoundException|MalformedURLException|RemoteException e) {
            e.printStackTrace();
        }
    }

    public RMIServerINT getRmiserver() {
        return rmiserver;
    }

    public void setRmiserver(RMIServerINT rmiserver) {
        this.rmiserver = rmiserver;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getLogin() throws RemoteException {
        return rmiserver.verificaLogin(this.cc,this.password);
    }
}
