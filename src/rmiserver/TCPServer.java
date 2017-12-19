/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.RMISecurityManager;
import java.net.*;
import java.io.*;
import java.rmi.*;

import static java.lang.Thread.State.WAITING;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hb
 */
public class TCPServer {

    public static MainThread mainThread;

    public static void main(String args[]) {
        
      
        mainThread = new MainThread();

    }
}

class MainThread extends Thread {

    public static RMIServerINT h;
    public static Config config;
    public static MenuThread menuThread;
    public static ArrayList<TerminalThread> listTerminal;
    public static String departamento;
    public static String eleicaoGeral;
    public static ServerSocket listenSocket;
    public static int portotcp;

    public MainThread() {
        config = new Config("tcpconfig.cfg");
      
        this.start();
    }

    public void run() {
        //-----------------Ligação RMI---------------------

        this.listTerminal = new ArrayList<TerminalThread>();
        String[] departamentos = null;
        System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());

        
        try {
            this.h = (RMIServerINT) Naming.lookup("rmi://"+config.getHostRmi()+":"+config.getPortRMI()+"/"+config.getNomeRMI());;
            
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            System.out.println("Não foi possível ligar ao servidor!");
            System.exit(0);
        } 
        
        Scanner sc = new Scanner(System.in);
        boolean teste = false;
        do{
            System.out.print("Insira porto de escuta de pedidos: ");
            String tempstring = sc.nextLine();
            try{
                portotcp = Integer.parseInt(tempstring);
                teste = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
            }
        }while(!teste);
        
        String message = null;
        teste = false;
        do {
            try {
                message = this.h.listarDepartamentos(); //lista os dep para o server escolher
                teste = true;
            } catch (RemoteException e) {
                if (reconecta()) {
                    continue;
                } else {
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }

            }
        } while (!teste);

        teste = false;
        System.out.println(message);
        if (!message.equals("")) {
            departamentos = colocaArrayDep(message);
            this.departamento = escolheArray(departamentos, 1); //escolhe um departamento

        } else {
            System.out.println("Não existem departamentos!");
            return;
        }
        teste = false;
        String message1 = null;
        do {
            try {
                message1 = this.h.receberEleicoes(this.departamento); //recebe as eleições disponível
                teste = true;
            } catch (RemoteException e) {
                if (reconecta()) {
                    continue;
                } else {
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        } while (!teste);
        teste = false;

        boolean check = false;
        if (!message1.equals("")) {
            String[] eleicoes1 = message1.split("\n");
            this.eleicaoGeral = escolheArray(eleicoes1, 0); //escolhe uma eleição
            do {
                try {
                    check = this.h.atribuirDepartamento(this.departamento, this.eleicaoGeral);
                    teste = true;
                } catch (RemoteException e) {
                    if (reconecta()) {
                        continue;
                    } else {
                        System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                        return;
                    }
                }
            } while (!teste);

            if (check == true) {
                this.menuThread = new MenuThread(); //cria a thread para poder desbloquear terminais
                System.out.println("Pronto para escutar pedidos");
                escutaPedidosLigacaoTCP();
            } else {
                System.out.println("Não foi possivel atribuir departamento!");
            }

        }else{
            System.out.println("Não existem eleições!");
        }
    }

    public boolean reconecta()  {
        //Tentar reconectar ao servidor RMI
        long startT = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startT < 30000) {
                try {
                    this.h = (RMIServerINT) Naming.lookup("rmi://"+config.getHostRmi()+":"+config.getPortRMI()+"/"+config.getNomeRMI());

                    return true;
                } catch (RemoteException | NotBoundException | MalformedURLException e) {
                    Thread.sleep(1000);
                }

            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted exception at notifica: " + ex);
        }
        return false;
    }

    private String[] colocaArrayDep(String message) {
        //Separa os departamentos, colocandos no array
        String[] Departamentos = message.split("\n");
        return Departamentos;
    }

