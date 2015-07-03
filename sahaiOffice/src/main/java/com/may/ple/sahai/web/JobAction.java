/*package com.may.ple.sahai.web;

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

import com.may.ple.sahai.office.dao.JobDao;
import com.may.ple.sahai.office.entity.BuySaleJobReq;
import com.may.ple.sahai.office.entity.BuySaleJobResp;
import com.may.ple.sahai.office.entity.CommonResp;
import com.may.ple.sahai.office.service.JobService;
import com.mongodb.BasicDBObject;

@Path("jobAction")
public class JobAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(JobAction.class.getName());
	
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
			BasicDBObject dbObj = new JobService().prepareSaveJob(req);
			
			new JobDao().save(dbObj);
			
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
			BasicDBObject dbObj = new JobService().prepareSearchJob(req);
			
			Map<String, Object> searchResult = new JobDao().searchJob(dbObj, req.getCurrentPage(), req.getItemsPerPage());
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
			new JobDao().delete(jobId, userName);
			
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
			resp = new JobDao().viewJob(req);
			
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
			BasicDBObject dbObj = new JobService().prepareUpdateJob(req);
			
			new JobDao().update(new BasicDBObject("$set", dbObj), req.getId());
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
}
*/