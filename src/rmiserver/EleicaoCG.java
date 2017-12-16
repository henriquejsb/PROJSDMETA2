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
public class EleicaoCG extends Eleicao{
    private ArrayList<ListaAlunos> listasAlunos;
    private ArrayList<ListaDocentes> listasDocentes;
    private ArrayList<ListaFuncionarios> listasFuncionarios;
    private ArrayList<Departamento> departamentos;
    private int nvotosbrancosalunos;
    private int nvotosnulosalunos;
    private int nvotosbrancosdocentes;
    private int nvotosnulosdocentes;
    private int nvotosbrancosfuncionarios;
    private int nvotosnulosfuncionarios;
    private int nvotostotalalunos;
    private int nvotostotaldocentes;
    private int nvotostotalfuncionarios;
    
    
    
    public EleicaoCG( String nome, String descricao, Date start, Date end) {
        super(nome, descricao, start, end);
        this.listasAlunos = new ArrayList<>();
        this.listasDocentes = new ArrayList<>();
        this.listasFuncionarios = new ArrayList<>();
        this.departamentos = new ArrayList<>();
        this.nvotosbrancosalunos = 0;
        this.nvotosnulosalunos = 0;
        this.nvotosbrancosdocentes = 0;
        this.nvotosnulosdocentes = 0;
        this.nvotosbrancosfuncionarios = 0;
        this.nvotosnulosfuncionarios = 0;
        this.nvotostotalalunos = 0;
        this.nvotostotaldocentes = 0;
        this.nvotostotalfuncionarios = 0;

        
               
    }
    
    @Override
    public boolean isEleicaoCG(){
        return true;
    }
    
    public ArrayList<Departamento> getDepartamentos(){
        return this.departamentos;
    }
    
    @Override
    public boolean adicionaLista(Lista lista){
        if(lista.isListaAlunos()){
            ListaAlunos aux = this.pesquisaListaAlunos(lista.getNome());
            if(aux == null){
                this.listasAlunos.add((ListaAlunos) lista);
                return true;
            }
            else return false;
        }
        else if(lista.isListaDocentes()){
            ListaDocentes aux = this.pesquisaListaDocentes(lista.getNome());
            if(aux == null){
                this.listasDocentes.add((ListaDocentes) lista);
                return true;
            }
            else return false;
        }
        else if(lista.isListaFuncionarios()){
            ListaFuncionarios aux = this.pesquisaListaFuncionarios(lista.getNome());
            if(aux == null){
                this.listasFuncionarios.add((ListaFuncionarios) lista);
                return true;
            }
            else return false;
        }
        return false;
    }
    
    public void eliminaListaAlunos(String nome){
        ListaAlunos aux = this.pesquisaListaAlunos(nome);
        if(aux != null) this.listasAlunos.remove(aux);
    }
    
    public void eliminaListaDocentes(String nome){
        ListaDocentes aux = this.pesquisaListaDocentes(nome);
        if(aux != null) this.listasDocentes.remove(aux);
    }
    
    public void eliminaListaFuncionarios(String nome){
        ListaFuncionarios aux = this.pesquisaListaFuncionarios(nome);
        if(aux != null) this.listasFuncionarios.remove(aux);
    }
    
    
    
    public ListaAlunos pesquisaListaAlunos(String nome){
        for(Lista lis: this.listasAlunos){
            if(lis.getNome().equals(nome)){
                return (ListaAlunos) lis;
            }
        }
        return null;
    }
    
    public ListaDocentes pesquisaListaDocentes(String nome){
        for(Lista lis: this.listasDocentes){
            if(lis.getNome().equals(nome)){
                return (ListaDocentes) lis;
            }
        }
        return null;
    }
    
    public ListaFuncionarios pesquisaListaFuncionarios(String nome){
        for(Lista lis: this.listasFuncionarios){
            if(lis.getNome().equals(nome)){
                return (ListaFuncionarios) lis;
            }
        }
        return null;
    }
    
    public boolean adicionaDepartamento(Departamento dep){
        for(Departamento d: departamentos){
            if(d.getNome().equals(dep.getNome())){
                return false;
            }
            
        }
        departamentos.add(dep);
        return true;
        
    }
    
    @Override
    public boolean votar(String nome, Pessoa pess, int voto, String dep){
        //VOTO -> -1 NULO 0 -> BRANCO 1 -> VOTO 
        if(pesquisaVotos(pess.getCC()) == true){
            
            return false;
        }
        Date data = new Date();
        

        if(pess.getNaluno() != -1){
            
            if(voto == 0){
                this.nvotosbrancosalunos++;
            }
            if(voto == -1){
                this.nvotosnulosalunos++;
            }
            if(voto == 1){
                ListaAlunos aux = this.pesquisaListaAlunos(nome);
                if(aux == null) return false;
                aux.adicionaVoto();
            }
            this.nvotostotalalunos++;
        }
        if(pess.getNdocente() != -1){
            
            if(voto == 0){
                this.nvotosbrancosdocentes++;
            }
            if(voto == -1){
                this.nvotosnulosdocentes++;
            }
            if(voto == 1){
                ListaDocentes aux = this.pesquisaListaDocentes(nome);
                if(aux == null) return false;
                aux.adicionaVoto();
            }
            this.nvotostotaldocentes++;
        }
        
        if(pess.getNfuncionario() != -1){
            
            if(voto == 0){
                this.nvotosbrancosfuncionarios++;
            }
            if(voto == -1){
                this.nvotosnulosfuncionarios++;
            }
            if(voto == 1){
                ListaFuncionarios aux = this.pesquisaListaFuncionarios(nome);
                if(aux == null) return false;
                aux.adicionaVoto();
            }
            this.nvotostotalfuncionarios++;
        }

       
        ArrayList<String> aux = new ArrayList<>();
        if(votos.containsKey(dep)){
            aux = votos.get(dep);
        }
        aux.add(pess.getCC() + "\n" + data.toString());
        votos.put(dep,aux);
        return true;
    }
    