    private String escolheArray(String[] array, int tipo) {
        //Para escolher o departamento/eleição da mesa de voto  -> tipo- 1 se é para escolher dep, 0 se é para escolher eleição
        switch (tipo) {
            case 1:
                System.out.println("---------Escolha um Departamento------\n");
                break;
            case 0:
                System.out.println("---------Escolha uma Eleição------\n");
                break;
        }

        int i;
        for (i = 0; i < array.length; i++) {
            System.out.println(i + "-" + array[i]);
        }
        System.out.println("[ARRAY]"+array.length);
        Scanner sc = new Scanner(System.in);
        int n = -1;
        do {
            try {
                System.out.println("Opção: ");
                n = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input inválido!");
                sc.nextLine();
            }
            if(n < 0 || n >= array.length){
                System.out.println("Input inválido!");
            }
        } while (n < 0 || n >= array.length);
        return array[n];
    }

    public RMIServerINT getRmiInt() {
        return this.h;
    }

    private void escutaPedidosLigacaoTCP() {
        //Métodos que está sempre à escuta de pedidos de ligação do tcp. Cria uma TerminalThread por cada pedido, que vai tratar desse terminal
        //-----------------------Ligação TCP--------------------
        while (true) {
            int threadId = 0;
            TerminalThread terminal = null;
            try {
                //int serverPort = 6000;
                System.out.println("A Escuta no Porto " + portotcp);
                listenSocket = new ServerSocket(portotcp);
                System.out.println("LISTEN SOCKET=" + listenSocket);
                while (true) {
                    Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                    System.out.println("Novo terminal criado!");
                    threadId++;
                    System.out.print("O que pretende fazer?\n" +"1-Adicionar Terminal de Voto\n" +"2-Sair\nOpção:");
                    terminal = new TerminalThread(clientSocket, threadId);
                    listTerminal.add(terminal);
                }
            } catch (IOException e) {
                System.out.println("Listen:" + e.getMessage());

            }
        }
    }

}

class MenuThread extends MainThread {
    //Thread que trata do menu do TCP server. Permite adicionar um CC para desbloquear um termninal de voto

    public MenuThread() {
        super();
        //this.start();
    }

    @Override
    public synchronized void run() {
        //Método que permite adicionar CC desbloqueando um terminal
        int op;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("O que pretende fazer?");
            System.out.println("1-Adicionar Terminal de Voto\n2-Sair");
            do {
                System.out.print("Opção: ");
                op = sc.nextInt();
            } while (op < 1 || op > 2);
            switch (op) {
                case 1:
                    boolean access = false;
                    System.out.print("Insira o CC: ");
                    int cc = pedeCC();
                    boolean teste = false;
                    do {
                        try {
                            access = super.h.confirmarCC(cc);
                            teste = true;
                        } catch (RemoteException ex) {
                            if (reconecta()) {
                                continue;
                            } else {
                                System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                                return;
                            }
                        }
                    } while (!teste);
                    if (access == false) {
                        System.out.println("Número de cc inválido!");
                        break;
                    } else {
                        boolean aux1 = false;
                        //Thread.State = WAITING;
                        for (TerminalThread t : listTerminal) {
                            if (t.getState() == WAITING) {
                                aux1 = true;
                                synchronized (t) {
                                    System.out.println("Terminal " + t.thread_number + " ativo!");
                                    t.notify();
                                    break;
                                }
                            }

                        }
                        if (aux1 == false) {
                            System.out.println("Não existem terminais!");
                        }

                    }

                    break;
                case 2:
                    System.out.println("A fechar tudo....");
                    teste = false;
                    do {
                        try {

                            h.fechaMesa(this.departamento, this.eleicaoGeral);
                            teste = true;

                            //-----------------------------------------------Fechar tudo*************************************
                        } catch (RemoteException ex) {
                            if (reconecta()) {
                                continue;
                            } else {
                                System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                                return;
                            }
                        }
                    } while (!teste);
                    for (TerminalThread t : listTerminal) {
                        t.interrupt();
                    }

                    try {
                        listenSocket.close();
                    } catch (IOException ex) {
                        System.out.println("Erro a fechar socket!");
                    }

                    System.exit(0);
            }
        } while (op != 2);
    }

    private static int pedeCC() {
        //Método que pede o CC. O input deve de conter 8 números
        Scanner sc = new Scanner(System.in);
        int n = -1;
        do {
            try {
                n = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input inválido!");
                sc.nextLine();
            }
            if (((int) (Math.log10(n) + 1)) != 8) {
                System.out.println("Input inválido! O número tem de conter 8 digitos!\nInsira o CC");
            }
        } while (((int) (Math.log10(n) + 1)) != 8);
        return n;

    }

}


