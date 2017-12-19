package meta2.interceptor;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;


public class LoginInterceptor implements Interceptor{

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();

        if (session.get("loggedin") != null) {
            if (((boolean) session.get("loggedin"))) {
                if ((boolean) session.get("admin")) {
                    return "ADMIN";
                } else {
                    return "PESSOA";
                }
            } else{
                return invocation.invoke();
            }
        }
        else
        {
            return invocation.invoke();
        }
    }
}