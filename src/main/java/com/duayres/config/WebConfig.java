package com.duayres.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.GlobalFilter;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.extend.Configurator;
import org.directwebremoting.spring.DwrClassPathBeanDefinitionScanner;
import org.directwebremoting.spring.DwrController;
import org.directwebremoting.spring.DwrHandlerMapping;
import org.directwebremoting.spring.SpringConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.duayres.controller.MainController;


//import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
//import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = { MainController.class })
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

	
	/**
	 * Mapeamento de URL's para resources internos
	 * @author Eduardo Ayres
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(1);
		registry.addResourceHandler("/assets/**").addResourceLocations("/WEB-INF/assets/").setCachePeriod(1);
		registry.addResourceHandler("/views/**").addResourceLocations("/WEB-INF/views/").setCachePeriod(1);
	}
	
    @Bean
    public InternalResourceViewResolver jstlViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".html");
        return bean;
    }
	
	/**
	 * Metodo que fixa os headers e identifiers do servidor para a localização brasil (pt_BR)
	 * @return Objeto FixedLocaleResolver
	 * @author Eduardo Ayres
	 */
	@Bean
	public LocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
    
	
	/*
	 * Configuração experimental do DWR começa aqui:
	 * 
	 */
	@Bean
	public DwrController dwrController(ApplicationContext applicationContextx) {

		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContextx
				.getAutowireCapableBeanFactory();
		Map<String, String> configParam = new HashMap<String, String>();
		configParam.put("activeReverseAjaxEnabled", "true");
		configParam.put("allowScriptTagRemoting", "true");
		
		configParam.put("debug", "true");
		

		ClassPathBeanDefinitionScanner scanner = new DwrClassPathBeanDefinitionScanner(beanDefinitionRegistry);
		scanner.addIncludeFilter(new AnnotationTypeFilter(RemoteProxy.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(DataTransferObject.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(GlobalFilter.class));
		scanner.scan("com.duayres.service","com.duayres.model");

		DwrController dwrController = new DwrController();
		dwrController.setDebug(true);
		dwrController.setConfigParams(configParam);

		SpringConfigurator springConfigurator = new SpringConfigurator();
		List<Configurator> configurators = new ArrayList<Configurator>();
		configurators.add(springConfigurator);
		dwrController.setConfigurators(configurators);

		return dwrController;
	}

	@Bean
	public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
		BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
		return beanNameUrlHandlerMapping;
	}

	@Bean
	public DwrHandlerMapping dwrHandlerMapping(DwrController dwrController) {
		Map<String, DwrController> urlMap = new HashMap<String, DwrController>();
		urlMap.put("/dwr/**/*", dwrController);

		DwrHandlerMapping dwrHandlerMapping = new DwrHandlerMapping();
		dwrHandlerMapping.setAlwaysUseFullPath(false);
		dwrHandlerMapping.setUrlMap(urlMap);
		return dwrHandlerMapping;
	}

}
