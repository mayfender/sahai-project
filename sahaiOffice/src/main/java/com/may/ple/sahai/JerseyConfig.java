package com.may.ple.sahai;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.web.CompanyInfoAction;

@Component
@ApplicationPath(value="/resResource")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(CompanyInfoAction.class);
	}

}
