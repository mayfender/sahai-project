package com.may.ple.sahai.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.domain.SearchVatReq;
import com.may.ple.sahai.domain.SearchVatResp;
import com.may.ple.sahai.domain.Vat;

@Component
@Path("vatAction")
public class VatAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(VatAction.class.getName());
	
	@POST
    @Path("/searchVat")
	public SearchVatResp searchVat(SearchVatReq req) {
		SearchVatResp resp = null;
		
		try {
			log.debug("Start");
			
			// 1.
			validateReq(req);
			
			log.debug("req : " + req.toString());
			
			List<Vat> list = new ArrayList<Vat>();
			
			// 2.
			Vat vat = new Vat();
			vat.setVatAddress("59/8 ม.1 ต.ทุ่งไทรทอง");
			vat.setVatDocNo("SH-12-45");
			vat.setVatPayCondition("เงินสด");
			vat.setVatPayDate("19/04/2528");
			vat.setVatDueDate("21/09/2528");
			vat.setCompanyName("Hitech Nakhon");
			list.add(vat);
			
			vat = new Vat();
			vat.setVatAddress("59/8 ม.1 ต.ทุ่งไทรทอง 2");
			vat.setVatDocNo("SH-12-46");
			vat.setVatPayCondition("เงินสด");
			vat.setVatPayDate("23/02/2529");
			vat.setVatDueDate("08/11/2529");
			vat.setCompanyName("Hitech Nakhon");
			list.add(vat);			
			
			resp = new SearchVatResp();
			resp.setVatLst(list);
			
		} catch (Exception e) {
			log.error(e.toString());
			resp = new SearchVatResp();
			resp.setStatus("1");
		}
		
		log.debug("End");
		return resp;
	}

}
