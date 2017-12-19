package meta2.interceptor;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;


public class PessoaInterceptor implements Interceptor{

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();

        if (session.get("loggedin") == null || !((boolean) session.get("loggedin")))
        {
            return Action.LOGIN;
        }
        else if((boolean) session.get("admin")){
            return "ADMIN";
        }
        else
        {
            return invocation.invoke();
        }
    }
}