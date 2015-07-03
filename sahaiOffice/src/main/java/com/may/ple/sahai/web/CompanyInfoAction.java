package com.may.ple.sahai.web;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.domain.CompanyInfoReq;
import com.may.ple.sahai.domain.CompanyInfoResp;
import com.may.ple.sahai.repository.CompanyInfoDao;
import com.mongodb.BasicDBObject;

@Component
@Path("company")
public class CompanyInfoAction {
	private static final Logger log = Logger.getLogger(CompanyInfoAction.class.getName());
	private CompanyInfoDao companyInfoDao;
	
	@Autowired
	public CompanyInfoAction(CompanyInfoDao companyInfoDao) {
		this.companyInfoDao = companyInfoDao;
	}
	
	@GET
	@Path("/test")
	public String test() {
		
		log.debug("Start test");
		/*BasicDBObject dbObj = new BasicDBObject("name", "name-a")
		.append("age", "age-b")
		.append("birthDate", new Date());
		
		companyInfoDao.save(dbObj);*/
		log.debug("End test");
		
		return "Success";
	}

	@POST
	@Path("/save")
	public CompanyInfoResp search(CompanyInfoReq req) {
		CompanyInfoResp resp = new CompanyInfoResp();

		try {
			log.debug("Start");

			if (req == null) {
				log.debug("buySaleInfoReq is null");
				resp.setStatus("1");
				return resp;
			}

			log.debug(req);
			
			log.debug("Call testService");

			BasicDBObject dbObj = new BasicDBObject("companyType", req.getCompanyType())
			.append("companyName", req.getCompanyName())
			.append("address", req.getAddress())
			.append("comments", req.getComments());
			
			companyInfoDao.save(dbObj);

			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}

		log.debug("response : " + resp);
		return resp;
	}

}
