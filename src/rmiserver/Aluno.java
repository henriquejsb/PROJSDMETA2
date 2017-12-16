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
public class Aluno extends Pessoa{
    private int naluno;
    
    public Aluno(String nome, String morada, int numero, int telefone, Date validadeCC, String pass, Departamento dep){
        super(nome,morada,numero,telefone, validadeCC,pass,dep);
        this.naluno = numero;
    }
    
    @Override
    public int getNaluno(){
        return this.naluno;
    }
    
    public void setNaluno(int naluno){
        this.naluno = naluno;
    }
    
}
