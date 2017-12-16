/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

/**
 *
 * @author hb
 */
public class ListaFuncionarios extends Lista{
    
    public ListaFuncionarios(String nome){
        super(nome);
    }
    
    @Override
    public boolean isListaFuncionarios(){
        return true;
    }
    
    
    @Override
    public boolean adicionaElemento(Pessoa novo){
        if(novo.getNfuncionario() != -1 && this.pesquisaPessoa(novo.getCC()) == null){
            //#FIXME ver se ja esta na lista
            this.lista.add((Funcionario) novo);
            return true;
        }
         else{
            return false;
        }
    }
}
