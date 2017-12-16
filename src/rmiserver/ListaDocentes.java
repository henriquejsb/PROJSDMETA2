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
public class ListaDocentes extends Lista{
    
    public ListaDocentes(String nome){
        super(nome);
    }
    
    
    @Override
    public boolean isListaDocentes(){
        return true;
    }
    
    @Override
    public boolean adicionaElemento(Pessoa novo){
        if(novo.getNdocente() != -1 && this.pesquisaPessoa(novo.getCC()) == null){
            //#FIXME ver se ja esta na lista
            this.lista.add((Docente) novo);
            return true;
        }
        else{
            return false;
        }
    }
}