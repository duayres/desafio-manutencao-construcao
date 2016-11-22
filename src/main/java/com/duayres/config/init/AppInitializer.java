package com.duayres.config.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.directwebremoting.servlet.DwrServlet;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.duayres.config.JPAConfig;
import com.duayres.config.ServiceConfig;
import com.duayres.config.WebConfig;

/**
 * boilerplate.. 80% inútil hahaha
 */

/*public class AppInitializer implements WebApplicationInitializer {


	public void onStartup(ServletContext container) {

		// Cria o contexto da aplicação raiz do spring (e registra as classes de configuração != MVC)
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(JPAConfig.class, ServiceConfig.class);

		// Gerencia o ciclo de vida do contexto da aplicação (raiz)
		container.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
		dispatcherServletContext.register(WebConfig.class);

		DwrServlet dwrServlet = new DwrServlet();
		//dwrServlet.setApplicationContext(dispatcherServletContext);
		
		ServletRegistration.Dynamic dispatcherC = container.addServlet("dispatcher",
				new DispatcherServlet(dispatcherServletContext));
		dispatcherC.setLoadOnStartup(1);;
		dispatcherC.addMapping("/");
		//dispatcherC.setInitParameter("debug", "true");
		
		// Registra e mapeia as urls (raizes) do servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("dwr-invoker",
				dwrServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/dwr/*");
		dispatcher.addMapping("/");
		dispatcher.setInitParameter("debug", "true");
	}

}*/



public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	
	@Override
	public void onStartup(ServletContext servletContext) {
	    AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
	//scan the package of your @Configuration java files
	    rootAppContext.scan("com.duayres.config");
	    servletContext.addListener(new ContextLoaderListener(rootAppContext));
	    
	    
	    AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
	    webAppContext.setParent(rootAppContext);
	    rootAppContext.scan("com.duayres.config");            // scan some other packages
	    
		DwrServlet dwrServlet = new DwrSpringServlet();
		//dwrServlet.setApplicationContext(dispatcherServletContext);//não funga o.O
		
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
