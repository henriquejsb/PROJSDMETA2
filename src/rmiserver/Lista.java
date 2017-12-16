/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author hb
 */
public abstract class Lista implements Serializable{
    protected String nome;
    protected ArrayList<Pessoa> lista;
    protected int nvotos;

    
    public Lista(String nome){
        this.nome = nome;
        this.lista = new ArrayList<>();
        this.nvotos = 0;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public int getNvotos(){
        return this.nvotos;
    }
    

    
    public ArrayList<Pessoa> getLista(){
        return this.lista;
    }
    
    public abstract boolean adicionaElemento(Pessoa novo);
    
    public void eliminaElemento(int cc){
        Pessoa aux = this.pesquisaPessoa(cc);
        if(aux != null){
            lista.remove(aux);
        }
        else{
            System.out.println("Not found");
        }
    }
    
    public Pessoa pesquisaPessoa(int cc){
        for(Pessoa pess: lista){
            if (pess.getCC() == cc){
                return pess;
            }
        }
        return null;
    }
    
    public void adicionaVoto(){
        this.nvotos++;
    }
    
    
    
    
    
    @Override
    public String toString(){
        String res = "" + this.nome + "\n";
        for(Pessoa pess: lista){
            res += pess.nome + "\n";
        }
        return res;
    }
    
    public boolean isListaAlunos(){
        return false;
    }
    
    public boolean isListaDocentes(){
        return false;
    }
    
    public boolean isListaFuncionarios(){
        return false;
    }
    
    
    
}
