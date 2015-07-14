package com.may.ple.sahai;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {
	 private static Logger log = Logger.getLogger(Application.class.getName());
	
	// Entry point for application
	public static void main(String[] args) {
		log.debug("Start by main method");
		SpringApplication.run(Application.class, args);
	}
	
	// Entry point Servlet Engine
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		log.debug("######## Start by SpringBootServletInitializer");
		return builder.sources(Application.class);
	}
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
		
		@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	    }
		

		/*@Autowired
		private SecurityProperties security;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin().loginPage("/app/components/index.html").failureUrl("/login?error").permitAll();
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER").and().withUser("user").password("user").roles("USER");
		}*/

	}
	
	@PostConstruct
	public void init() {
		logger.debug("###### Application initial.....");
	}

}
