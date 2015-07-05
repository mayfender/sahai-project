package com.may.ple.sahai.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.domain.SearchVatResp;

@Component
@Path("vatAction")
public class VatAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(VatAction.class.getName());
	
	@GET
    @Path("/searchVat")
	public SearchVatResp searchVat() {
		SearchVatResp resp = null;
		
		try {
			validateReq(null);
		} catch (Exception e) {
			log.error(e.toString());
			resp = new SearchVatResp();
			resp.setStatus("1");
		}
		return resp;
	}

}
