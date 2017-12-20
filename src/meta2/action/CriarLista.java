package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class CriarLista extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String tipoLista = null, eleicao = null, lista = null;

    @Override
    public String execute() throws RemoteException {
        int tipo1=-1;
        this.getMeta2Bean().setLista(this.lista);
        this.getMeta2Bean().setEleicao(this.eleicao);
        if(tipoLista.equals("Docentes")){
            tipo1 = 2;
        }else if(tipoLista.equals("Alunos")){
            tipo1 = 1;
        }else if(tipoLista.equals("Funcionários")){
            tipo1 = 3;
        }else{
            return NONE;
        }

        this.getMeta2Bean().setTipo(tipo1);

        if(this.getMeta2Bean().getCriaLista()){
            return SUCCESS;
        }else{
            this.getMeta2Bean().setErro("Não foi possível adicionar essa lista a essa eleição!");
            return ERROR;
        }


    }
    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(String tipo) {
        this.tipoLista = tipo;
    }

    public String getEleicao() {
        return eleicao;
    }

    public void setEleicao(String eleicao) {
        this.eleicao = eleicao;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }


    public Meta2Bean getMeta2Bean() {
        if(!session.containsKey("meta2Bean"))
            this.setMeta2Bean(new Meta2Bean());
        return (Meta2Bean) session.get("meta2Bean");
    }

    public void setMeta2Bean(Meta2Bean bean) {
        this.session.put("meta2Bean", bean);
    }


}
