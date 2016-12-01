package com.duayres.config.init;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.duayres.config.JPAConfig;
import com.duayres.config.SecurityConfig;
import com.duayres.config.ServiceConfig;
import com.duayres.config.WebConfig;

/**
 * AppInitializer que utilizar uma abstração do WebApplicationInitializer (já implementado, facilitando algumas coisas)
 */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/dwr/*","/" };
	}
	
	
	@Override
	public void onStartup(ServletContext servletContext) {
	    AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
	//scan pela pacote de classes com annotation @Configuration
	    rootAppContext.scan("com.duayres.config");
	    servletContext.addListener(new ContextLoaderListener(rootAppContext));
	    
	    
	    AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
	    webAppContext.setParent(rootAppContext);
	    rootAppContext.scan("com.duayres.config");            // scan dos config tudo
	    
		DwrSpringServlet dwrServlet = new DwrSpringServlet();
		//dwrServlet.set;
		//dwrServlet.setApplicationContext(dispatcherServletContext);//não funga esse carai o.O
		
		ServletRegistration.Dynamic defaultDispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(webAppContext));
		defaultDispatcher.setLoadOnStartup(1);;
		defaultDispatcher.addMapping("/");
		//dispatcherC.setInitParameter("debug", "true");
		
		// Registra e mapeia as urls (raizes) do servlet
		ServletRegistration.Dynamic dwrDispatcher = servletContext.addServlet("dwr-invoker",
				dwrServlet);
		dwrDispatcher.setLoadOnStartup(1);
		dwrDispatcher.addMapping("/dwr/*");
		dwrDispatcher.addMapping("/");
		dwrDispatcher.setInitParameter("debug", "true");
		dwrDispatcher.setInitParameter("allowScriptTagRemoting", "true");	
		dwrDispatcher.setInitParameter("crossDomainSessionSecurity", "false");
		dwrDispatcher.setInitParameter("accessLogLevel", "CALL");
	    
	}
	
	/**
	 * Encoding -> UTF-8
	 */
	@Override
	protected Filter[] getServletFilters()
	{
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		
		return new Filter[] {encodingFilter};
	}
}
	/**
	 * filtro http (como especificado na aula da algaworks)
	 */
	/*@Override
	protected Filter[] getServletFilters() {
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
        return new Filter[] { httpPutFormContentFilter };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}*/

/*}*/
