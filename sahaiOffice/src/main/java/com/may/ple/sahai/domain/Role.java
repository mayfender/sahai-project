package com.may.ple.sahai.domain;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	private String rolename;
	
	@Override
	public String getAuthority() {
		return rolename;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
