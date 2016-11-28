package com.duayres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.duayres.security.AppUserDetailsService;
import com.duayres.security.DesafioBasicAuthenticationEntryPoint;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
        .authorizeRequests().anyRequest().authenticated()
        .antMatchers("/user/**").hasRole("ADMIN")
        .and().httpBasic().realmName("REALM_DESAFIO").authenticationEntryPoint(getBasicAuthEntryPoint())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    
    @Bean
    public DesafioBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new DesafioBasicAuthenticationEntryPoint();
    }
    
    /*
     * Ignorar auth na tela de login, assets e webjars
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        	.antMatchers(HttpMethod.OPTIONS, "/**")
        	.antMatchers("/")
        	.antMatchers("/login")
        	.antMatchers("/webjars/**")
        	.antMatchers("/assets/**");
        
    }

}
