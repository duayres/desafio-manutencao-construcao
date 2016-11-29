package com.duayres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.duayres.security.AuthenticationService;

/**
 * Classe de Configuração
 *
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = {AuthenticationService.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private AuthenticationService service;

	/**
	 * Garante que qualquer requisição para a aplicação requer que o usuário seja autenticado;
	 * Permite aos usuários autenticar via formulário com base em login;
	 * Permite aos usuários autenticar com autenticação básica de HTTP.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests()
			
			.antMatchers("/static/**").permitAll()
			
			.anyRequest().authenticated()
			
			.and()
				.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login").permitAll()
	            .usernameParameter("email")
	            .passwordParameter("senha")
	            .defaultSuccessUrl("/currentUser")
	            //.failureUrl("currentUser")
			
			.and()
				.logout()
				.permitAll();
				
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	} 
	
	
    
    /*
     * Ignorar auth na tela de login, assets e webjars
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
    	super.configure(web);
        web.ignoring()
        	.antMatchers(HttpMethod.OPTIONS, "/**")
        	.antMatchers("/webjars/**")
        	.antMatchers("/assets/**");
    }

}