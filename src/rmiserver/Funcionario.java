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
public class Funcionario extends Pessoa{
    private int nfuncionario;
    
    public Funcionario(String nome, String morada, int numero, int telefone, Date validadeCC, String pass, Departamento dep){
        super(nome,morada,numero,telefone, validadeCC,pass,dep);
        this.nfuncionario = numero;
    }
    
    @Override
    public int getNfuncionario(){
        return this.nfuncionario;
    }
    
    public void setNfuncionario(int nfuncionario){
        this.nfuncionario = nfuncionario;
    }
    
    
    
}
