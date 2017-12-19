package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class CriarDepartamento extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String faculdade = null, departamento = null;

    @Override
    public String execute() throws RemoteException {

        if(this.faculdade!= null && this.departamento!=null) {
            this.getMeta2Bean().setFaculdade(this.faculdade);
            this.getMeta2Bean().setDepartamento(this.departamento);
            if(this.getMeta2Bean().getCriaDepartamento()){
                System.out.println("Criou departamento!");
                return SUCCESS;
            }else{
                System.out.println("Não criou departamento!");
                return NONE;
            }
        }
        return NONE;
    }


    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
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
