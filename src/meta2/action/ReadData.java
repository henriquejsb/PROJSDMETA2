package meta2.action;


import com.opensymphony.xwork2.ActionSupport;

public class ReadData extends ActionSupport {

    private static final long serialVersionUID = -8819352697303500472L;

    private String state, district;
    private String states[], districts[];

    public String[] getDistricts() {
        return districts;
    }

    public void setDistricts(String[] districts) {
        this.districts = districts;
    }

    public String[] getStates() {
        return states;
    }

    public void setStates(String[] states) {
        this.states = states;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        System.out.println("Inside Setter " + state);
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String dbDistricts() {
        System.out.println("Getting Districts for " + state);
        //Do the database code or business logic here.
        districts = new String[5];
        if (state.equalsIgnoreCase("tamilnadu")) {
            districts[0] = "chennai";
            districts[1] = "madurai";
            districts[2] = "trichy";
            districts[3] = "Covai";
            districts[4] = "Pudukkottai";
        } else if (state.equalsIgnoreCase("kerala")) {
            districts[0] = "allappey";
            districts[1] = "trivandrum";
            districts[2] = "kozhikkode";
            districts[3] = "District 4";
            districts[4] = "District 5";
        } else if (state.equalsIgnoreCase("karnataka")) {
            districts[0] = "bangalore";
            districts[1] = "Bommanahalli";
            districts[2] = "Mysore";
            districts[3] = "District 4";
            districts[4] = "District 5";
        } else {
            districts[0] = "District 1";
            districts[1] = "District 2";
            districts[2] = "District 3";
            districts[3] = "District 4";
            districts[4] = "District 5";
        }
        return SUCCESS;
    }

    public String dbStates() {
        //Do the database code or business logic here.
        states = new String[5];
        states[0] = "tamilnadu";
        states[1] = "kerala";
        states[2] = "karnataka";
        states[3] = "delhi";
        states[4] = "kashmir";
        return SUCCESS;
    }
}