package nxp.west.infobase.nxpwest.filter;

import nxp.west.infobase.nxpwest.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Order(1)
@WebFilter(filterName = "piceaFilter", urlPatterns = "/*" , initParams = {
        @WebInitParam(name = "URL", value = "http://localhost:8080")})
public class MyFilter implements Filter {
    @Autowired
    JsonUtil jsonUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String mapJson = servletRequest.getParameter("mapJson");
        if (mapJson == null || mapJson == "") {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Map<String,String> map = jsonUtil.decodeUTF8JsonToMap(mapJson);
        if (map == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Map<String, String[]> parameterMap = new HashMap<String, String[]>(servletRequest.getParameterMap());

        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            parameterMap.put(key,new String[]{value});
        }
        servletRequest = new ParameterRequestWrapper((HttpServletRequest) servletRequest, parameterMap);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
