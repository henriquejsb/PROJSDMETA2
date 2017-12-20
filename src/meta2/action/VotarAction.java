package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import meta2.model.Meta2Bean;


public class VotarAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String voto;


    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public String execute() throws RemoteException {
        Meta2Bean meta2bean = getMeta2Bean();
        meta2bean.setLista(voto);
        if(meta2bean.getVotar())
            return SUCCESS;
        else
            meta2bean.setErro("Não foi possível registar o voto!");
            return ERROR;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
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
