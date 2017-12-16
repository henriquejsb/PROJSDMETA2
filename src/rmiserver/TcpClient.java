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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author Filipe Good
 */
public class TcpClient {

    private Socket s;
    private InputStreamReader input;
    private InetAddress hostname;
    private int serversocket;
    private int contador;
    public static String tipoGeral;
    public static String strListas;
    public static boolean writeToServer;
    public static boolean loginSuccess;
    PrintWriter outToServer;
    private Thread thread;
    private Envia_Thread threadEnvia;
    BufferedReader inFromServer = null;
    public static String [] arrayListas;
    public TcpClient(){
        
    }
    public TcpClient(String hostname, int serversocket) throws IOException {
        try {
            this.hostname = InetAddress.getByName(hostname);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.serversocket = serversocket;

        connectClient();

    }

    public void connectClient() throws IOException {
        //Método que se conecta com o server
        try {
            this.s = new Socket(this.hostname, this.serversocket);
            System.out.println("SOCKET=" + s);
            this.input = new InputStreamReader(System.in);
            inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outToServer = new PrintWriter(s.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
            return;
        }catch(SocketException e){
            System.out.println("Erro ao tentar ligar ao servidor!:"+e);
            return;
        }
        esperaDesbloquear();

    }

    public void esperaDesbloquear() {
        //Método que fica à escuta da mensagem para desbloquear
        String acesso = null;
        System.out.println("À espera de eleitores...");
        try {
            acesso = inFromServer.readLine();
        } catch (IOException ex) {
            System.out.println("Erro ao ler acesso do server TCP: " + ex);
            Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] words = acesso.split(";");
        String[] type = words[0].split(" | ");
        if (type[2].equals("unlocked")) {
            String[] msg = words[1].split(" | ");
            if (msg[3].equals("true")) {
                System.out.println("******ACESSO DESBLOQUEADO*******: ");
                this.threadEnvia = new Envia_Thread(this.outToServer);
                this.thread = new Thread(threadEnvia);
                thread.start();
                this.tipoGeral  = "login";
                System.out.println("-------------***LOGIN***-------------");
                this.writeToServer = true;
                recebeMsgdoServer();
                
            } else {
                esperaDesbloquear();
            }
        } else {
            esperaDesbloquear();
        }
    }
    private void recebeMsgdoServer() {
        //Método que recebe as msg do server
        String msg=null;
        while(true){
            try {
                msg = inFromServer.readLine();
                System.out.println("Recebeu:"+msg);
            } catch (IOException ex) {
                System.out.println("Erro ao receber mensagem do server:"+ex);
                Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(isError(msg)){
                System.out.println("Mensagem de erro!");
                break;
            }else if(isOutTime(msg)){
                System.out.println("Ficou sem tempo!");
                break;
            }else if(isConfirmacao(msg)){
                System.out.println("Voto enviado com sucesso!");
                break;
            }else{
                try {
                    decodeData(msg);
                } catch (IOException ex) {
                    System.out.println("Erro: "+ex);
                    Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        esperaDesbloquear();
    }

   

    public void decodeData(String data) throws IOException {
        //Método que descodifica as mensagens Protocolo e altera o valor as variaveis writeToServer e tipo
        String[] words = data.split(";");
        String[] type = words[0].split(" | ");
        if (type[2].equals("status")) {
            String[] logged = words[1].split(" | ");
            if (logged[3].equals("on")) {
                System.out.println("You have logged in successfully!!");
                this.loginSuccess= true;
                this.contador = 0;
                this.writeToServer = false;
                return;
            } else if (logged[3].equals("off")) {
                
                this.contador += 1;
                System.out.println("Wrong username or password!!!!");
                if(this.contador == 3){
                    System.out.println("Acabaram-se as tentativas");
                    this.thread.interrupt();
                    this.writeToServer = false;
                    esperaDesbloquear();
                }else{
                    this.writeToServer = true;
                }
                System.out.println("Contador:"+this.contador);
                
            }
        } else if (type[2].equals("lista_candidatas")) {
            System.out.println("Escolha a lista:");
            arrayListas = new String [20];
            arrayListas = colocaListasArray(data);
            tipoGeral = "votar";
            writeToServer= true;
           
        }
        return;
    }

    public boolean isError(String data) {
        //Método que verifica se a string que recebemos do server é do tipo "error"
        String str = null;
        String[] words = data.split(";");
        String[] type = words[0].split(" | ");
        int op = 0;
        if (type[2].equals("error")) {
            String[] str1 = words[1].split(" | ");
            if (str1[3].equals("Não_existem_eleições")) {
                System.out.println("Não existem eleições!");
                return true; //TODO - fechar o cliente como deve de ser
            } else if (str1[3].equals("Não_existem_listas")) {
                System.out.println("Não existem listas");
                return true;
            }
        }
        return false;
    }


    

    public void exit() {
        try {
            this.s.close();;
        } catch (IOException e) {
        }
        return;
    }

    private boolean isOutTime(String data) {
        //Método que verifica se a string que recebeu é do tipo "time_error"
        String[] words = data.split(";");
        String[] aux = words[1].split(" | ");

        if(aux[3].equals("Time_Error")) return true;
        
        return false;
        
    }

    private boolean isConfirmacao(String data) {
        //Método que verifica se a string que recebeu é de confirmação do voto
        String[] words = data.split(";");
        String[] type = words[0].split(" | ");
        String[] msg = words[1].split(" | ");
        System.out.println(type[2]);
        if(type[2].equals("votoRecebido")){
            if(msg[3].equals("true")){
                return true;
            }
        }
        return false;
    }
       private String[] colocaListasArray(String data) {
        //Método que coloca as listas num array
        String[] words = data.split(";");
        String[] type = words[0].split(" | ");
        String[] item_count = words[1].split(" | ");

        int count = Integer.parseInt(item_count[3]);
        int a = 1, j = 0;
        String[] aux = new String[count];
        for (int i = 2; i < count + 2; i++) {
            String[] s = words[i].split(" | ");
            aux[j] = s[3];
            j++;
        }

        return aux;
    }

}

    

class Envia_Thread extends TcpClient implements Runnable{
    //Thread que está sempre à escuta de inputs do teclado. Só os processa quando a variável writeToServer está a true
    PrintWriter outToServer;
    public Envia_Thread(PrintWriter outToServer){
        super();
        this.outToServer = outToServer;
    }
    public void run(){
        Scanner sc = new Scanner(System.in);
        String str=null;
        boolean check = false;
        while (true) {
            do {
                try {
                    str = sc.nextLine();
                } catch (Exception e) {
                }
                if(super.writeToServer == true){  //veririca se pode enviar
                    check = enviaParaServer(str, null);
                }
                
                if (!check) {
                    System.out.println("Input inválido!");
                }
            } while (!check);

    }
    }
    private boolean enviaParaServer(String data, String[] arrayListas) {
        boolean check =false;
        //Método que recebe a msg no protocolo e verifica se está tudo ok. Se estiver envia para o server e retorna true, caso contrário retorna false!
    
        String[] words = null;
        String[] type = null;
        String tipo = null;
        String str = null;
        String[] str1 = null;
        try {
            words = data.split(";");
            type = words[0].split(" | ");
            tipo = type[2];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return false;
        }

        if (tipoGeral.equals("login")) {
            if (tipo.equals("login")) {
                String pwd = null;
                String username = null;
                try {
                    String[] type1 = words[1].split(" | "); //username
                    String[] type2 = words[2].split(" | "); // pwd
                    if (type1[1].equals("username")) {
                        username = type1[3];
                    } else {
                        return false; //retorna falso se o input estiver incorreto
                    }
                    if (type2[1].equals("password")) {
                        pwd = type2[3];
                    } else {
                        return false;
                    }

                } catch (ArrayIndexOutOfBoundsException exception) {
                    return false;
                }

                outToServer.println(data);
                System.out.println("Login enviado");
                super.writeToServer = false;
                return true;
            }
        } else if (tipoGeral.equals("votar")) {
            if (tipo.equals("lista_vote")) {
                try {
                    str1 = words[1].split(" | ");
                    if (str1[1].equals("voto")) {
                        str = str1[3];
                        check = verificaVoto(str, super.arrayListas);
                        //Verificar se existe a lista votada 
                        if (check == false) {
                            return false;
                        }

                    } else {
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    return false;
                }
                outToServer.println(data);
                super.writeToServer = false;
                System.out.println("Voto enviado!");
                return true;
            }}
            return false;
        }

    private boolean verificaVoto(String voto, String[] aux) {
        //Verifica se o voto é válido
        
        boolean check = false;
        if (voto.equals("BRANCO")) {
            return true;
        } else if (voto.equals("NULO")) {
            return true;
        } else {
            for (int k = 0; k < aux.length; k++) {
                if (voto.equals(aux[k])) {
                    return true;
                }
            }
        }
        return check;
    }
    
}
