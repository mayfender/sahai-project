package com.may.ple.sahai.web;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private VatService vatService;
	private Authentication auth;
	private VatDao vatDao;
	
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
			resp.setSumVatInTotalPrice((String)searchVat.get("sumVatInTotalPrice"));
			resp.setSumVatOutTotalPrice((String)searchVat.get("sumVatOutTotalPrice"));
			resp.setTotalItems((Long)searchVat.get("totalItems"));
			resp.setBuyVat((String)searchVat.get("buyVat"));
			resp.setSaleVat((String)searchVat.get("saleVat"));
			resp.setPayVat((String)searchVat.get("payVat"));			
			
			log.debug("resp : " + resp);
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("End");
		return resp;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * Have to return the saved item for outVat for updating the module dialog.
	 * But the vat object result isn't to be used in vatIn save.
	 */
	@POST
    @Path("/saveVat")
	public Vat saveVat(Vat req) {
		Vat resp = new Vat();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. UserDetail
			auth = SecurityContextHolder.getContext().getAuthentication();
			req.setUserName(auth.getName());
			
			// 3.
			BasicDBObject dbObj;
			if(req.getVatType().equals("1")) {
				dbObj = vatService.prepareVatInSave(req, 1);
				vatDao.save(dbObj);
			} else {
				dbObj = vatService.prepareVatOutSave(req);
				
				// 3.
				if(req.getIsCreatedVat()) {
					vatDao.save(dbObj);
				}else{
					vatDao.update(dbObj, req.getId());
				}
			}
			
			// 4.
			Map<String, Object> searchVat = vatDao.searchVat(new BasicDBObject("taskId", req.getTaskId()), 1, 1);
			resp = ((List<Vat>)searchVat.get("vatLst")).get(0);
			log.debug("resp: " + resp);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}
	
	@GET
    @Path("/findVat")
	public Vat findVat(@QueryParam("vatInId") String vatId) {
		Vat resp;
		
		try {
			log.debug("Start >> " + vatId);
			
			// 1. Validate request criteria
			validateReq(vatId);
			
			// 2.
			resp = vatDao.findVat(vatId);
			
			log.debug("resp >> " + resp);
		} catch (Exception e) {
			log.error(e.toString());
			resp = new Vat();
			resp.setStatus("1");
		}
		return resp;
	}
	
	@POST
    @Path("/updateVat")
	public CommonResp updateVat(Vat req) {
		CommonResp resp = new SearchVatResp();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. UserDetail
			auth = SecurityContextHolder.getContext().getAuthentication();
			req.setUserName(auth.getName());
			
			// 3.
			BasicDBObject dbObj = vatService.prepareVatInSave(req, 2);
			vatDao.update(dbObj, req.getId());
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}

}
