package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class AdicionarMesaVoto extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String eleicao = null,departamento;

    @Override
    public String execute() throws RemoteException {
        System.out.println("---"+this.eleicao);
        System.out.println("---"+this.departamento);
        this.getMeta2Bean().setEleicao(this.eleicao);
        this.getMeta2Bean().setDepartamento(this.departamento);

        if(this.getMeta2Bean().setAdicionarMV()) {
            System.out.println("Adicionou mesa de voto!");
            return SUCCESS;
        }else{
            System.out.println("Não adicionou mesa de voto!");
            return NONE;
        }
    }

    public String getEleicao() {
        return eleicao;
    }

    public void setEleicao(String eleicao) {
        this.eleicao = eleicao;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Meta2Bean getMeta2Bean() {
        if(!session.containsKey("meta2Bean"))
            this.setMeta2Bean(new Meta2Bean());
        return (Meta2Bean) session.get("meta2Bean");
    }

    public void setMeta2Bean(Meta2Bean bean) {
        this.session.put("meta2Bean", bean);
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
