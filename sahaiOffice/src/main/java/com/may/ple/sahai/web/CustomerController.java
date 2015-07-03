package com.may.ple.sahai.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.may.ple.sahai.service.CustomerService;

@RestController
public class CustomerController {
	private static Logger log = Logger.getLogger(CustomerController.class.getName());
	private CustomerService customerServic;

	@Autowired
	public CustomerController(CustomerService customerServic) {
		this.customerServic = customerServic;
	}
	
	@RequestMapping("/hello")
	public String hello() {
		log.debug("Start hello...");
		String restult = null;
		
		try {
			restult = customerServic.calculate(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "########## " + restult;			
	}

}