class TerminalThread extends MainThread {
    //Thread que trata da comunicação com o TCP client
    Socket clientSocket;
    int thread_number;
    int cc;
    BufferedReader reader;
    BufferedWriter writer;

    public TerminalThread(Socket aClientSocket, int numero) {
        super();

        thread_number = numero;
        try {
            clientSocket = aClientSocket;
            clientSocket.setSoTimeout(120000); // Bloquea o terminal depois de 120s de inatividade
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

     //=============================
    public synchronized void run() {
        //Método que desbloqueia o terminal e recebe o login. De seguida chama o método menuVotar
        //O login bloqueia depois de 3 tentativas erradas
        System.out.println("Terminal " + this.thread_number + " está disponível!\n"); //Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
        String msg = null, str = null;
        //while(true){
        try {
            
            while (true) {
                synchronized (this) {
                    System.out.println("Terminal" + this.thread_number + " à espera de pedidos");
                    wait();
                }

                try {
                    writer.write("type | unlocked; msg | true\n");
                    writer.flush();
                } catch (SocketException ex) {
                    super.listTerminal.remove(this);
                    System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
                    this.join();
                } catch (IOException ex) {
                    System.out.println("Erro ao enviar terminal desbloqueado para o cliente: " + ex);
                    Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                int contador = 0;
                while (contador != 3) {
                    try {
                        msg = reader.readLine();
                    } catch (SocketTimeoutException ex) {
                        System.out.println("[T" + thread_number + "] Acabou o tempo: " + ex);
                        outOfTime();
                        break;
                    } catch (SocketException ex) {
                        super.listTerminal.remove(this);

                        System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
                        this.join();
                    } catch (IOException ex) {
                        System.out.println("Erro ao ler login do cliente!: " + ex);
                        Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //recebe login
                    str = decodeData(msg);
                    if (str.equals("Login Errado")) {
                        try {
                            contador += 1;
                            writer.write("type | status ; logged | off; msg | Wrong Login\n");
                            writer.flush();
                        } catch (SocketException ex) {
                            super.listTerminal.remove(this);

                            System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
                            this.join();
                        } catch (IOException ex) {
                            System.out.println("Erro ao enviar mensagem para o cliente: " + ex);
                            Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (str.equals("Login Aceite")) {
                        menuVotar();
                    }
                }
            }
            
        } catch (InterruptedException ex) {
            try {
                clientSocket.close();
            } catch (IOException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            try {
                reader.close();
            } catch (SocketException ex2) {
                super.listTerminal.remove(this);
                System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());

                try {
                    this.join();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            try {
                writer.close();
            } catch (SocketException ex3) {
                super.listTerminal.remove(this);
                System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
                try {
                    this.join();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
                }

            } catch (IOException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("A fechar a thread " + thread_number);
        
            return;
        }
    //}
    }

    public void outOfTime() {
        //Método que apanha no catch a excepção do tempo e envia uma msg para o client a avisar
        try {
            writer.write("type | error; msg | Time_Error\n");
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.run();
    }

    public String decodeData(String data) {
        //Método que recebe uma string no protocolo e separa
        String tipo = null, str = "";
        String[] words = null;
        try {
            words = data.split(";");
            String[] type = words[0].split(" | ");
            tipo = type[2];
        } catch (NullPointerException ex) {
            System.out.println("Erro no decodeData");
        }

        int cc1 = 0;
        if (tipo.equals("login")) {
            String[] type1 = words[1].split(" | "); //username
            String[] type2 = words[2].split(" | "); // pwd
            String username = type1[3];
            String pwd = type2[3].replaceAll("\\s+", "");
            try{
            cc1 = Integer.parseInt(username);}catch(NumberFormatException e){ cc1 = -1; }
            String login = null;
            boolean teste = false;
            do {
                try {
                    login = super.h.verificaLogin(cc1, pwd);
                    teste = true;
                } catch (RemoteException ex) {
                    if (reconecta()) {
                        continue;
                    } else {
                        System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                        return "";
                    }
                }
            } while (!teste);
            if (login.equals("PESSOA")) {
                this.cc = cc1;
                return str = "Login Aceite";

            } else  {
                return str = "Login Errado";
            }
        } else if (tipo.equals("lista_vote")) {
            String[] str1 = words[1].split(" | ");
            str = str1[3];
            return str;
        }
        return str;

    }

    private void menuVotar() {
        //Recebe as listas do rmi e envia para o client. De seguida recebe o voto e envia para o rmi
        String listas = null;
        String voto = null;
        try {
            writer.write("type | status ; logged | on; msg | Welcome to iVotas\n");
            writer.flush();
        } catch (SocketException ex) {
            super.listTerminal.remove(this);
            System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
            try {
                this.join();
            } catch (InterruptedException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (IOException ex) {
            System.out.println("Erro ao enviar mensagem para o cliente de confirmação do login: " + ex);
            Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean teste = false;
        do {
            try {
                //--------------Receber listas da eleição escolhida
                listas = super.h.receberListas(this.cc, this.eleicaoGeral);
                teste = true;
            } catch (RemoteException ex) {
                if (reconecta()) {
                    continue;
                } else {
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        } while (!teste);

        if (listas.equals("")) {
            //Se não houverem listas
            System.out.println("Não existem listas para esta eleição");
            try {
                writer.write("type | error; msg | Não_existem_listas\n");
                writer.flush();
            } catch (SocketException ex) {
                super.listTerminal.remove(this);
                System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
                try {
                    this.join();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
                }

            } catch (IOException ex) {
                System.out.println("Erro ao enviar msg para o cliente: " + ex);
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.run();
        }
        String listas1 = passaListaProtocolo(listas);
        try {
            writer.write(listas1 + "\n");
            writer.flush();
        } catch (SocketException ex) {
            super.listTerminal.remove(this);
            System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
            try {
                this.join();
            } catch (InterruptedException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (IOException ex) {
            System.out.println("Erro ao enviar lista de candidatos para o cliente : " + ex);
            Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //-----------Receber Voto------------
            voto = reader.readLine();
        } catch (SocketTimeoutException ex) {
            System.out.println("[T" + thread_number + "] Acabou o tempo: " + ex);
            outOfTime();
        } catch (SocketException ex) {
            super.listTerminal.remove(this);
            System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
            try {
                this.join();
            } catch (InterruptedException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (IOException ex) {
            System.out.println("Erro a receber voto do cliente: " + ex);
            Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        String voto1 = decodeData(voto);
        teste = false;
        do {
            try {
                super.h.enviarVoto(this.eleicaoGeral, voto1, this.cc, super.departamento);//temos que mudar o rmi
                //System.out.println("A enviar o voto -" + voto1 + "-");
                teste = true;
            } catch (RemoteException ex) {
                if (reconecta()) {
                    continue;
                } else {
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }

            }
        } while (!teste);
        try {
            writer.write("type | votoRecebido; msg | true\n");
            writer.flush();
        } catch (SocketException ex) {
            super.listTerminal.remove(this);
            System.out.println("Impossivel comunicar com o cliente!:" + listTerminal.size());
            try {
                this.join();
            } catch (InterruptedException ex1) {
                Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (IOException ex) {
            System.out.println("Erro ao enviar confirmação do voto: " + ex);
            Logger.getLogger(TerminalThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.run();

    }

    private String passaListaProtocolo(String listas) {
        //Método que recebe as listas e coloca no protocolo
        String prot = "type | lista_candidatas; item_count | ";
        String[] words = listas.split("\n");
        prot += words.length + "; ";
        for (int i = 0; i < words.length; i++) {
            prot += "item_" + i + "_name | " + words[i] + "; ";
        }
        return prot;
    }

}
