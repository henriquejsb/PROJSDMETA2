package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class AssociarCandidato extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String eleicao = null,lista = null;
    private int cc;


    @Override
    public String execute() throws RemoteException {
        this.getMeta2Bean().setEleicao(this.eleicao);
        this.getMeta2Bean().setLista(this.lista);
        this.getMeta2Bean().setCc(this.cc);
        if(this.getMeta2Bean().setAssociarPessoa()){
            System.out.println("Associou candidato "+this.cc+"à lista "+this.lista);
            return SUCCESS;
        }else{
            this.getMeta2Bean().setErro("Não foi possível associar esse candidato a essa lista!");
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

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
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
