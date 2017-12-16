/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.io.Serializable;

/**
 *
 * @author hb
 */
public class Departamento implements Serializable{
    private String nome;
    private boolean ocupado;
    
    public Departamento(String nome){
        this.nome = nome;
        ocupado = false;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public boolean isOcupado(){
        return this.ocupado;
    }
    
}
