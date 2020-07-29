package nxp.west.infobase.nxpwest.interceptor;



import nxp.west.infobase.nxpwest.annotation.Admin;
import nxp.west.infobase.nxpwest.annotation.PassToken;
import nxp.west.infobase.nxpwest.annotation.UserLoginToken;
import nxp.west.infobase.nxpwest.exception.LoginException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有登陆注解，没有则跳过认证
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken loginToken = method.getAnnotation(UserLoginToken.class);
            if (loginToken.required()) {
                HttpSession session = httpServletRequest.getSession(false);
                if (session == null){
                    throw new LoginException();
                }
                Object user = session.getAttribute("user");
                if (user == null){
                    throw new LoginException();
                }
            }
        }
        //检查是否有登陆注解，没有则跳过认证
        if (method.isAnnotationPresent(Admin.class)) {
            Admin loginToken = method.getAnnotation(Admin.class);
            if (loginToken.required()) {
                HttpSession session = httpServletRequest.getSession(false);
                if (session == null){
                    throw new LoginException();
                }
                Object user = session.getAttribute("user");
                if (user == null){
                    throw new LoginException();
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}