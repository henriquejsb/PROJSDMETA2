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
public class Faculdade implements Serializable{
    private String nome;
    private ArrayList<Departamento> departamentos;
    
    public Faculdade(String nome){
        this.nome = nome;
        this.departamentos = new ArrayList<>();
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public ArrayList<Departamento> getDepartamentos(){
        return this.departamentos;
    }
    
    public void adicionaDepartamento(Departamento novo){
        //#FIXME verificar se ja tem algum dep.
        this.departamentos.add(novo);
    }
    
    public void removeDepartamento(Departamento del){
        this.departamentos.remove(del);
    }
    
    public Departamento pesquisaDepartamento(String nome){
        for(Departamento dep: this.departamentos){
            if(dep.getNome().equals(nome)){
                return dep;
            }
        }
        return null;
    }
    
    
    
    
}
