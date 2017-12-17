package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import meta2.model.Meta2Bean;


public class LoginAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String password = null;
    private int cc = -1;

    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public String execute() throws RemoteException {
        if(this.cc!= -1 && this.password!=null){
            this.getMeta2Bean().setCc(this.cc);
            this.getMeta2Bean().setPassword(this.password);
            if(this.getMeta2Bean().getLogin()) {
                session.put("cc", cc);
                session.put("loggedin", true); // this marks the user as logged in
                return SUCCESS;
            }
            else{
                return LOGIN;
            }
        }
        else
            return LOGIN;
    }



    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Meta2Bean getMeta2Bean() {
        if(!session.containsKey("meta2Bean"))
            this.setMeta2Bean(new Meta2Bean());
        return (Meta2Bean) session.get("meta2Bean");
    }

    public void setMeta2Bean(Meta2Bean meta2Bean) {
        this.session.put("meta2Bean", meta2Bean);
    }
}
