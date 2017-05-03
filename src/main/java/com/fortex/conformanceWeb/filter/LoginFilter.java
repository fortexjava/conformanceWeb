/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortex.conformanceWeb.pojo.User;
import com.fortex.conformanceWeb.service.UserService;

 

/**
 * @author Administrator
 *
 */
@WebFilter(filterName="loginFilter",urlPatterns="/track/*")
public class LoginFilter implements Filter{
	
	@Autowired	
	UserService userService;
	
	public void destroy() {

    }

     public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

    	User user = userService.getCurrentUser((HttpServletRequest) request);
    	if(null == user){
    		((HttpServletResponse)response).sendRedirect("/");
    		return ;
    	}
    	request.setAttribute("user", user);
        chain.doFilter(request, response);
    }

     public void init(FilterConfig config) throws ServletException {

    }
}
