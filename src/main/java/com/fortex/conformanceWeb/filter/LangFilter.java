/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.filter;

import java.io.IOException;
import java.util.Locale;

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
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author Administrator
 *
 */
@WebFilter(filterName="langFilter",urlPatterns="/*")
public class LangFilter  implements Filter{

	@Autowired
    LocaleResolver localeResolver;
	
	/* (non-Javadoc)
	 * @author Ivan Huo
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @author Ivan Huo
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub		
		String lang =request.getParameter("lang");
		if(null!=lang){
			if(lang.indexOf("_")>0){
				String[]langs = lang.split("_");
				localeResolver.setLocale((HttpServletRequest )request, (HttpServletResponse)response, new Locale(langs[0],langs[1]));
			}
			else
				localeResolver.setLocale((HttpServletRequest )request, (HttpServletResponse)response, new Locale(lang));			
		}
		chain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @author Ivan Huo
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
