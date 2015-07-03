/*package com.may.ple.sahai.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.may.ple.sahai.office.dao.CompanyInfoDao;
import com.may.ple.sahai.office.entity.CompanyInfoReq;
import com.may.ple.sahai.office.entity.CompanyInfoResp;
import com.mongodb.BasicDBObject;

@Path("company")
public class CompanyInfoAction {
	private static final Logger log = Logger.getLogger(CompanyInfoAction.class.getName());
	
	@POST
    @Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CompanyInfoResp search(CompanyInfoReq req) {
		CompanyInfoResp resp = new CompanyInfoResp();
		
		try {
			log.debug("Start");
			
			if(req == null) {
				log.debug("buySaleInfoReq is null");
				resp.setStatus("1");
				return resp;
			}	
			
			log.debug(req);
			
			BasicDBObject dbObj = new BasicDBObject("companyType", req.getCompanyType())
			.append("companyName", req.getCompanyName())
			.append("address", req.getAddress())
			.append("comments", req.getComments());
			
			new CompanyInfoDao().save(dbObj);
			
			log.debug("response : " + resp);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}

}
*/