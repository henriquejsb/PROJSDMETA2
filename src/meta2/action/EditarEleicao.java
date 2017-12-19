package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class EditarEleicao extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String novoNome = null,descrição = null, eleiEdit = null;

    @Override
    public String execute() throws RemoteException {
        this.getMeta2Bean().setEleicao(this.eleiEdit);
        this.getMeta2Bean().setEditElei(novoNome);
        this.getMeta2Bean().setDescricao(this.descrição);

        if(this.getMeta2Bean().getEditarEleicao()){
            System.out.println("Editou Eleicao!!");
            return SUCCESS;
        }else{
            System.out.println("Não editou eleição!");
            return NONE;
        }

    }


    public String getNovoNome() {
        return novoNome;
    }

    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getEleiEdit() {
        return eleiEdit;
    }

    public void setEleiEdit(String eleiEdit) {
        this.eleiEdit = eleiEdit;
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
