/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Scanner;


/**
 *
 * @author hb
 */
public class Consola extends UnicastRemoteObject implements ConsolaINT {
    
    private static RMIServerINT rmiserver;
    private static Scanner reader;
    private static ConsolaINT consola;
    private static boolean live;
    private static Config config;
    
    public Consola() throws RemoteException{
        
        super();
        rmiserver = null;
        reader = new Scanner(System.in);   
        live = false;
        config = new Config("consolaconfig.cfg");
    }   

    
    public static void imprimeMenu(){
        System.out.println("MENU\n"
                + "-----------------\n"
                + "1 - Registar pessoas\n"
                + "2 - Criar faculdade\n"
                + "3 - Criar departamento\n"
                + "4 - Criar eleição\n"
                + "5 - Editar eleição\n"
                + "6 - Criar lista de candidatos\n"
                + "7 - Associar candidato a uma lista\n"
                + "8 - Adicionar mesa de voto a uma eleição\n"
                + "9 - Consultar detalhes de eleições passadas\n"
                + "10 - Modo live\n"
                + "11 - Ver onde votou uma pessoa para uma eleição\n"
                + "0 - Sair\n");
    }
    
    
    public static void criarFaculdade() throws RemoteException{
        try{
        System.out.print("Nome da faculdade: ");
        String aux = reader.nextLine();
        boolean teste = false;
        do{
            try{
                rmiserver.adicionarFaculdade(consola,aux);
                teste = true;
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
        }catch(Exception e){
            System.out.println("Exception at criarFaculdade: " + e);
        }
    }
    
    
    public static void criarDepartamento() throws RemoteException{
        try{
        System.out.println("Faculdade correspondente: ");
        String fac = reader.nextLine();
        System.out.println("Departamento novo: ");
        String dep = reader.nextLine();
        boolean teste = false;
        do{
            try{
                rmiserver.adicionarDepartamento(consola,fac, dep);
                teste = true;
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
        
        }catch(Exception e){
            System.out.println("Exception at criarDepartamento: " + e);
        }
    }
    
    public static void registarPessoa() throws RemoteException{
        try{
            int op = -1;
            String input = "";
            do{
                System.out.println("0 - Aluno\n1 - Docente\n2 - Funcionario");
                System.out.print("Opção: ");
                try{
                input = reader.nextLine();
                op = Integer.parseInt(input);
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
            }while(op < 0 || op > 2);
            System.out.print("Nome: ");
            String nome = reader.nextLine();
            boolean valid = false;
            int bi = -1;
            do{
                System.out.print("BI: ");
                input = reader.nextLine();
                try{
                bi = Integer.parseInt(input);
                if(bi <= 0) continue;
                if((int) (Math.log10(bi) +1) != 8) continue;
                valid = true;
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
                
            }while(valid == false);
            
            System.out.println("Data de validade do CC:");
            Date novo = pedeData();
            Date data = new Date();
            if(data.getTime() > novo.getTime()){
                System.out.println("CC já expirado!");
                return;
            }
            System.out.print("Departamento: ");
            String dep = reader.nextLine();
            System.out.print("Password: ");
            String pwd = reader.nextLine();
            System.out.print("Morada: ");
            String morada = reader.nextLine();
            valid = false;
            int telefone = -1;
            do{
                System.out.print("Numero de telefone: ");
                input = reader.nextLine();
                try{
                telefone = Integer.parseInt(input);
                valid = true;
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
                
            }while(valid == false);
        boolean teste = false;
        do{
            try{
                if(rmiserver.registaPessoa(consola,op,nome,morada,bi,telefone,novo,pwd,dep) == true){
                System.out.println("Pessoa adicionada com sucesso!");
                
            }
                teste = true;
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
            
            
        }catch(Exception e){
            System.out.println("Exception at registarPessoa: " + e);
        }
       
    }
    
    public static Date pedeData(){
        int ano, mes, dia;
        String input = "";
        System.out.println("Insira o ano, o mes e o dia por ordem:");
        boolean valid = false;
        ano = -1;
        do{
            System.out.print("Ano: ");
            input = reader.nextLine();
            try{
            ano = Integer.parseInt(input);
            valid = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        valid = false;
        mes = -1;
        do{
            System.out.print("Mes: ");
            input = reader.nextLine();
            try{
            mes = Integer.parseInt(input);
            
            if(!(mes < 1 || mes > 12)) valid = true;
           
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        
        valid = false;
        dia = -1;
        do{
            System.out.print("Dia: ");
            input = reader.nextLine();
            try{
            dia = Integer.parseInt(input);
            if(dia > 0 && dia <= 31) valid = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        Date novo = new Date(ano - 1900, mes - 1, dia);
        return novo;
    }
    
    public static Date pedeDataHoras(){
        int ano, mes, dia, hora, minuto;
        System.out.println("Insira o ano, o mes, o dia, a hora e os minutos por ordem:");
        String input = "";
        boolean valid = false;
        ano = -1;
        do{
            System.out.print("Ano: ");
            input = reader.nextLine();
            try{
            ano = Integer.parseInt(input);
            valid = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        valid = false;
        mes = -1;
        do{
            System.out.print("Mes: ");
            input = reader.nextLine();
            try{
            mes = Integer.parseInt(input);
            
            if(!(mes < 1 || mes > 12)) valid = true;
           
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        
        valid = false;
        dia = -1;
        do{
            System.out.print("Dia: ");
            input = reader.nextLine();
            try{
            dia = Integer.parseInt(input);
            if(dia > 0 && dia <= 31) valid = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        valid = false;
        hora = -1;
        do{
            System.out.print("Hora: ");
            input = reader.nextLine();
            try{
            hora = Integer.parseInt(input);
            if(hora >= 0 && hora <= 23) valid = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        valid = false;
        minuto = -1;
        do{
            System.out.print("Minuto: ");
            input = reader.nextLine();
            try{
            minuto = Integer.parseInt(input);
            if(minuto > 0 && minuto < 60) valid = true;
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }

        }while(valid == false);
        Date novo = new Date(ano - 1900, mes - 1, dia,hora,minuto);
        return novo;
    }
    
    public static void criarEleicao(){
        try{
            String input = "";
            int op = -1;
            String dep = "";
            do{
                System.out.println("1 - Eleição Nucleo");
                System.out.println("2 - Eleição CG");
                input = reader.nextLine();
                try{
                    op = Integer.parseInt(input);
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
            }while(op < 1 && op > 2);
            System.out.println("Nome da eleição: ");
            String nome = reader.nextLine();
            
            if(op == 1){
                System.out.println("Departamento: ");
                dep = reader.nextLine();
                
            }
            System.out.println("Descriçao: ");
            String descricao = reader.nextLine();
            System.out.println("Insira data de inicio:");
            Date inicio = pedeDataHoras();
            Date aux = new Date();
            if(inicio.getTime() < aux.getTime() ){
                System.out.println("Data de inicio ja passou!");
                return;
            }
            System.out.println("Insira data de fim:");
            Date fim = pedeDataHoras();
            if(fim.getTime() < inicio.getTime()){
                System.out.println("Não pode ter data de fim anterior a data de inicio");
                return;
            }
            
            boolean teste = false;
        do{
            try{
               if(op == 1){
                if(rmiserver.criaEleicaoNucleo(consola,nome,descricao,inicio,fim,dep)){
                    System.out.println("Eleicao criada com sucesso!");
                    
                }
                else System.out.println("Erro ao criar eleição!");
                
                }
               
            else{
                if(rmiserver.criaEleicaoCG(consola,nome,descricao,inicio,fim)){
                    System.out.println("Eleicao criada com sucesso!");
                }
                else System.out.println("Erro ao criar eleição!");
                }
            teste = true;
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
            
            
        }catch(Exception e){
            System.out.println("Exception at criarEleicao: " + e);
        }
    }
    
    public static void criarLista(){
        try{
            int op = -1;
            String input = "";
            do{
                System.out.println("1 - Lista de alunos\n2 - Lista de docentes\n3 - Lista de funcionarios");
                input = reader.nextLine();
                try{
                    op = Integer.parseInt(input);
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
            }while(op < 1 && op > 3);
            System.out.print("Eleicao a associar: ");
            String eleicao = reader.nextLine();
            System.out.print("Nome: ");
            String nome = reader.nextLine();
            boolean teste = false;
            do{
            try{
                if(rmiserver.criarLista(consola,eleicao,nome,op)){
                    System.out.println("Lista criada com sucesso!");
                   
                }
                else System.out.println("Erro a criar lista!");       
                teste = true;
                                
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
             
        }catch(Exception e){
            System.out.println("Exception at criarLista: " + e);
        }
        
        //ADICIONAR 
    }
    
    public static void adicionarPessoaLista(){
        String input = "";
        try{
            System.out.print("Eleicao a associar: ");
            String eleicao = reader.nextLine();
            System.out.print("Nome da lista: ");
            String nome = reader.nextLine();
            System.out.print("CC da pessoa: " );
            boolean valid = false;
            int cc = -1;
            do{
                input = reader.nextLine();
                try{
                cc = Integer.parseInt(input);
                valid = true;
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
            }while(valid == false);
            
            
            boolean teste = false;
            do{
            try{
                if(rmiserver.adicionarPessoaLista(consola,eleicao,nome, cc)){
                System.out.println("Adicionou com sucesso à lista!");
            }
            else{
                System.out.println("Não foi possível efetuar esta operação!");
            }
                teste = true;
                                
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
        }catch(Exception e){
            System.out.println("Exception at adicionarPessoaLista: " + e);
        }
    }
    
    public static void editarEleicao(){
        try{
            System.out.println("Eleicao a editar: ");
            String eleicao = reader.nextLine();
            System.out.println("Novo nome: ");
            String nome = reader.nextLine();
            System.out.println("Nova descrição: ");
            String desc = reader.nextLine();
            
            boolean teste = false;
            do{
            try{
                if(rmiserver.editarEleicao(consola,eleicao,nome,desc)){
                System.out.println("Eleição editada com sucesso!");
            }
            else{
                System.out.println("Erro a editar eleição!");
            }
                teste = true;
                                
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
        }catch(Exception e){
            System.out.println("Exception at editarEleicao: " + e);
        }
    }
    
    public static void adicionarMesaVoto(){
        try{
            System.out.print("Eleição: ");
            String eleicao = reader.nextLine();
            System.out.println("Departamento a adicionar: ");
            String dep = reader.nextLine();
            boolean teste = false;
            do{
            try{
                if(rmiserver.adicionarMesaVoto(consola,eleicao,dep)) System.out.println("Mesa de voto adicionada com sucesso!");
            else System.out.println("Erro a adicionar mesa de voto!");
                teste = true;
                                
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
        }catch(Exception e){
            System.out.println("Exception at adicionarMesaVoto: " + e);
        }
    }
    
    public static void consultarDetalhesEleicao(){
        try{
            System.out.print("Eleição: ");
            String eleicao = reader.nextLine();
            boolean teste = false;
            do{
            try{
               rmiserver.consultarDetalhesEleicao(consola, eleicao);
                teste = true;
                                
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
            
            System.out.println("Pressione enter...");
            reader.nextLine();
        }catch(Exception e){
            System.out.println("Exception at consultarDetalhesEleicao: " + e);
        }
    }
    
    public static void verVotou(){
        String input = "";
        int bi = -1;
        boolean valid = false;
        try{
            do{
                System.out.print("BI: ");
                input = reader.nextLine();
                try{
                bi = Integer.parseInt(input);
                
                valid = true;
                }catch(NumberFormatException e){
                    System.out.println("Input inválido!");
                    continue;
                }
                
            }while(valid == false);
        System.out.print("Eleição: "); 
        input = reader.nextLine();
        boolean teste = false;
            do{
            try{
                rmiserver.verVotou(consola, bi,input);
                teste = true;
                                
            }catch(RemoteException e){
                if(reconecta()){
                    continue;
                }else{
                    System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                    return;
                }
            }
        }while(!teste);
        
        }catch(Exception e){
            System.out.println("Exception at verVotou: " + e);
        }
    }
    
    public static void menu() throws RemoteException{
        int op = 1;
        String input = "";
        do{
            imprimeMenu();
            System.out.print("Opção:");
            try{
            input = reader.nextLine();
            op = Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.out.println("Input inválido!");
                continue;
            }
            switch(op){
                case 1:
                    registarPessoa();
                    break;
                case 2:
                    criarFaculdade();
                    break;
                case 3:
                    criarDepartamento();
                    break;
                case 4:
                    criarEleicao();
                    break;
                case 5:
                    editarEleicao();
                    break;
                case 6:
                    criarLista();
                    break;
                case 7:
                    adicionarPessoaLista();
                    break;
                case 8:
                    adicionarMesaVoto();
                    break;
                case 9:
                    consultarDetalhesEleicao();
                    break;
                case 10:
                    getLiveStats();
                    break;
                case 11:
                    verVotou();
                    break;
                
                    
            }
        }while(op != 0);
        System.out.println("A sair...");
    }
    
    
    public static void getLiveStats(){
        live = true;
        Thread t = new Thread(){
            public void run(){
                try{
                while(true){
                    System.out.println("\n\n\n\n\n\n\n\n\n\nA receber info...");
                    boolean teste = false;
                    do{
                    try{
                        System.out.println(rmiserver.liveStats(consola));
                        Thread.sleep(1000);
                        teste = true;

                    }catch(RemoteException e){
                        if(reconecta()){
                            continue;
                        }else{
                            System.out.println("Não foi possível efetuar esta operação! Erro do servidor!");
                            return;
                        }
                    }
                }while(!teste);
                    
                    }
                
                }catch(InterruptedException e){
                    return;
                }
            }
        };
        t.start();
        
        reader.nextLine();
        
        t.interrupt();
        live = false;
        
    }
    
    
    @Override
    public void notifica(String mess) throws RemoteException{
        System.out.println(mess);
    }
    
    @Override
    public void notificaLive(String mess) throws RemoteException{
        if(live) System.out.println(mess);
    }
    
        public static boolean reconecta(){
            long startT = System.currentTimeMillis();
            try{
            while(System.currentTimeMillis() - startT < 30000){
                try{
                    rmiserver = (RMIServerINT) Naming.lookup("rmi://"+config.getHostRmi()+":"+config.getPortRMI()+"/"+config.getNomeRMI());
                    rmiserver.subscribe((ConsolaINT) consola);
                    return true;
                }catch(RemoteException | NotBoundException | MalformedURLException e){
                    Thread.sleep(1000);
                }

            }
            }catch(InterruptedException ex){
                System.out.println("Interrupted exception at notifica: " + ex);
            }
            return false;
        }
    
    
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException{
        System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());
        consola = new Consola();
        try{
            
            rmiserver = (RMIServerINT) Naming.lookup("rmi://"+config.getHostRmi()+":"+config.getPortRMI()+"/"+config.getNomeRMI());
            rmiserver.subscribe((ConsolaINT) consola);
            
        }catch(RemoteException | MalformedURLException | NotBoundException e ){
            System.out.println("Nao ha nenhum servidor RMI a correr!\n");
            return;
        }
        
     
        menu();
        try{
        rmiserver.unsubscribe((ConsolaINT) consola);
        }catch(RemoteException e){
            System.out.println("O servidor não está ligado...");
        }
        System.exit(0);
        
    }
    
    
    
}
