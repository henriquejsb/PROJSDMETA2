package rmiserver;
import java.util.Date;

/**
 *
 * @author hb
 */
public class Admin extends Pessoa{

    public Admin(String nome, String morada, int numero, int telefone, Date validadeCC, String pass, Departamento dep){
        super(nome,morada,numero,telefone, validadeCC,pass,dep);
    }

    @Override
    public boolean isAdmin(){
        return true;
    }


}
