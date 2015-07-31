package com.may.ple.sahai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
    				MongoUserDetailsService mongoUserDetailsService) throws Exception {
		/*auth
        	.inMemoryAuthentication()
            	.withUser("yo").password("123").roles("USER").and()
            	.withUser("may").password("456").roles("USER", "GO");*/
		
		auth
			.userDetailsService(mongoUserDetailsService);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/resResource/**").hasRole("ADMIN")
				.antMatchers("/assets/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/app/components/login/login.html")
				.defaultSuccessUrl("/app/components/index.html", true)
				.failureUrl("/app/components/login/fail.html")
				.permitAll()
				.and()
			.logout().logoutUrl("/");
	}

}
