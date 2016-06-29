package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LogInFilter
 */
//@WebFilter("/LogInFilter")
public class LogInFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
       // boolean userHasLoggedIn = session.getAttribute("user");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("path: **" + path + "**");
//        if (path.equals("/"))
//        	chain.doFilter(request, response);
//        else if (!path.equals("/") && session.getAttribute("user") == null)
//        	res.sendRedirect( req.getContextPath() + "/");
//        else 
//        	chain.doFilter(request, response);

        if (!path.equals("/") && session.getAttribute("user") == null)
        	res.sendRedirect( req.getContextPath() + "/");
        
        chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
