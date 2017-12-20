package meta2.model;

import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;


import rmiserver.Pessoa;
import rmiserver.RMIServerINT;
import rmiserver.Config;


public class Meta2Bean {
    private RMIServerINT rmiserver;
    private int user;
    private int cc;
    private String password;
    private String  departamento,morada,nome,faculdade,descricao,eleicao,editElei,lista;
    private int tipo, numero, telefone;
    Date data,dataFim;


    public Meta2Bean(){
        try {
            rmiserver = (RMIServerINT) Naming.lookup("rmi://localhost:7000/iVotas");
        }
        catch(NotBoundException|MalformedURLException|RemoteException e) {
            e.printStackTrace();
        }
    }

    public String call(){
        return "";
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEleicao() {
        return eleicao;
    }

    public void setEleicao(String eleicao) {
        this.eleicao = eleicao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getEditElei() {
        return editElei;
    }

    public void setEditElei(String editElei) {
        this.editElei = editElei;
    }



    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String getLogin() throws RemoteException {
        return rmiserver.verificaLogin(this.cc,this.password);
    }

    public boolean getRegisterPessoa() throws RemoteException{
        return rmiserver.registaPessoa(null,this.tipo,this.nome, this.morada,this.cc, this.telefone,this.data, this.password,this.departamento);
    }

    public boolean getCriaFaculdade() throws RemoteException{
        return rmiserver.adicionarFaculdade(null,this.faculdade);

    }

    public boolean getCriaDepartamento() throws RemoteException{
        return rmiserver.adicionarDepartamento(null,this.faculdade,this.departamento);

    }

    public ArrayList<String> getEleicoes() throws RemoteException{
        ArrayList<String> res = new ArrayList<>();
        String [] departamentos = rmiserver.listarDepartamentos().split("\n");
        for(String dep: departamentos){

        }
        return res;

    }


    public boolean getCriaEleicao(String tipo) throws RemoteException {
        if(tipo.equals("Núcleo")){
            System.out.println("eleição nucleo");
            return rmiserver.criaEleicaoNucleo(null, this.eleicao, this.descricao, this.data, this.dataFim,this.departamento);
        }else if(tipo.equals("Conselho Geral")){
            System.out.println("if cg");
            return rmiserver.criaEleicaoCG(null, this.eleicao, this.descricao, this.data, this.dataFim);
        }
        return false;
    }

    public boolean getEditarEleicao() throws RemoteException{
        return rmiserver.editarEleicao(null,this.eleicao,this.editElei,this.descricao);

    }

    public boolean getCriaLista() throws RemoteException{
        return rmiserver.criarLista(null,this.eleicao,this.lista,this.tipo);

    }

    public boolean setAssociarPessoa() throws RemoteException {
        return rmiserver.adicionarPessoaLista(null,this.eleicao,this.lista,this.cc);
    }

    public boolean setAdicionarMV() throws RemoteException {
        return rmiserver.adicionarMesaVoto(null,this.eleicao, this.departamento);
    }


    public  ArrayList<String> getPessoas() throws RemoteException {
        return rmiserver.getPessoas();
    }

    public ArrayList<String>  getDepartamentos() throws java.rmi.RemoteException{
        return rmiserver.getDepartamentos();
    }

    public String getVerVotou() throws RemoteException {
        return rmiserver.verVotou(null, this.cc, this.eleicao);
    }
}