package nxp.west.infobase.nxpwest.aop;

import nxp.west.infobase.nxpwest.utils.NetworkUtil;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Aspect
@Component
public class UserAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(value = "execution(public * nxp.west.infobase.nxpwest.controller.*.*(..))")
    public void UserAspect() {

    }
    @Before("UserAspect()")
    public void beforeController(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取用户的真实ip
        String ip = NetworkUtil.getIpAddress(request);
        logger.info("URL: "+request.getRequestURL().toString());
        logger.info("HTTP_METHOD: "+request.getMethod());
        logger.info("IP: "+request.getRemoteAddr());
        logger.info("REAL_IP: {}",ip);
        Enumeration<String> enu = request.getParameterNames();

        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            logger.info("name:{},value:{}", name, request.getParameter(name));
        }

    }
    @After("UserAspect()")
    public void doAfterGame(){

    }


    @AfterReturning("UserAspect()")
    public void doAfterReturningGame(){

    }

    @AfterThrowing("UserAspect()")
    public void doAfterThrowingGame(){

    }
}
