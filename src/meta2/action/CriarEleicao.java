package meta2.action;

import com.opensymphony.xwork2.ActionSupport;
import meta2.model.Meta2Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;

public class CriarEleicao extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String departamento = null,descrição = null, eleicao = null, tipoElei = null;
    private int diaInicio, anoInicio, mesInicio, diaFim, anoFim, mesFim,horaInicio, minutosInicio, horaFim, minutosFim;
    private Date dataInicio, dataFim;

    @Override
    public String execute() throws RemoteException {
        dataInicio = new Date(anoInicio, mesInicio, diaInicio,horaInicio,minutosInicio);
        dataFim = new Date(anoFim, mesFim, diaFim,horaFim,minutosFim);
        if(dataInicio.after(dataFim)){
            System.out.println("Não pode ter data de fim anterior a data de inicio");
            return NONE;
        }

        this.getMeta2Bean().setData(this.dataInicio);
        this.getMeta2Bean().setDataFim(this.dataFim);
        this.getMeta2Bean().setDescricao(this.descrição);
        this.getMeta2Bean().setEleicao(this.eleicao);
        this.getMeta2Bean().setDepartamento(this.departamento);

        if(this.getMeta2Bean().getCriaEleicao(this.tipoElei)){
            System.out.println("Criou Eleicao!!");
            return SUCCESS;
        }else{
            System.out.println("NONE");
            return NONE;
        }

    }

    public String getTipoElei() {
        return tipoElei;
    }

    public void setTipoElei(String tipoElei) {
        this.tipoElei = tipoElei;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getMinutosInicio() {
        return minutosInicio;
    }

    public void setMinutosInicio(int minutosInicio) {
        this.minutosInicio = minutosInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }

    public int getMinutosFim() {
        return minutosFim;
    }

    public void setMinutosFim(int minutosFim) {
        this.minutosFim = minutosFim;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getEleicao() {
        return eleicao;
    }

    public void setEleicao(String eleicao) {
        this.eleicao = eleicao;
    }

    public String getTipo() {
        return tipoElei;
    }

    public void setTipo(String tipo) {
        this.tipoElei = tipo;
    }

    public int getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(int diaInicio) {
        this.diaInicio = diaInicio;
    }

    public int getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(int anoInicio) {
        this.anoInicio = anoInicio;
    }

    public int getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(int mesInicio) {
        this.mesInicio = mesInicio;
    }

    public int getDiaFim() {
        return diaFim;
    }

    public void setDiaFim(int diaFim) {
        this.diaFim = diaFim;
    }

    public int getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(int anoFim) {
        this.anoFim = anoFim;
    }

    public int getMesFim() {
        return mesFim;
    }

    public void setMesFim(int mesFim) {
        this.mesFim = mesFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
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
