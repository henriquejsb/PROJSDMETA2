/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.Remote;

/**
 *
 * @author hb
 */
public interface ConsolaINT extends Remote{
    public void notifica(String mess) throws java.rmi.RemoteException;
    public void notificaLive(String mess) throws java.rmi.RemoteException;
}
