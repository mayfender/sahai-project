package com.may.ple.sahai.web;

import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.domain.CommonResp;
import com.may.ple.sahai.domain.SearchVatReq;
import com.may.ple.sahai.domain.SearchVatResp;
import com.may.ple.sahai.domain.Vat;
import com.may.ple.sahai.repository.VatDao;
import com.may.ple.sahai.service.VatService;
import com.mongodb.BasicDBObject;

@Component
@Path("vatAction")
public class VatAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(VatAction.class.getName());
	private VatDao vatDao;
	private VatService vatService;
	
	@Autowired
	public VatAction(VatDao vatDao, VatService vatService) {
		this.vatDao = vatDao;
		this.vatService = vatService;
	}
	
	@POST
    @Path("/searchVat")
	public SearchVatResp searchVat(SearchVatReq req) {
		SearchVatResp resp = new SearchVatResp();
		
		try {
			log.debug("Start");
			
			// 1.
			validateReq(req);
			log.debug("req : " + req.toString());
			
			// 2.
			BasicDBObject dbObject = vatService.prepareSearchVat(req);
			Map<String, Object> searchVat = vatDao.searchVat(dbObject, req.getCurrentPage(), req.getItemsPerPage());
			resp.setVatLst((List<Vat>)searchVat.get("vatLst"));
			resp.setTotalItems((Long)searchVat.get("totalItems"));
			
			log.debug("resp : " + resp);
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("End");
		return resp;
	}
	
	@POST
    @Path("/saveVatIn")
	public CommonResp saveTask(Vat req) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. 
			BasicDBObject dbObj = vatService.prepareSaveVat(req, 1);
			
			// 3.
			vatDao.saveVatIn(dbObj);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}

}
