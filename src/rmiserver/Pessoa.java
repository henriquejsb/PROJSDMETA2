/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hb
 */
public class Pessoa implements Serializable{
    protected String nome;
    protected String morada;
    protected int cc;
    protected Date validadeCC;
    protected String facebookId;
    protected int telefone;
    protected String pwd;
    protected Departamento dep ;
    
    public Pessoa(String nome, String morada, int numero, int telefone, Date validadeCC, String pass, Departamento dep){
        this.nome = nome;
        this.morada = morada;
        this.cc = numero;
        this.telefone = telefone;
        this.validadeCC = validadeCC;
        this.pwd = pass;
        this.dep = dep;
        this.facebookId = null;
    }
    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getCC() {
        return cc;
    }

    public void setCC(int numero) {
        this.cc = numero;
    }

    public Date getValidadeCC() {
        return validadeCC;
    }

    public void setValidadeCC(Date validadeCC) {
        this.validadeCC = validadeCC;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Departamento getDep() {
        return dep;
    }

    public void setDep(Departamento dep) {
        this.dep = dep;
    }
    
    public int getNaluno(){
        return -1;
    }
    
    public int getNdocente(){
        return -1;
    }

    public boolean isAdmin(){ return false; }
    
    public int getNfuncionario(){
        return -1;
    }
    
    @Override
    public String toString(){
        String res = "";
        res += nome + "\n" + cc;
        return res;
    }
    
    
    
}
