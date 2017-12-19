package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class CriarFaculdade extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String faculdade = null;

    @Override
    public String execute() throws RemoteException {
        if(this.faculdade!= null) {
            this.getMeta2Bean().setFaculdade(this.faculdade);
            if(this.getMeta2Bean().getCriaFaculdade()){
                System.out.println("Criou faculdade "+this.faculdade);
                return SUCCESS;
            }else{
                System.out.println("Não criou faculdade!");
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
