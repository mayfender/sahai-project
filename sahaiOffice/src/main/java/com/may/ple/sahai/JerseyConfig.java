package com.may.ple.sahai;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.web.CompanyInfoAction;
import com.may.ple.sahai.web.JobAction;
import com.may.ple.sahai.web.TaskAction;

@Component
@ApplicationPath(value="/resResource")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(CompanyInfoAction.class);
		register(JobAction.class);
		register(TaskAction.class);
	}

}
