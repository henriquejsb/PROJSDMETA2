/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





package rmiserver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;


/**
 * Classe do RMIServer
 * @author hbfg
 */

public class RMIServerIMP extends UnicastRemoteObject implements RMIServerINT{

   
    
    
    
    private static ArrayList<Pessoa> pessoas;
    private static ArrayList<Faculdade> faculdades;
    private static ArrayList<MesaVoto> mesas;
    private static ArrayList<Eleicao> eleicoes;
    private static ArrayList<ConsolaINT> consolas;
    private static Config config;
    private static Registry r;
   
    /**
     * Construtor da classe RMIServerIMP. Inicializa as ArrayLists que guardam as pessoas, as faculdades, as mesas de voto e as eleições,
     * assim como as consolas ligadas, e que recebe configurações do ficheiro config.cfg.
     * @throws RemoteException 
     */
    
    public RMIServerIMP() throws RemoteException{
        super();
        pessoas = new ArrayList<>();
        faculdades = new ArrayList<>();
        mesas = new ArrayList<>();
        eleicoes = new ArrayList<>();
        consolas = new ArrayList<>();
        config = new Config("config.cfg");
        
        

        
        Faculdade f1 = new Faculdade("FCTUC");
        Departamento d2 = new Departamento("DEEC");
        Departamento d1 = new Departamento("DEI");
        f1.adicionaDepartamento(d1);
        f1.adicionaDepartamento(d2);
        faculdades.add(f1);
        // PARA LISTAS
        pessoas.add(new Aluno("Joao Maria","Coimbra",11111111,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Aluno("Sopas","Coimbra",22222222,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Docente("Carl Cox","Coimbra",33333333,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Docente("Princess Di","Coimbra",44444444,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Funcionario("Fernando Mendes","Coimbra",55555555,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Funcionario("Paulo Bento","Coimbra",66666666,1,new Date(2017,3,1), "pass",d1 ));
        // PARA OUTROS
        pessoas.add(new Aluno("Madonna","Coimbra",77777777,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Docente("Paulo Escobar","Coimbra",88888888,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Funcionario("Tony Caracol","Coimbra",99999999,1,new Date(2017,3,1), "pass",d1));
        pessoas.add(new Aluno("Shakira","Coimbra",12121212,1,new Date(2017,3,1), "pass",d2 ));
        pessoas.add(new Docente("Adolfo Hitler","Coimbra",13131313,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Funcionario("Lord Voldemort","Coimbra",14141414,1,new Date(2017,3,1), "pass",d1 ));
        pessoas.add(new Admin(null,null,69696969,0,null,"pass",null));
        //MESAS DE VOTO
     
        //LISTAS
        
        ListaAlunos nei = new ListaAlunos("NEI");
        nei.adicionaElemento(pesquisaPessoa(11111111));
        ListaAlunos notnei = new ListaAlunos("Anti-NEI");
        notnei.adicionaElemento(pesquisaPessoa(22222222));
        
        ListaDocentes mrpp = new ListaDocentes("MRPP");
        mrpp.adicionaElemento(pesquisaPessoa(33333333));
        ListaDocentes pnr = new ListaDocentes("PNR");
        pnr.adicionaElemento(pesquisaPessoa(44444444));
        
        //ELEICOES
        EleicaoCG el1 = new EleicaoCG("ELEICAO1","Descricao",new Date(2017-1900,1,1), new Date(2018-1900,12,2));
        
        el1.adicionaLista(notnei);
        el1.adicionaLista(nei);
        el1.adicionaLista(mrpp);
        el1.adicionaLista(pnr);
        el1.adicionaDepartamento(d1);
        el1.adicionaDepartamento(d2);     
        
      
        
        //el1.votar("NEI",pesquisaPessoa(11111111),1,"DEI");
        el1.votar(null,pesquisaPessoa(33333333),0,"DEEC");
        
        
        
        eleicoes.add(el1);

        escreveFicheiro("eleicoes.ser",eleicoes);
        escreveFicheiro("pessoas.ser",pessoas);
        escreveFicheiro("faculdades.ser",faculdades);
        escreveFicheiro("mesas.ser",mesas);

        
       

        
        
    }
    
        
        //---------------------------------------------------------------//
       //---------------------------------------------------------------//
      //----------------MÉTODOS PARA TCP SERVER------------------------//
     //--------------------------------------.........................//
    //---------------------------------------------------------------//
    
    
    /**
     * Método para listar todos os departamentos
     * @return Devolve uma String que contem todos os departamentos separados por um \n ou uma String vazia caso não existam.
     * @throws RemoteException 
     */
    
    @Override
    public String listarDepartamentos() throws RemoteException{
        String send = "";
        try{
            for(Faculdade f: faculdades){
                for(Departamento d: f.getDepartamentos()){
                    send += d.getNome().replaceAll(" ","_") + "\n";     
                }
            }
        return send;
         }catch (Exception e){
        System.out.println("Exception at listarDepartamentos: " + e);
        }
        return send;
    }
    
    /**
     * Método para criar uma nova mesa de voto. Recebe o nome do departamento e da eleição,
     * e verifica se o departamento e a eleição existem, se a eleição ainda não acabou ou não é inválida,
     * e se não existe uma mesa de voto para aquela eleição naquele departamento.
     * @param rcv Nome do departamento ao qual se quer associar a mesa
     * @param eleicao Nome da eleição à qual se quer associar a mesa
     * @return true ou false em caso de sucesso ou insucesso da operação
     * @throws RemoteException 
     */
    
    
    @Override
    public boolean atribuirDepartamento(String rcv, String eleicao) throws RemoteException{
        try{
            Departamento dep = pesquisaDepartamento(rcv.replaceAll("_"," "));
            if(dep == null) return false;
            Eleicao el = pesquisaEleicao(eleicao.replaceAll("_"," "));  
            if(el == null) return false;
            if(!(el.getStatus().equals("NEW") || el.getStatus().equals("ACTIVE"))) return false;
            if(pesquisaMesa(dep.getNome() , el.getNome()) != null) return false;
            MesaVoto mesa = new MesaVoto(dep,el);
            try{
            notificaLive("Acaba de abrir uma mesa de voto em " + dep.getNome());
            }catch(Exception e){
                System.out.println("Erro a ligar a uma consola!");
            }
            mesas.add(mesa);  
 
            escreveFicheiro("mesas.ser",mesas);
            return true;
        }catch(Exception e){
            System.out.println("Exception : " + e);
        }
        return false;
       }
    
    /**
     * Método para verificar login. Recebe o CC e a password e verifica se esse login é válido.
     * 
     * @param cc Identificação do utilizador
     * @param pass Palavra-passe do utilizador
     * @return true ou false em caso de sucesso ou insucesso
     * @throws RemoteException 
     */
    
    @Override 
    public String verificaLogin(int cc, String pass) throws RemoteException{
        try{
        Pessoa p = pesquisaPessoa(cc);
        if(p == null) return "FALSE";
        if(p.getPwd().equals(pass)){
            try{
            notificaLive(cc + " acaba de fazer login!");
            }catch(Exception e){
                System.out.println("Erro a ligar a uma consola!");
            }
            if(p.isAdmin()) return "ADMIN";
               
            else return "PESSOA";
        }
        }catch(Exception e){
            System.out.println("Exception at verificaLogin: " + e);
        }
        return "FALSE";
    }
    
    /**
     * Método para votar. Verifica a existência da eleição e da pessoa que votou,
     * e verifica se a pessoa já votou e se a eleição ainda está ativa
     * @param eleicao Nome da eleição 
     * @param lista "BRANCO", "NULO" ou contém o nome da lista para votar
     * @param cc Identificação do votante
     * @param dep Identificação da mesa de voto por departamento
     * @return true ou false em caso de sucesso ou insucesso
     * @throws RemoteException 
     */
    
    @Override
    public boolean enviarVoto(String eleicao, String lista, int cc, String dep) throws RemoteException{
        
        try{
            System.out.println("Recebeu voto -" + eleicao+ "-" +lista + "-" + cc +"-"+dep);
            Pessoa aux = pesquisaPessoa(cc);
            if(aux == null) return false;
            Eleicao el = pesquisaEleicao(eleicao.replaceAll("_"," "));
            if(el == null) return false;
            if(el.pesquisaVotos(cc) == false && el.getStatus().equals("ACTIVE")){
                int voto;
                switch (lista) {
                    case "BRANCO":
                        voto = 0;
                        break;
                    case "NULO":
                        voto = -1;
                        break;
                    default:
                        voto = 1;
                        break;
                }
                boolean res = el.votar(lista.replaceAll("_"," "), aux, voto,dep);
                if(res){
                    try{
                    notificaLive(cc + " acaba de votar em " + dep + " para " + el.getNome());
                    }catch(Exception ex){
                        System.out.println("Erro a ligar a uma consola!");
                    }
                    escreveFicheiro("eleicoes.ser",eleicoes);
                }
                return res;
            }
        }catch(Exception e){
            System.out.println("Exception at enviarVoto: " + e);
        }
        return false;
    }
    
    /**
     * Método para fechar uma mesa de voto. Elimina uma mesa de voto da lista de mesas ativas
     * @param dep Nome do departamento onde está a mesa de voto
     * @param el Nome da eleição associada à mesa de voto
     * @throws RemoteException 
     */
    
    @Override
    public void fechaMesa(String dep, String el) throws RemoteException{
        try{
            MesaVoto mesa = pesquisaMesa(dep.replaceAll("_"," "), el.replaceAll("_"," "));
            if(mesa == null) return;
            mesas.remove(mesa);
            try{
                notificaLive("Mesa de voto para " + mesa.getEleicao().getNome() + " em " + dep + " acaba de fechar!");
            }catch(Exception e){
                System.out.println("Erro a ligar a uma consola!");
            }
        }catch(Exception e){
            System.out.println("Exception at fechaMesa : " + e);
        }
    }
    
    /**
     * Método para receber listas de uma eleição. De acordo com a pessoa que está a votar
     * (Aluno, Docente ou Funcionário), mostra as listas de uma determinada eleição que esteja 
     * a decorrer.
     * @param cc Identificação da pessoa que está a votar
     * @param eleicao Identificação da eleição
     * @return String com as listas da eleição 
     * @throws RemoteException 
     */
    @Override
    public String receberListas(int cc, String eleicao) throws RemoteException{
        String res = "";
        Eleicao el = pesquisaEleicao(eleicao.replaceAll("_"," "));
        if(el == null) return res;
        if(!el.getStatus().equals("ACTIVE")) return res;
        Pessoa aux = pesquisaPessoa(cc);
        if(aux == null) return res;            
        try{
        if(el.isEleicaoNucleo()){
            if(aux.getNaluno() == -1) return res;
            EleicaoNucleo eln = (EleicaoNucleo) el;
            res = eln.getListasString().replaceAll(" ", "_");
            return res;
        }
        EleicaoCG elcg = (EleicaoCG) el;
        if(aux.getNaluno() != -1){
            res += elcg.getListasAlunosString().replaceAll(" ", "_");
            return res;
        }
        if(aux.getNdocente() != -1){
            res += elcg.getListasDocentesString().replaceAll(" ", "_");
            return res;
        }
        if(aux.getNfuncionario() != -1){
            res += elcg.getListasFuncionariosString().replaceAll(" ", "_");
            return res;
        }
        }catch (Exception e){
            System.out.println("Exception!");
        }
        return res;
    }
    
    /**
     * Método para confirmar um CC. Confirma a existência de uma pessoa com a identificação recebida
     * @param cc Identificação da pessoa
     * @return true ou false
     * @throws RemoteException 
     */
    @Override
    public boolean confirmarCC(int cc) throws RemoteException{
        try{
        for(Pessoa pess: pessoas){
            if(pess.getCC() == cc && !pess.isAdmin()){
                try{
                notificaLive(cc + "acaba de se preparar para votar!");
                }catch(Exception e){
                    System.out.println("Erro a ligar a uma consola!");
                }
                return true;
            }
        }
        }catch(Exception e){
            System.out.println("Exception at confirmarCC: " + e);
        }
        return false;
    }
    
   /**
    * Método para receber eleições. Consoante o departamento que recebe, envia uma lista de eleições
    * que tenham esse departamento associado
    * @param dep
    * @return String com todas as eleições que tenham esse departamento associado
    * @throws RemoteException 
    */
    
    @Override
    public String receberEleicoes(String dep) throws RemoteException{
        String res = "";
        try{
        for(Eleicao el: eleicoes){
            if(el.getStatus().equals("ACTIVE") || el.getStatus().equals("NEW")){
                if(pesquisaMesa(dep,el.getNome()) != null) continue;
                if(el.isEleicaoNucleo()){
                    EleicaoNucleo en = (EleicaoNucleo) el;
                    if(!en.getDepartamento().getNome().equals(dep.replaceAll("_"," "))) continue;
                    
                }
                else{
                    EleicaoCG ecg = (EleicaoCG) el;
                    
                    boolean found = false;
                    for(Departamento d: ecg.getDepartamentos()){
                        if(d.getNome().equals(dep.replaceAll("_"," "))){
                            found = true;
                            break;
                        }
                    }
                    if(found == false) continue;
                    
                }
                res += el.getNome().replaceAll(" ","_") + "\n";
            }
        }
        }catch (Exception e){
            System.out.println("Exception at receberEleicoes: " + e);
        }
        return res;
        
    }

    @Override
    public ArrayList<String> receberEleicoesWeb(int cc) throws RemoteException{
        ArrayList<String> res = new ArrayList<>();
        Pessoa pess = pesquisaPessoa(cc);
        try{
            for(Eleicao el: eleicoes){
                if(!el.verVotou(cc).equals("")) continue;
                if(el.getStatus().equals("ACTIVE")){
                    if(el.isEleicaoNucleo()){
                        EleicaoNucleo en = (EleicaoNucleo) el;
                        if(!pess.getDep().getNome().equals(en.getDepartamento().getNome())) continue;

                    }
                    else{
                        EleicaoCG ecg = (EleicaoCG) el;
                        if(!(pess.getNaluno() != -1 && !ecg.getListasAlunos().isEmpty() || pess.getNdocente() != -1 && !ecg.getListasDocentes().isEmpty() || pess.getNfuncionario() != -1 && !ecg.getListasFuncionarios().isEmpty())){
                            continue;
                        }

                    }
                    System.out.println(el.getNome());
                    res.add(el.getNome());
                }
            }
        }catch (Exception e){
            System.out.println("Exception at receberEleicoes: " + e);
        }
        return res;

    }
    
    
        //------------------------------------------------------------------//
       //-----------------------------------------------------------------//
      //----------------MÉTODOS PARA CONSOLA DE ADMINISTRAÇÃO...........//
     //--------------------------------------.........................//
    //--------------------------------------------------------------//
    
    /**
     * Método para inscrever uma consola. Quando uma consola se liga ao servidor RMI, utiliza este método
     * para o RMI guardar uma referência para ela para poder comunicar
     * @param consol Objeto do tipo ConsolaINT 
     * @throws RemoteException 
     */
    @Override
    public void subscribe(ConsolaINT consol) throws RemoteException{
        
        consolas.add(consol);
    }
    
    
    /**
     * Método para notificar uma consola.
     * @param consol ConsolaINT , consola para a qual se quer enviar uma mensagem
     * @param mess String mensagem que se quer enviar
     */
    public void notifica(ConsolaINT consol, String mess){
        try{
            consol.notifica(mess);
        }catch(RemoteException e){
            consolas.remove(consol);
        }
    }
    
    /**
     * Método para registar uma pessoa. Recebe toda a informação necessária para criar uma nova Pessoa,
     * verificando se o CC dessa pessoa não está atribuído a mais ninguém e se o departamento a que ela 
     * está associada existe.
     * @param consola ConsolaINT para notificar a consola de possíveis erros
     * @param tipo Inteiro que define se a Pessoa é Aluno, Docente ou Funcionário
     * @param nome String com nome da pessoa
     * @param morada String com morada da pessoa
     * @param numero Inteiro com identificação (CC)
     * @param telefone Inteiro com número de telefone
     * @param validadeCC Date validade do CC
     * @param pass String palavra-passe
     * @param dep String departamento associado a essa pessoa
     * @return true ou false 
     * @throws RemoteException 
     */
    @Override
    public boolean registaPessoa(ConsolaINT consola, int tipo, String nome, String morada, int numero, int telefone, Date validadeCC, String pass, String dep) throws RemoteException{
        Pessoa novo;
        System.out.println("----- A REGISTAR PESSOA-------");
        System.out.println("n"+numero);
        System.out.println("d"+dep);
        try{
            Pessoa aux = pesquisaPessoa(numero);

            if(aux != null){
                if(consola!=null){
                    notifica(consola, "Esse CC já está associado!");

                }
                System.out.println("Esse CC já está associado!");
                return false;
                

            }
            System.out.println("DEPOIS 1IF");
            Departamento d = pesquisaDepartamento(dep);
            if(d == null){
                if(consola!=null){
                notifica(consola, "Departamento " + dep + " não existe!");
                
                }
                System.out.println("Departamento " + dep + " não existe!");
                return false;
            }
            System.out.println("Depois dos ifs");
            switch(tipo){
                case 0:
                    novo = (Aluno) new Aluno(nome,morada,numero,telefone, validadeCC,pass,d);
                    pessoas.add(novo);
                    break;
                case 1:
                    novo = (Docente) new Docente(nome,morada,numero,telefone, validadeCC,pass,d);
                    pessoas.add(novo);
                    break;
                case 2:
                    novo = (Funcionario) new Funcionario(nome,morada,numero,telefone, validadeCC,pass,d);
                    pessoas.add(novo);
                    break;
                case 3:
                    novo = (Admin) new Admin(null,null,numero,0, null, pass, null);
                    pessoas.add(novo);
                    break;
            }
            escreveFicheiro("pessoas.ser",pessoas);
            return true;
        }catch(Exception e){
            System.out.println("Exception at registaPessoa: " + e);
        }
        return false; 
    }
    
    /**
     * Método para associar uma pessoa a uma lista. Verifica se a eleição ainda não começou e tenta adicionar
     * uma pessoa a uma lista, consoante o tipo de pessoa (Aluno, Docente ou Funcionário)
     * @param consola ConsolaINT consola para notificações de erros
     * @param eleicao String com nome da eleição
     * @param lista String com nome da lista 
     * @param cc Inteiro com identificação da pessoa
     * @return true ou false
     * @throws RemoteException 
     */
    
    
    @Override
    public boolean adicionarPessoaLista(ConsolaINT consola, String eleicao, String lista, int cc) throws RemoteException{
        try{
            boolean booleano = false;
            Eleicao el = pesquisaEleicao(eleicao);
            if(el == null){
                notifica(consola, "Não existe essa eleição!");
                return false;
            }
            if(!el.getStatus().equals("NEW")){
                notifica(consola, "Já não é possível adicionar membros a esta eleição!");
                return false;
            }
            Pessoa p = pesquisaPessoa(cc);
            if(p == null){
                notifica(consola, "Não existe nenhuma pessoa associada a esse CC!");
                return false;
            }
            if(p.isAdmin()){
                notifica(consola,"Não pode associar um administrador!");
                return false;
            }
            if(el.isEleicaoNucleo()){
                if(p.getNaluno() == -1){
                    
                    return false;
                }
                EleicaoNucleo en = (EleicaoNucleo) el;
                for(Lista l: en.getListas()){
                    if(l.getNome().equals(lista)){
                        booleano = l.adicionaElemento(p);
                        if(booleano) escreveFicheiro("eleicoes.ser",eleicoes);
                        return booleano;
                    }
                }
            }
            else if(el.isEleicaoCG()){
                EleicaoCG ecg = (EleicaoCG) el;
                if(p.getNaluno() != -1){
                    for(Lista l: ecg.getListasAlunos()){
                        if(l.getNome().equals(lista)){
                            booleano = l.adicionaElemento(p);
                            if(booleano) escreveFicheiro("eleicoes.ser",eleicoes);
                            return booleano;
                        }
                    }
                }
                else if(p.getNdocente() != -1){
                    for(Lista l: ecg.getListasDocentes()){
                        if(l.getNome().equals(lista)){
                            booleano = l.adicionaElemento(p);
                            if(booleano) escreveFicheiro("eleicoes.ser",eleicoes);
                            return booleano;
                        }
                    }
                }
                else if(p.getNfuncionario() != -1){
                    for(Lista l: ecg.getListasFuncionarios()){
                        if(l.getNome().equals(lista)){
                            booleano = l.adicionaElemento(p);
                            if(booleano) escreveFicheiro("eleicoes.ser",eleicoes);
                            return booleano;
                        }
                    }
                }
            }
           
            
        }catch(Exception e){
            System.out.println("Exception at adicionarPessoaLista: " + e);
        }
        return false;
    }
                               

    
    /**
     * Método para adicionar um departamento.
     * @param consola ConsolaINT consola para notificações de erros
     * @param faculdade String com nome da faculdade 
     * @param novo Nome do departamento
     * @return true ou false
     * @throws RemoteException 
     */
    
    @Override
    public boolean adicionarDepartamento(ConsolaINT consola, String faculdade, String novo) throws RemoteException{
        try{

            Faculdade fac = pesquisaFaculdade(faculdade);
            if(fac == null){
                notifica(consola, "Não existe a faculdade " + faculdade);
                return false;
            }
            if(pesquisaDepartamento(novo) != null){
                notifica(consola, "Já existe esse departamento!");
                return false;
            }
            Departamento dep = new Departamento(novo);
            fac.adicionaDepartamento(dep);
            escreveFicheiro("faculdades.ser",faculdades);
            return true;
        }catch(Exception e){
            System.out.println("Exception at adicionarDepartamento: " + e);
        }
        return false;
    }
    /**
     * Método para adicionar uma faculdade.
     * @param consola ConsolaINT consola para notificações de erros
     * @param novo String com nome da nova faculdade
     * @return true ou false
     * @throws RemoteException 
     */
    
    
    @Override
    public boolean adicionarFaculdade(ConsolaINT consola, String novo) throws RemoteException{

        try{
            Faculdade fac1 =pesquisaFaculdade(novo);
            if(fac1 == null){
                Faculdade fac = new Faculdade(novo);
                faculdades.add(fac);
                escreveFicheiro("faculdades.ser",faculdades);
                return true;
            }
            else{
                System.out.println("5");
                if(consola!=null){
                    notifica(consola, "Já existe essa faculdade!");
                }
                System.out.println("Já existe essa faculdade!");
                return false;
            }
        }catch(Exception e){
            System.out.println("Exception at adicionarFaculdade: " + e);
        }
        return false;
    }
    
    /**
     * Método para editar uma eleição. Pede um novo nome e uma nova descrição. Verifica se
     * já exise alguma eleição com esse nome e se ela ainda não começou.
     * @param consola ConsolaINT consola para notificações de erros
     * @param eleicao String com nome da eleição que se quer editar
     * @param nome String com novo nome
     * @param desc String com nova descrição
     * @return true ou false
     * @throws RemoteException 
     */
    @Override
    public boolean editarEleicao(ConsolaINT consola, String eleicao, String nome, String desc) throws RemoteException{
        try{
            Eleicao el1 = pesquisaEleicao(eleicao);
            if(el1 == null){
                notifica(consola, "A eleição que pretende editar não existe!");
                return false;
            }
            if(!el1.getStatus().equals("NEW")){
                notifica(consola, "Já não é possível editar essa eleição!");
                return false;
            }
            Eleicao el2 = pesquisaEleicao(nome);
            if(el2 != null){
                notifica(consola, "Já existe uma eleição com o novo nome!");
                return false;
            }
            el1.setNome(nome);
            el1.setDescricao(desc);
            escreveFicheiro("eleicoes.ser",eleicoes);
            return true;
        }catch(Exception e){
            System.out.println("Exception at editarEleicao: " + e);
        }
        return false;
    }
    
    
    
   /**
    * Método para criar uma nova EleicaoNucleo. 
    * @param consola ConsolaINT consola para notificações de erros
    * @param nome String com nome da eleição 
    * @param desc String com descrição da eleição
    * @param inicio Date data de início da eleição
    * @param fim Date data de fim da eleiçãoS
    * @param dep String com nome do departamento associado a esta eleição
    * @return true ou false
    * @throws RemoteException 
    */
    
    @Override
    public boolean criaEleicaoNucleo(ConsolaINT consola, String nome, String desc, Date inicio, Date fim, String dep) throws RemoteException{
        try{
            Eleicao el = pesquisaEleicao(nome);
            if(el != null){
                notifica(consola, "Já existe uma eleição com esse nome!");
                return false;
            }
            Departamento d = pesquisaDepartamento(dep);
            if(d == null){
                notifica(consola, "Esse departamento não existe!");
                return false;
            }
            EleicaoNucleo nova = new EleicaoNucleo(nome,desc,inicio,fim,d);
            eleicoes.add(nova);
            System.out.println("Criou eleição " + nome + " a começar a: " + inicio + "e a acabar a " + fim);
            escreveFicheiro("eleicoes.ser",eleicoes);
            return true;
        }catch(Exception e){
            System.out.println("Exception at criaEleicaoNucleo: " + e);
        }
        return false;
    }
    
    /**
     * Método para criar uma nova EleicaoCG
     * @param consola ConsolaINT consola para notificações de erros
     * @param nome String nome da eleição
     * @param desc String descrição da eleição
     * @param inicio Date data de início da eleição
     * @param fim Date data de fim da eleição
     * @return true ou false
     * @throws RemoteException 
     */
    
    @Override
    public boolean criaEleicaoCG(ConsolaINT consola, String nome, String desc, Date inicio, Date fim) throws RemoteException{
        try{
            Eleicao el = pesquisaEleicao(nome);
            if(el != null){
                notifica(consola, "Já existe uma eleição com esse nome!");
                return false;
            }
            EleicaoCG nova = new EleicaoCG(nome,desc,inicio,fim);
            eleicoes.add(nova);
            escreveFicheiro("eleicoes.ser",eleicoes);
            return true;
        }catch(Exception e){
            System.out.println("Exception at criaEleicaoDG:" + e);
        }
        return false;
    }
    
    
    
    
    /**
     * Método para criar uma nova lista para uma eleição
     * @param consola ConsolaINT consola para notificações de erros
     * @param eleicao String nome da eleição
     * @param nome String com nome da nova lista
     * @param tipo Inteiro que diferencia ListaAlunos , ListaDocentes ou ListaFuncionarios
     * @return true ou false
     * @throws RemoteException 
     */
    @Override
    public boolean criarLista(ConsolaINT consola, String eleicao, String nome, int tipo) throws RemoteException{
        try{
            Eleicao el = pesquisaEleicao(eleicao);
            if(el == null){
                notifica(consola, "Não existe uma eleição com esse nome!");
                return false;
            }
            if(!el.getStatus().equals("NEW")){
                notifica(consola, "Já não é possível adicionar uma lista a esta eleição!");
                return false;
            }
            
            switch(tipo){
                case 1:
                    ListaAlunos aux = new ListaAlunos(nome);
                    if(el.adicionaLista(aux)){
                        escreveFicheiro("eleicoes.ser",eleicoes);
                        return true;
                    }
                    break;
                case 2:
                    ListaDocentes aux1 = new ListaDocentes(nome);
                    if(el.adicionaLista(aux1)){
                        escreveFicheiro("eleicoes.ser",eleicoes);
                        return true;
                    }
                    break;
                case 3:
                    ListaFuncionarios aux2 = new ListaFuncionarios(nome);
                    if(el.adicionaLista(aux2)){
                        escreveFicheiro("eleicoes.ser",eleicoes);
                        return true;
                    }
                    break;
                 
            }
            
            
        }catch(Exception e){
            System.out.println("Exception at criarLista: " + e);
        }

        return false;
    }
    
    /**
     * Adicionar mesa de voto. Adiciona uma mesa de voto a uma eleição.
     * @param consola ConsolaINT consola para notificações de erros
     * @param eleicao String com nome da eleição 
     * @param dep String com nome do departamento que se quer associar
     * @return true ou false
     * @throws RemoteException 
     */
    
    
    @Override
    public boolean adicionarMesaVoto(ConsolaINT consola, String eleicao, String dep) throws RemoteException{
        boolean booleano = false;
        Eleicao el = pesquisaEleicao(eleicao);
        if(el == null){
            notifica(consola, "Não existe essa eleição!");
            return false;
        }
        String status = el.getStatus();
        if(status.equals("INVALID") || status.equals("OVER")){
            if(consola!=null){
                notifica(consola, "Não é possível adicionar mesas de voto a essa eleição!");
            }
            System.out.println("Não é possível adicionar mesa de voto a essa eleição!");
            return false;
        }
        if(el.isEleicaoNucleo()){
            if(consola!=null){
                notifica(consola, "Essa é uma eleição de núcleo, já tem uma mesa associada no seu departamento!");
            }

            return false;
        }
        Departamento d = pesquisaDepartamento(dep);
        if(d == null){
            if(consola!=null){
                notifica(consola, "Esse departamento não existe!");
            }

            return false;
        }
        EleicaoCG ecg = (EleicaoCG) el;
        booleano = ecg.adicionaDepartamento(d);
        if(booleano) escreveFicheiro("eleicoes.ser",eleicoes);
        return booleano;
    }
    
    
    /**
     * Método para consultar detalhes de uma eleição. Recebe o nome da eleição e pede as estatísticas 
     * da eleição, que já tem de ter acabado
     * @param consola ConsolaINT consola para notificações 
     * @param eleicao String com nome da eleição que se pretende consultar
     * @throws RemoteException 
     */
    
    
    @Override
    public String consultarDetalhesEleicao(ConsolaINT consola, String eleicao) throws RemoteException{
        System.out.println("YO RMI A BOMBAR");
        Eleicao el = pesquisaEleicao(eleicao);
        if( el == null){
            System.out.println("RMI BUSTED THAT SKINNY ONE");
            if(consola != null) notifica(consola, "Essa eleição não existe!");
            return "Essa eleição não existe!";
            
        }
        if(!el.getStatus().equals("OVER")){
            System.out.println("RMI HAS NOT ENDED THAT BOYYY");
            if(consola != null) notifica(consola, "Não é possível consultar os detalhes dessa eleição!");
            return "Não é possível consultar os detalhes dessa eleição!";
            
        }
        System.out.println("I'M HERE BOY EHEHE");
        String res ="OLA - " +  el.getStats();
        if(consola != null) notifica(consola, res);
        return res;
        
    }
    
    /**
     * Método para ver onde alguém votou. Verifica a existência da pessoa e da eleição e vê onde uma pessoa votou para
     * uma dada eleição. Método para ser chamado na consola, só imprime na consola.
     * @param consola ConsolaINT referência para comunicar com a consola
     * @param cc Inteiro com identificação da pessoa
     * @param eleicao String com nome da eleição
     * @throws RemoteException 
     */
    
    
    @Override
    public void verVotou(ConsolaINT consola, int cc, String eleicao) throws RemoteException{
        Pessoa pess = pesquisaPessoa(cc);
        if(pess == null){
            notifica(consola, "Essa pessoa não existe!");
            return;
        }
        if(pess.isAdmin()){
            notifica(consola, "Administrador não vota!");
            return;
        }
        Eleicao el = pesquisaEleicao(eleicao);
        if(el == null){
            notifica(consola, "Essa eleição não existe!");
            return;
        }
        if(!(el.getStatus().equals("OVER") || el.getStatus().equals("ACTIVE"))){
            notifica(consola, "Não é possível efetuar esta operação para esta eleição!");
            return;
        }
        String res = el.verVotou(cc);
        if(res.equals("")){
            notifica(consola, "Essa pessoa não votou nessa eleição!");
            return;
        }
        String dep = res.split("\n")[0];
        String date = res.split("\n")[1];
        notifica(consola, cc + " votou para " + eleicao + " no departamento " + dep +" em " + date );
    }
    
    /**
     * Método que devolve estatísticas ao vivo. Devolve uma String que contém a informação das mesas 
     * de voto ligadas no momento, das eleições que estão a decorrer e respetivos votos por mesa de 
     * voto
     * @param consola ConsolaINT consola 
     * @return String com informação
     * @throws RemoteException 
     */
    
    
    @Override
    public String liveStats(ConsolaINT consola) throws RemoteException{
        String res = "";
        res += "MESAS DE VOTO LIGADAS:\n--------------------\n ";
        for(MesaVoto mesa: mesas){
            res += mesa.getEleicao().getNome() + " - " + mesa.getDepartamento().getNome() + "\n";
        }
        /*
        res += "MESAS DE VOTO DESLIGADAS:\n--------------------\n";
        for(Faculdade f: faculdades){
            for(Departamento d: f.getDepartamentos()){
                if(pesquisaMesa(d.getNome()) == null){
                    res += d.getNome() + "\n";
                }
            }
        }
        */
        res += "ELEIÇÕES A DECORRER:\n--------------\n";
        for(Eleicao e: eleicoes){
            if(e.getStatus().equals("ACTIVE")){
                
                if(e.isEleicaoCG()){
                    res += "ELEICAO CG - " + e.getLiveStats();
                     /*
                    EleicaoCG ecg = (EleicaoCG) e;
                   
                    if(!ecg.getListasAlunos().isEmpty()){
                        res += " - VOTOS ALUNOS - " + ecg.getNvotosAlunos();
                    }
                    if(!ecg.getListasDocentes().isEmpty()){
                        res += " - VOTOS DOCENTES - " + ecg.getNvotosDocentes();
                    }
                    if(!ecg.getListasFuncionarios().isEmpty()){
                        res += " - VOTOS FUNCIONARIOS - " + ecg.getNvotosFuncionarios();
                    }
                    res += "\n";
                    */
                    
                }
                if(e.isEleicaoNucleo()){
                    res += "ELEICAO NUCLEO - " + e.getLiveStats();
                }
                
            }
        }
        return res;
        
    }
   
       
    
        
        //-----------------------------------------------------------------//
       //-----------------------------------------------------------------//
      //----------------MÉTODOS PARA AUXILIO DO RMISERVER................//
     //--------------------------------------...........................//
    //-----------------------------------------------------------------//
    
    
    /**
     * Método para pesquisar uma faculdade.
     * @param nome String com nome da faculdade
     * @return Objeto Faculdade 
     */
    
    
    public Faculdade pesquisaFaculdade(String nome){
        System.out.println("pesquisa-"+nome);
        for(Faculdade f: faculdades){
            System.out.println("--");
            if(f.getNome().equals(nome)){
                return f;
            }
        }
        return null;
    }   
    
    /**
     * Método para pesquisar uma pessoa.
     * @param cc Inteiro com identificação da pessoa
     * @return Objeto Pessoa
     */
    
    public static Pessoa pesquisaPessoa( int cc){
        for(Pessoa pess: pessoas){
            if (pess.getCC() == cc){
                return pess;
            }
        }
        return null;
    }
    
    
    /**
     * Método para pesquisar um departamento.
     * @param nome String com nome do departamento
     * @return Objeto Departamento
     */
    
    public Departamento pesquisaDepartamento(String nome){
        Departamento aux;
        for(Faculdade fac: faculdades){
            aux = fac.pesquisaDepartamento(nome);
            if(aux != null) return aux;
        }
        return null;
    }
    
    /**
     * Método para pesquisar uma MesaVoto
     * @param dep String com departamento 
     * @param eleicao String com eleição
     * @return Objeto MesaVoto associado a uma dada eleição e departamento
     */
    
    
    public MesaVoto pesquisaMesa(String dep, String eleicao){
        for(MesaVoto mesa: mesas){
            Departamento d = mesa.getDepartamento();
            Eleicao el = mesa.getEleicao();
            if(d.getNome().equals(dep) && el.getNome().equals(eleicao)){
                return mesa;
            }
            
        }
        return null;
    }
    
    
    
    /**
     * Método para pesquiar uma eleição.
     * @param nome String com nome da eleição
     * @return Objeto Eleicao
     */
    public Eleicao pesquisaEleicao(String nome){
        for(Eleicao el: eleicoes){
            if(el.getNome().equals(nome)) return el;
        }
        return null;
    }
    
    /**
     * Método para notificar todas as consolas ligadas
     * @param mess  String com mensagem
     */
    
    
    public static void notificaLive(String mess){
        for(ConsolaINT consola: consolas){
            try{
                consola.notificaLive(mess);
            }catch(RemoteException e){
                consolas.remove(consola);
            }catch(Exception e){
                System.out.println("Erro a ligar a uma consola!");
            }
        }
    }
    
    /**
     * Método para remover referência a uma consola ligada
     * @param consola ConsolaINT
     * @throws RemoteException 
     */
    public void unsubscribe(ConsolaINT consola) throws RemoteException{
        consolas.remove(consola);
    }
    
 
    /**
     * Método para escrever um objeto num ficheiro
     * @param fich Nome do ficheiro
     * @param obj Object
     */
    public static void escreveFicheiro(String fich, Object obj ){
        ObjectOutputStream ooS = null;
        try{
            FileOutputStream foS = new FileOutputStream(fich);
            ooS = new ObjectOutputStream(foS);
            ooS.writeObject(obj);
            ooS.flush();
        }catch (IOException e) {
            System.out.println("Erro a abrir o ficheiro " + fich);
        }finally {
            if(ooS  != null){
                try {
                    ooS.close();
                } catch (IOException e) {
                    System.out.println("Erro a fechar o ficheiro " + fich);
                }
            }
        }
    }
    
    
    /**
     * Método para ler um Object de um ficheiro
     * @param fich Nome do ficheiro
     * @return Object lido
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public Object leFicheiro(String fich) throws IOException, ClassNotFoundException {
        Object res = new Object();
        ObjectInputStream oiS = null;
        try {
 
            FileInputStream fiS = new FileInputStream(fich);
            oiS = new ObjectInputStream(fiS);
            res = oiS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Merda aqui");
            System.out.println("Erro a ler o ficheiro " + fich);
            return null;
        } finally {
            if (oiS!= null) {
                try {
                    oiS.close();
                } catch (IOException e) {
                    System.out.println("Merda em baixo");
                    System.out.println("Erro a fechar o ficheiro " + fich);
                    
                }
            }
        }
        return res;
    }
    
    
    
  /**
   * Método que lê os ficheiros associados ao RMIServer. Se algum der erro de leitura, não atribui nenhuma informação
   * e as listas ficam vazias
   * @throws IOException
   * @throws ClassNotFoundException 
   */
    public void leFicheiroAll() throws IOException, ClassNotFoundException{
        ArrayList<Faculdade> faculdadestemp = (ArrayList<Faculdade>) leFicheiro("faculdades.ser");
        ArrayList<MesaVoto> mesastemp = (ArrayList<MesaVoto>) leFicheiro("mesas.ser");
        ArrayList<Pessoa> pessoastemp = (ArrayList<Pessoa>) leFicheiro("pessoas.ser");
        ArrayList<Eleicao> eleicoestemp = (ArrayList<Eleicao>) leFicheiro("eleicoes.ser");
        if(faculdadestemp != null && mesastemp != null && pessoastemp != null && eleicoestemp != null){
            faculdades = faculdadestemp;
            mesas = mesastemp;
            pessoas = pessoastemp;
            eleicoes = eleicoestemp;
        }
    }

    public ArrayList<String>  getDepartamentos() throws java.rmi.RemoteException{
        ArrayList<String> array = new ArrayList<String>();
        Faculdade aux;
        for(int i =0; i<faculdades.size(); i++){
            aux = faculdades.get(i);
            for(int j=0; j<aux.getDepartamentos().size(); j++){
                array.add(aux.getDepartamentos().get(i).getNome());
            }

        }

        return array;

    }

    public ArrayList<String> getPessoas()  throws java.rmi.RemoteException{
        ArrayList<String> array = new ArrayList<String> ();
        if(pessoas == null) return null;
        for( int i = 0; i<pessoas.size();i++){
            array.add(pessoas.get(i).nome);
        }
        return array;
    }

    /**
     * Main. São criadas 3 threads. 1 thread monitoriza as eleições para ver se já acabaram ou não. 1 delas é 
     * ativada quando o servidor se identifica como servidor principal, e serve para enviar 
     * pacotes UDP para o porto 6789. A outra thread é ativada quando o servidor se identifica como
     * servidor secundário, e recebe os pacotes UDP no porto 6789. O servidor verifica se já existe alguma referência
     * para um RMI Server. Se sim, é secundário. Se não, cria uma e é o servidor principal.
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO code application logic here
        
        
        
        System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());
        
        RMIServerIMP server = new RMIServerIMP();
        server.leFicheiroAll();
        for(int i = 0; i<pessoas.size(); i++){
            System.out.println(pessoas.get(i).cc);
            System.out.println(pessoas.get(i).pwd);

        }

        for(int i = 0; i<faculdades.size(); i++){
            System.out.println(faculdades.get(i).getNome());

        }
        Thread t = new Thread(){
            @Override
            public synchronized void run(){
            Date aux;
            while(true){
                try {
                    aux = new Date();
                    for(Eleicao el: eleicoes){
                        if(el.getStatus().equals("OVER")) continue;
                        if(el.getStatus().equals("INVALID")) continue;
                        if(el.getStart().getTime() < aux.getTime() ){
                            if(el.getStatus().equals("NEW") ){
                                if(el.isEleicaoNucleo()){
                                    EleicaoNucleo en = (EleicaoNucleo) el;
                                    if(en.getListas().isEmpty()){
                                        el.setStatus("INVALID");
                                        try{
                                        notificaLive(el.getNome() + " foi declarada inválida!");
                                        }catch(Exception e){}
                                        escreveFicheiro("eleicoes.ser",eleicoes);
                                        System.out.println(el.getNome() + " não tem listas associadas! Inválida!");
                                        continue;
                                    }
                                }else{
                                    EleicaoCG ecg = (EleicaoCG) el;
                                    if(ecg.getListasAlunos().isEmpty() && ecg.getListasDocentes().isEmpty() && ecg.getListasFuncionarios().isEmpty()){
                                        ecg.setStatus("INVALID");
                                        try{notificaLive(el.getNome() + " foi declarada inválida!");
                                        }catch(Exception e){}
                                        
                                        escreveFicheiro("eleicoes.ser",eleicoes);
                                        System.out.println(el.getNome() + " não tem listas associadas! Inválida!");
                                        continue;
                                    }
                                }
                                if(el.getEnd().getTime() > aux.getTime()){
                                    el.setStatus("ACTIVE");
                                    try{notificaLive(el.getNome() + " acaba de começar!");
                                    }catch(Exception e){}
                                    escreveFicheiro("eleicoes.ser",eleicoes);
                                    System.out.println(el.getNome() + " acaba de começar!");
                                    continue;
                                }
                                else{
                                    el.setStatus("OVER");
                                    escreveFicheiro("eleicoes.ser",eleicoes);
                                    System.out.println(el.getNome() + " acabou agora!");
                                    continue;
                                }
                            }

                            if(el.getStatus().equals("ACTIVE")){
                                if(el.getEnd().getTime() < aux.getTime()){
                                    try{notificaLive(el.getNome() + " acabou agora!");
                                    }catch(Exception e){}
                                    System.out.println(el.getNome() + " acabou agora!");
                                    el.setStatus("OVER");
                                    escreveFicheiro("eleicoes.ser",eleicoes);

                                }
                            }
                        }
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    System.out.println("InterruptedException at EleicoesThread");
                    return;
                }
            }
        }
        };
        t.start();
        
        
        
      
        
        
        
        
        
        Thread udpprincipal = new Thread() {

            @Override
            public void run() {
                int conta = 0;
                try {
                    DatagramSocket socket = new DatagramSocket();

                    while (true) {

                        byte[] bufsend = "ping".getBytes();
                        InetAddress address = InetAddress.getLocalHost();
                        DatagramPacket responde = new DatagramPacket(bufsend, bufsend.length, address, 6789);
                        socket.send(responde);
                        /*
                byte[] buffer = new byte[256];
                DatagramPacket packetRequest = new DatagramPacket(buffer, buffer.length);
                socket.setSoTimeout(3000);
              
                
                try{
                socket.receive(packetRequest);
                }catch(SocketTimeoutException e){
                    if(conta < 5){
                        conta++;
                        System.out.println("PINGS DOWN: " + conta);
                        continue;
                    }
                    else{
                        System.out.println("SERVIDOR SECUNDARIO DESLIGOU");
                        return;
                    }
                }
                         */

                    }
                } catch (IOException ex) {
                    System.out.println("Erro com thread UDP");
                }
                   
            }
        };
        
        Thread udpsecundario = new Thread(){
            @Override
            public void run() {
                int conta = 1;
                try {
                    DatagramSocket socket = new DatagramSocket(6789);
                    int porto = 0;
                    InetAddress endereco = null;
                    while (true) {

                        byte[] buffer = new byte[256];
                        DatagramPacket packetRequest = new DatagramPacket(buffer, buffer.length);
                        socket.setSoTimeout(3000);

                        try {
                            socket.receive(packetRequest);
                            porto = packetRequest.getPort();
                            endereco = packetRequest.getAddress();
                            conta = 1;
                        } catch (SocketTimeoutException e) {
                            if (conta < 5) {
                                System.out.println("PINGS DOWN: " + conta);
                                conta++;
                                

                            } else {
                                try {
                                    server.leFicheiroAll();
                                    LocateRegistry.createRegistry(config.getPortRMI()).bind(config.getNomeRMI(), server);
                                    System.out.println("SOU O PRINCIPAL AGORA");
                                    socket.close();
                                    udpprincipal.start();
                                    return;

                                } catch (RemoteException | AlreadyBoundException ex) {
                                    System.out.println("Erro ao substituir o servidor principal!");
                                } catch (IOException | ClassNotFoundException exc) {
                                    //System.out.println("Exception a ler ficheiro!");
                                }

                            }
                        }
                        /*
                byte[] bufsend = "ack".getBytes();
                
                DatagramPacket responde = new DatagramPacket(bufsend, bufsend.length,endereco ,  porto);
                socket.send(responde);*/
                    }
                } catch (SocketException ex) {
                    System.out.println("SocketException: " + ex);
                } catch (IOException ex) {
                    System.out.println("IOException: " + ex);
                }

            }

        };
        
        
        
          try{
            r = LocateRegistry.createRegistry(config.getPortRMI());
        }catch(ExportException e){
            r = LocateRegistry.getRegistry(config.getPortRMI());
        }
        try{
            RMIServerINT aux = (RMIServerINT) Naming.lookup("rmi://"+config.getHostRmi()+":"+config.getPortRMI()+"/"+config.getNomeRMI());
            
            System.out.println("Sou o servidor secundário!");
            
            udpsecundario.start();
            
        }catch(NotBoundException e){
            System.out.println("Sou o servidor principal!");
            r.rebind(config.getNomeRMI(), server);
            
            udpprincipal.start();
        }
        
        
       
        
          /*
       
        

           
        */
    }
    
    
    
    
}
