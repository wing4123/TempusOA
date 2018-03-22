package cn.tempus.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class baseInterceptor extends HandlerInterceptorAdapter {
	protected static Logger logger = LoggerFactory.getLogger(baseInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		Object user = request.getSession().getAttribute("USER");
		String ctxpath = request.getContextPath();
		String URL = request.getRequestURI();
		String path = request.getServletPath();
		String query = request.getQueryString();
		if(user == null) {
			response.sendRedirect(request.getContextPath()+"/login?redirect="+URLEncoder.encode(URL+(query==null?"":"?"+query), "UTF-8"));
			return false;
		}else {
			logger.info(user.toString());
			logger.info(request.getRequestURI());
			Object myright = request.getSession().getAttribute("MYRIGHTURL");
			Object allright = request.getSession().getAttribute("ALLRIGHTURL");
			if("/".equals(path)) {
				response.sendRedirect(ctxpath+"/index");
				return false;
			}else if(user!=null && myright!=null && allright!=null && allright.toString().contains(path) && !myright.toString().contains(path)){
				response.getWriter().write("no power!");
				return false;
			}
			
			return true;
		}
		
	}
}
