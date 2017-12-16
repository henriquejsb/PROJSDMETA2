/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

/**
 *
 * @author Filipe Good
 */
import java.io.IOException;

public class Client {
    public static void main(String args[])  throws IOException {
	// args[0] <- hostname of destination
        
	if (args.length != 2) {
	    System.out.println("Argumentos: hostname port");
	    System.exit(0);
	}
        String host = args[0];
        int port = 6000;
        try{
            port = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            System.out.println("Argumentos: hostname port");
            System.exit(0);
        }
        TcpClient client = new TcpClient(host,port);
        
        /*
	int serversocket = 6000;
	TcpClient client = new TcpClient("localhost",serversocket);
	*/
}}



