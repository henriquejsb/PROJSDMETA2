package meta2.action;
import java.util.Date;
import java.util.Map;
import java.rmi.RemoteException;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import static com.opensymphony.xwork2.Action.SUCCESS;


public class RegisterAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null, cpassword = null, departamento = null,morada = null;
    private int tipo, numero, telefone, dia, mes, ano;
    // private Date data;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String execute() throws RemoteException{
        System.out.println("-----------");
        // System.out.println("User:"+this.username);
        //System.out.println(this.password+"-"+this.cpassword+"\n"+this.dia+"/"+this.mes+"/"+this.ano);
        //data = new Date(this.ano, this.mes, this.dia);
        return SUCCESS;

        /*
        if(this.username!= null && this.password!=null && this.cpassword!=null && this.password.equals(this.cpassword)){
            this.getMeta2Bean().setUsername(this.username);
            this.getMeta2Bean().setPassword(this.password);
            this.getMeta2Bean().setPasswordc(this.cpassword);
            if(this.getMeta2Bean().getRegister()){
                return SUCCESS;
            }
            else{
                return NONE;
            }
        }
        else
            return NONE;*/
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