    @Override
    public String toString(){
        String aux = this.nome + " - " + this.descricao + "\n";
        aux += "LISTAS ALUNOS\n---------------\n";
        for(Lista l: this.listasAlunos){
            aux += l.toString() + "\n";
        }
        aux += "\nLISTAS DOCENTES\n";
        for(Lista l: this.listasDocentes){
            aux += l.toString() + "\n";
        }
        aux += "\nLISTAS FUNCIONARIOS\n";
        for(Lista l: this.listasFuncionarios){
            aux += l.toString() + "\n";
        }
        return aux;
    }
    
    
    public String getListasAlunosString(){
        String aux = "";
        for(Lista lis: this.listasAlunos){
            aux += lis.getNome() + "\n";
        }
        return aux;
    }
    
    public String getListasDocentesString(){
        String aux = "";
        for(Lista lis: this.listasDocentes){
            aux += lis.getNome() + "\n";
        }
        return aux;
    }
    
    public String getListasFuncionariosString(){
        String aux = "";
        for(Lista lis: this.listasFuncionarios){
            aux += lis.getNome() + "\n";
        }
        return aux;
    }
    
    public ArrayList<ListaAlunos> getListasAlunos(){
        return this.listasAlunos;
    }
    
    public ArrayList<ListaDocentes> getListasDocentes(){
        return this.listasDocentes;
    }
    
    public ArrayList<ListaFuncionarios> getListasFuncionarios(){
        return this.listasFuncionarios;
    }
    
    public int getNvotosAlunos(){
        return this.nvotostotalalunos;
    }
    
    public int getNvotosDocentes(){
        return this.nvotostotaldocentes;
    }
    
    public int getNvotosFuncionarios(){
        return this.nvotostotalfuncionarios;
    }
    
    @Override
    public String getStats(){
        String res = "";
        res += "ELEICAO: " + this.nome;
        res += "\nDESCRICAO: " + this.descricao;
        res += "\nDATA INICIO: " + this.start.toString();
        res += "\nDATA FIM: " + this.end.toString();
        res += "\n----------------------------------------\n";
        if(!this.listasAlunos.isEmpty()){
            if(this.nvotostotalalunos == 0) res += "NINGUEM VOTOU EM LISTAS DE ALUNOS!\n";
            else{
                res += "LISTAS ALUNOS\n-------------------------------\n";
                long aux1 = this.nvotosbrancosalunos * 100 / this.nvotostotalalunos;
                long aux2 = this.nvotosnulosalunos * 100 / this.nvotostotalalunos;
                for(ListaAlunos l: this.listasAlunos){
                    long aux = l.getNvotos() * 100 / this.nvotostotalalunos;
                    
                    res += l.getNome() + " - " + l.getNvotos() + " votos - " + aux + "%\n";
                }
                res += "Votos brancos - " + this.nvotosbrancosalunos + " - " + aux1 + "%\n";
                res += "Votos nulos - " + this.nvotosnulosalunos + " - " + aux2 + "%\n";
            }
            
            
        }
        if(!this.listasDocentes.isEmpty()){
            if(this.nvotostotaldocentes == 0) res += "NINGUEM VOTOU EM LISTAS DE DOCENTES!\n";
            else{
                res += "LISTAS DOCENTES\n-------------------------------\n";
                long aux1 = this.nvotosbrancosdocentes * 100 / this.nvotostotaldocentes;
                long aux2 = this.nvotosnulosdocentes * 100 / this.nvotostotaldocentes;
                for(ListaDocentes l: this.listasDocentes){
                    long aux = l.getNvotos() * 100 / this.nvotostotaldocentes;
                    
                    res += l.getNome() + " - " + l.getNvotos() + " votos - " + aux + "%\n";
                }
                res += "Votos brancos - " + this.nvotosbrancosdocentes + " - " + aux1 + "%\n";
                res += "Votos nulos - " + this.nvotosnulosdocentes+ " - " + aux2 + "%\n";
            }
            
            
        }
        if(!this.listasFuncionarios.isEmpty()){
            if(this.nvotostotalfuncionarios == 0) res += "NINGUEM VOTOU EM LISTAS DE FUNCIONARIOS!\n";
            else{
                res += "LISTAS FUNCIONARIOS\n-------------------------------\n";
                long aux1 = this.nvotosbrancosfuncionarios * 100 / this.nvotostotalfuncionarios;
                long aux2 = this.nvotosnulosfuncionarios * 100 / this.nvotostotalfuncionarios;
                for(ListaFuncionarios l: this.listasFuncionarios){
                    long aux = l.getNvotos() * 100 / this.nvotostotalfuncionarios;
                    
                    res += l.getNome() + " - " + l.getNvotos() + " votos - " + aux + "%\n";
                }
                res += "Votos brancos - " + this.nvotosbrancosfuncionarios + " - " + aux1 + "%\n";
                res += "Votos nulos - " + this.nvotosnulosfuncionarios + " - " + aux2 + "%\n";
            }
            
            
        }
    return res;
    }
    
    
}
