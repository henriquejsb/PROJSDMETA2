/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author hb
 */
public abstract class Eleicao implements Serializable{
    protected String nome;
    protected String descricao;
    protected Date start;
    protected Date end;
    protected Hashtable<String, ArrayList<String>> votos;
    protected String status;
    protected int nvotostotal;
    

    
    public Eleicao(String nome, String descricao, Date start, Date end){
        this.nome = nome;
        this.descricao = descricao;
        this.start = start;
        this.end = end;
        this.votos = new Hashtable<>();
        this.status = "NEW";
        this.nvotostotal = 0;

    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getStart() {
        return start;
    }
    public int getNvotostotal(){
        return this.nvotostotal;
    }
 

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    
    public boolean isEleicaoCG(){
        return false;
    }
    
    public boolean isEleicaoNucleo(){
        return false;
    }
    
    
    public boolean pesquisaVotos(int cc){
        for(ArrayList<String> list: this.votos.values()){
            for(String string: list){
                if(Integer.parseInt(string.split("\n")[0]) == cc){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String verVotou(int cc){
        String res = "";
        for(Map.Entry<String, ArrayList<String>> entry : this.votos.entrySet()){
            String dep = entry.getKey();
            for(String s: entry.getValue()){
                String[] aux = s.split("\n");
                if(Integer.parseInt(aux[0]) == cc){
                    res += dep + "\n" + aux[1];
                    return res;
                }
            }
        }
        return res;
    }
    
    
    public String getLiveStats(){
        String res = "";
        res += this.nome + "\nVOTOS ATE AGORA : ";
        for(Map.Entry<String, ArrayList<String>> entry : this.votos.entrySet()){
            res += entry.getKey() + ": " + entry.getValue().size() + " ";
        }
        
        return res;
    }
    
    public abstract boolean adicionaLista(Lista lista);
    public abstract boolean votar(String nome, Pessoa pess, int voto, String dep);
    public abstract String getStats();
    
    
}
