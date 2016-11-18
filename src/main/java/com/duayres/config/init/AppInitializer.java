package com.duayres.config.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

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
/*
public class AppInitializer implements WebApplicationInitializer {


	public void onStartup(ServletContext container) {

		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(JPAConfig.class, ServiceConfig.class);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
		dispatcherServletContext.register(WebConfig.class);

		DwrSpringServlet dwrServlet = new DwrSpringServlet();
		//dwrServlet.setApplicationContext(dispatcherServletContext);
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
				dwrServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/dwr/*");
		dispatcher.addMapping("/");
	}

}
*/


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
	
	
	/*@Override
	public void onStartup(ServletContext servletContext) {
	    AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
	//scan the package of your @Configuration java files
	    root.scan("com.duayres.config");
	    servletContext.addListener(new ContextLoaderListener(root));
	    
	    
	    AnnotationConfigWebApplicationContext webContext1 = new AnnotationConfigWebApplicationContext();
	    webContext1.setParent(root);
	    //root.scan("com.duayres.config");            // scan some other packages
	    //ServletRegistration.Dynamic dispatcher1 = servletContext.addServlet("dwrServletDispatcher", new DwrSpringServlet());
	    ServletRegistration.Dynamic dispatcher1 = servletContext.addServlet("dwrServletDispatcher", new DispatcherServlet(webContext1));

	    dispatcher1.setLoadOnStartup(1);
	    //dispatcher1.setInitParameter("debug", "true");
	    dispatcher1.addMapping("/dwr/*");
	    dispatcher1.addMapping("/");
	    
	    
	}*/
	
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

}
