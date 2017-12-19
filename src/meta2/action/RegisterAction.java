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
    private String  password = null, cpassword = null, departamento = null,morada = null,nome = null,tipo = null;
    private int   telefone=-1, dia=-1, mes=-1, ano=-1,numerocc=-1,tipoN;
    private Date data;

    @Override
    public String execute() throws RemoteException{
            if(!password.equals(cpassword)) return NONE;
            if(tipo.equals("Alunos")){
                tipoN = 0;
            }else if(tipo.equals("Docentes")){
                tipoN =1;
            }else if(tipo.equals("Funcionários")){
                tipoN =2;
            }else{
                System.out.println("Erro ao receber tipo!!!!");
                return NONE;
            }
            data = new Date(ano, mes, dia);
            this.getMeta2Bean().setNome(this.nome);
            this.getMeta2Bean().setCc(this.numerocc);
            this.getMeta2Bean().setPassword(this.password);
            this.getMeta2Bean().setTelefone(this.telefone);
            this.getMeta2Bean().setTipo(this.tipoN);
            this.getMeta2Bean().setData(this.data);
            this.getMeta2Bean().setMorada(this.morada);
            this.getMeta2Bean().setDepartamento(this.departamento);
            if(this.getMeta2Bean().getRegisterPessoa()) {
                session.put("cc", this.numerocc);
                session.put("loggedin", true); // this marks the user as logged in
                System.out.println("Criou pessoa! cc-"+this.numerocc);
                return SUCCESS;
            }
            else{
                System.out.println("Não criou pessoa!");
                return NONE;
            }
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumerocc() {
        return numerocc;
    }

    public void setNumerocc(int numerocc) {
        this.numerocc = numerocc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

