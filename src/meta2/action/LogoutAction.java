package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class LogoutAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private int cc=-1;

    @Override
    public String execute() throws RemoteException {
        this.cc = this.getMeta2Bean().getCc();
        if(this.cc!= -1){
            this.getMeta2Bean().setCc(0);
            this.getMeta2Bean().setPassword(null);
            session.remove("username", this.cc);
            session.put("loggedin", false);
            System.out.println(this.cc+" acabou de sair");
            return SUCCESS;
        }
        else
            return NONE;
    }

    public Meta2Bean getMeta2Bean() {
        if(!session.containsKey("meta2Bean"))
            this.setMeta2Bean(new Meta2Bean());
        return (Meta2Bean) session.get("meta2Bean");
    }

    public void setMeta2Bean(Meta2Bean meta2Bean) {
        this.session.put("meta2Bean", meta2Bean);
    }
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}