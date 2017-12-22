package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public class AjaxCalls extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String eleicao;
    private InputStream inputStream;
    private String cc;


    public String execute() throws RemoteException {
        this.getMeta2Bean().setEleicao(eleicao);
        String res = this.getMeta2Bean().getResultados();
        System.out.println("-"+res+"-");
        try  {
            inputStream = new ByteArrayInputStream(res.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
        }catch(Exception e){
        }
        return SUCCESS;
    }

    public String execute1() throws RemoteException {
        System.out.println("CC-"+this.cc+"-EL-"+this.eleicao);
        int cc1 = Integer.parseInt(this.cc);
        this.getMeta2Bean().setCc(cc1);
        this.getMeta2Bean().setEleicao(this.eleicao);
        String res = this.getMeta2Bean().getVerVotou();
        if( res!=null){
            try {
                inputStream = new ByteArrayInputStream(res.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return SUCCESS;
        }else{
            return NONE;
        }
    }

    public String liveStats() throws RemoteException{
        String res = this.getMeta2Bean().getLiveStats();
        try{
            inputStream = new ByteArrayInputStream(res.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch(Exception e){

        }
        return SUCCESS;
    }

    public String liveEleicao() throws RemoteException{
        this.getMeta2Bean().setEleaux(this.eleicao);
        String res = this.getMeta2Bean().getLiveEleicao();
        try{
            inputStream = new ByteArrayInputStream(res.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch(Exception e){

        }
        return SUCCESS;
    }



    public Meta2Bean getMeta2Bean() {
        if(!session.containsKey("meta2Bean"))
            this.setMeta2Bean(new Meta2Bean());
        return (Meta2Bean) session.get("meta2Bean");
    }

    public void setMeta2Bean(Meta2Bean bean) {
        this.session.put("meta2Bean", bean);
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public String getEleicao() {
        return eleicao;
    }

    public void setEleicao(String eleicao) {
        this.eleicao = eleicao;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}


