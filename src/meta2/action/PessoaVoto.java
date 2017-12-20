package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.Map;

public class PessoaVoto extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private InputStream inputStream;
    private int cc;
    private String eleicao;

    @Override
    public String execute() throws RemoteException {
      this.getMeta2Bean().setCc(cc);
      this.getMeta2Bean().setEleicao(eleicao);
      String aux = this.getMeta2Bean().getVerVotou();
        if( aux!=null){
            inputStream = new ByteArrayInputStream(
                 aux.getBytes(StandardCharsets.UTF_8));
            return SUCCESS;
        }else{
            return NONE;
        }
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