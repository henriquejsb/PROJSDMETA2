/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.util.Date;

/**
 *
 * @author hb
 */
public class Docente extends Pessoa{
    private int ndocente;
    
    public Docente(String nome, String morada, int numero, int telefone, Date validadeCC, String pass, Departamento dep){
        super(nome,morada,numero,telefone, validadeCC,pass,dep);
        this.ndocente = numero;
    }
    
    @Override
    public int getNdocente(){
        return this.ndocente;
    }
    
    public void setNdocente(int ndocente){
        this.ndocente = ndocente;
    }
    
}
