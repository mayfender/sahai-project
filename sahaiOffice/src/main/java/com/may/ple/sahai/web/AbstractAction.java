package com.may.ple.sahai.web;

abstract public class AbstractAction {
	
	protected void validateReq(Object ...obj) throws Exception {
		if(obj == null) throw new Exception("Criteria is null");
		
		for (Object object : obj) {
			if(object == null) throw new Exception("Criteria is null");			
		}
	}

}
