package com.may.ple.sahai.web;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.domain.BuySaleJobReq;
import com.may.ple.sahai.domain.BuySaleJobResp;
import com.may.ple.sahai.domain.CommonResp;
import com.may.ple.sahai.repository.JobDao;
import com.may.ple.sahai.service.JobService;
import com.mongodb.BasicDBObject;

@Component
@Path("jobAction")
public class JobAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(JobAction.class.getName());
	private JobService jobService;
	private JobDao jobDao;
	private Authentication auth;
	
	@Autowired
	public JobAction(JobService jobService, JobDao jobDao) {
		this.jobService = jobService;
		this.jobDao = jobDao;
	}
	
	@POST
    @Path("/saveJob")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommonResp saveJob(BuySaleJobReq req) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. Prepare data for persistence			
			auth = SecurityContextHolder.getContext().getAuthentication();
			
			req.setUserName(auth.getName());
			
			BasicDBObject dbObj = jobService.prepareSaveJob(req);
			
			jobDao.save(dbObj);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}
	
	@POST
    @Path("/searchJob")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BuySaleJobResp searchJob(BuySaleJobReq req) {
		BuySaleJobResp resp = new BuySaleJobResp();	
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. Prepare data for search
			BasicDBObject dbObj = jobService.prepareSearchJob(req);
			
			Map<String, Object> searchResult = jobDao.searchJob(dbObj, req.getCurrentPage(), req.getItemsPerPage());
			resp.setSearchLst((List<BuySaleJobReq>)searchResult.get("searchLst"));
			resp.setTotalItems((Long)searchResult.get("totalItems"));
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@GET
	@Path("/deleteJob")
	public CommonResp deleteJob(@QueryParam("jobId") String jobId, @QueryParam("userName") String userName) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> " + jobId + " -- " + userName);
			
			// 1. Validate request criteria
			validateReq(jobId, userName);
			
			// 2. Process delete
			jobDao.delete(jobId, userName);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@POST
    @Path("/viewJob")
	@Consumes(MediaType.APPLICATION_JSON)
	public BuySaleJobReq viewJob(String req) {
		BuySaleJobReq resp = new BuySaleJobReq();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. Get data
			resp = jobDao.viewJob(req);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@POST
	@Path("/updateJob")
	@Consumes(MediaType.APPLICATION_JSON)
	public CommonResp updateJob(BuySaleJobReq req) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. Process updating
			BasicDBObject dbObj = jobService.prepareUpdateJob(req);
			
			jobDao.update(new BasicDBObject("$set", dbObj), req.getId());
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
}
