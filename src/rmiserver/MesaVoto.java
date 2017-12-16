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
public class MesaVoto implements Serializable {
    private Departamento dep;
    private Eleicao eleicao;

    
    public MesaVoto(Departamento dep, Eleicao eleicao){
        this.dep = dep;
        this.eleicao = eleicao;
   
    }
    
    public Departamento getDepartamento(){
        return this.dep;
    }
    
    public void setDepartamento(Departamento dep){
        this.dep = dep;
    }
    
    public Eleicao getEleicao(){
        return this.eleicao;
    }
    
  
    
    
    
}
