/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hb
 */
public class EleicaoNucleo extends Eleicao{
    ArrayList<ListaAlunos> listasAlunos;
    private int nvotosbrancos;
    private int nvotosnulos;
    private Departamento dep;

    public EleicaoNucleo( String nome, String descricao, Date start, Date end, Departamento dep) {
        super(nome, descricao, start, end);
        this.listasAlunos = new ArrayList<>();
        this.nvotosbrancos = 0;
        this.nvotosnulos = 0;
        this.dep = dep;
        
    }
    
    public boolean adicionaLista(Lista lista){
        if(lista.isListaAlunos()){
            if(this.pesquisaListaAlunos(lista.getNome()) == null){
                this.listasAlunos.add((ListaAlunos) lista);
                return true;
            }
            
        }
        return false;
    }
    
    public ListaAlunos pesquisaListaAlunos(String nome){
        for(Lista lis: this.listasAlunos){
            if(lis.getNome().equals(nome)){
                return (ListaAlunos) lis;
            }
        }
        return null;
    }
    
    
    @Override
    public boolean isEleicaoNucleo(){
        return true;
    }
    
    @Override
    public boolean votar(String nome, Pessoa pess, int voto, String dep){
        if(pesquisaVotos(pess.getCC()) == true){
            return false;
        }
        Date data = new Date();
        
        switch (voto) {
            case -1:
                this.nvotosnulos++;
                break;
            case 0:
                this.nvotosbrancos++;
                break;
            case 1:
                ListaAlunos aux = this.pesquisaListaAlunos(nome);
                if(aux == null) return false;
                aux.adicionaVoto();
                break;
            default:
                break;
        }
        ArrayList<String> aux = new ArrayList<>();
        if(votos.containsKey(dep)){
            aux = votos.get(dep);
        }
        aux.add(pess.getCC() + "\n" + data.toString());
        votos.put(dep,aux);
        this.nvotostotal++;
        return true;
    }
    
    @Override
    public String toString(){
        String aux = "";
        for(Lista lis: this.listasAlunos){
            aux += lis.toString() + "|";
        }
        return aux;
    }
    
    public String getListasString(){
        String aux = "";
        for(Lista lis: this.listasAlunos){
            aux += lis.getNome() + "\n";
        }
        return aux;
    }
    
    public ArrayList<ListaAlunos> getListas(){
        return this.listasAlunos;
    }
    
    public Departamento getDepartamento(){
        return this.dep;
    }
    
    @Override
    public String getStats(){
        String res = "";
        res += "ELEICAO NUCLEO : " + this.nome;
        res += "\nDESCRICAO: " + this.descricao;
        res += "\nDATA INICIO: " + this.start.toString();
        res += "\nDATA FIM: " + this.end.toString();
        res += "\n----------------------------------------\n";
        if(!this.listasAlunos.isEmpty()){
            if(this.nvotostotal== 0) res += "NINGUEM VOTOU EM LISTAS DE ALUNOS!\n";
            else{
                res += "LISTAS ALUNOS\n-------------------------------\n";
                long aux1 = this.nvotosbrancos * 100 / this.nvotostotal;
                long aux2 = this.nvotosnulos* 100 / this.nvotostotal;
                for(ListaAlunos l: this.listasAlunos){
                    long aux = l.getNvotos() * 100 / this.nvotostotal;
                    
                    res += l.getNome() + " - " + l.getNvotos() + " votos - " + aux + "%\n";
                }
                res += "Votos brancos - " + this.nvotosbrancos + " - " + aux1 + "%\n";
                res += "Votos nulos - " + this.nvotosnulos + " - " + aux2 + "%\n";
            }
            
            
        }
        return res;
    }
    
    
    
    
}
