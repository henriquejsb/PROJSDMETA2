/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hb
 */
public interface RMIServerINT extends Remote{
    
    //MESA DE VOTO (TCP SERVER)
    public String listarDepartamentos() throws java.rmi.RemoteException;
    public boolean atribuirDepartamento(String rcv, String eleicao) throws java.rmi.RemoteException;
    public boolean confirmarCC(int cc) throws java.rmi.RemoteException;
    public String receberEleicoes(String dep) throws java.rmi.RemoteException;
    public String receberListas(int cc, String eleicao) throws java.rmi.RemoteException;
    public boolean enviarVoto(String eleicao, String lista, int cc, String dep) throws java.rmi.RemoteException;
    public void fechaMesa(String dep, String el) throws java.rmi.RemoteException;
    public boolean verificaLogin(int cc, String pass) throws java.rmi.RemoteException;
    
    //CONSOLE ADMIN
    public boolean registaPessoa(ConsolaINT consola, int tipo, String nome, String morada, int numero, int telefone, Date validadeCC, String pass, String dep) throws java.rmi.RemoteException;
    public void verVotou(ConsolaINT consola, int cc, String eleicao) throws java.rmi.RemoteException;
    public boolean adicionarFaculdade(ConsolaINT consola, String faculdade) throws java.rmi.RemoteException;
    public boolean adicionarDepartamento(ConsolaINT consola, String faculdade, String novo) throws java.rmi.RemoteException;
    public void subscribe(ConsolaINT consolaINT) throws java.rmi.RemoteException;
    public void unsubscribe(ConsolaINT consola) throws java.rmi.RemoteException;
    public boolean criaEleicaoNucleo(ConsolaINT consola, String nome, String desc, Date inicio, Date fim, String dep) throws java.rmi.RemoteException;
    public boolean criaEleicaoCG(ConsolaINT consola, String nome, String desc, Date inicio, Date fim) throws java.rmi.RemoteException;
    public boolean criarLista(ConsolaINT consola, String eleicao, String nome, int tipo) throws java.rmi.RemoteException;
    public boolean adicionarPessoaLista(ConsolaINT consola, String eleicao, String lista, int cc) throws java.rmi.RemoteException;
    public boolean editarEleicao(ConsolaINT consola, String eleicao, String nome, String desc) throws java.rmi.RemoteException;
    public boolean adicionarMesaVoto(ConsolaINT consola, String eleicao, String dep) throws java.rmi.RemoteException;
    public void consultarDetalhesEleicao(ConsolaINT consola, String eleicao) throws java.rmi.RemoteException;
    public String liveStats(ConsolaINT consola) throws java.rmi.RemoteException;

    public ArrayList<String> getPessoas( ) throws java.rmi.RemoteException ;

    public ArrayList<String> getDepartamentos() throws java.rmi.RemoteException ;


//RMI SECUNDARIO
   

    //public void leFicheiroAll() throws ClassNotFoundException,IOException;
    
}
