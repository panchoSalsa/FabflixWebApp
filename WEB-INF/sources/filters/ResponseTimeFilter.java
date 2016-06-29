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
public class ResponseTimeFilter implements Filter {
	protected  FilterConfig config;
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
        long startTime = System.nanoTime();
        chain.doFilter(request, response);
        long endTime = System.nanoTime();
       	long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
        System.out.println("TS time is " + elapsedTime + " nano seconds");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
			// TODO Auto-generated method stub
	this.config = config;
	//System.out.println("hello from filter");

	}

}


