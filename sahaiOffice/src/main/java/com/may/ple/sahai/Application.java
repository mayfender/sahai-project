package com.may.ple.sahai;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
	
	@PostConstruct
	public void init() {
		logger.debug("###### Application initial.....");
	}

}
