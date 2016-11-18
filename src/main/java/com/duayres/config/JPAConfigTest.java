package com.duayres.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.duayres.model.Agendamento;
import com.duayres.repository.IAgendamentoRepository;

/**
 * boilerplate.. 70% inútil hahaha
 */
@Configuration
@ComponentScan(basePackageClasses = IAgendamentoRepository.class)
@EnableJpaRepositories(basePackageClasses = IAgendamentoRepository.class, enableDefaultTransactions = true)
@EnableTransactionManagement
public class JPAConfigTest {

	@Bean
	public DataSource dataSource() {
		/*JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		return dataSourceLookup.getDataSource("jdbc/agendamento");*/
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl("jdbc:postgresql://localhost:5432/agendamento");
	    dataSource.setUsername("postgres");
	    dataSource.setPassword("123");
	    
	    return dataSource; 
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setShowSql(true);
		//adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return adapter;
	}
	
	/*@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setJpaProperties(additionalProperties());
		factory.setPackagesToScan(Agendamento.class.getPackage().getName());
		factory.afterPropertiesSet();
		return factory.getObject();
	}*/

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	    em.setDataSource(dataSource());
		em.setPackagesToScan(Agendamento.class.getPackage().getName());
	    //JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    em.setJpaVendorAdapter(jpaVendorAdapter());
	    em.setJpaProperties(additionalProperties());
	    return em;
	}
	
	/*@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}*/
	
	
	@Bean
	public JpaTransactionManager transactionManager( EntityManagerFactory entityManagerFactory )
	{
		return new JpaTransactionManager( entityManagerFactory );
	}
	
	private Properties additionalProperties() {
		  Properties properties = new Properties();
		  properties.setProperty("hibernate.hbm2ddl.auto", "update");//create-drop
		  properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
		  properties.setProperty("hibernate.format_sql", "false");
		  properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		  return properties;
	}
}
