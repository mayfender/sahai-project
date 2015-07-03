package com.may.ple.sahai.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.may.ple.sahai.service.CustomerService;

@RestController
public class CustomerController {
	private CustomerService customerServic;

	@Autowired
	public CustomerController(CustomerService customerServic) {
		this.customerServic = customerServic;
	}
	
	@RequestMapping("/hello")
	public String hello() {
		String restult = null;
		
		try {
			restult = customerServic.calculate(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "########## " + restult;			
	}

}
