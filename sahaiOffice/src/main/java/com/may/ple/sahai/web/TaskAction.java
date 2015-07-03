/*package com.may.ple.sahai.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.may.ple.sahai.office.dao.TaskDao;
import com.may.ple.sahai.office.entity.BuySaleTaskReq;
import com.may.ple.sahai.office.entity.BuySaleTaskResp;
import com.may.ple.sahai.office.entity.CommonResp;
import com.may.ple.sahai.office.entity.VatSaveReq;
import com.may.ple.sahai.office.service.TaskService;
import com.mongodb.BasicDBObject;

@Path("taskAction")
public class TaskAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(TaskAction.class.getName());
	
	@POST
    @Path("/saveTask")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommonResp saveTask(BuySaleTaskReq req) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2. 
			BasicDBObject dbObj = new TaskService().prepareJobData(req, 1);
			dbObj.append("jobId", req.getJobId());
			
			new TaskDao().saveTask(dbObj);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}
	
	@GET
    @Path("/showTasks")
	public BuySaleTaskResp showTasks(@QueryParam("jobId") String jobId) {
		BuySaleTaskResp resp = new BuySaleTaskResp();	
		
		try {
			log.debug("Start >> "+jobId);
			
			// 1. Validate request criteria
			validateReq(jobId);
			
			// 2.
			resp.setTasks(new TaskDao().showTasks(jobId));
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@GET
	@Path("/findTask")
	public BuySaleTaskReq findTask(@QueryParam("taskId") String taskId) {
		BuySaleTaskReq resp = new BuySaleTaskReq();	
		
		try {
			log.debug("Start >> "+taskId);
			
			// 1. Validate request criteria
			validateReq(taskId);
			
			// 2.
			resp = new TaskDao().findTask(taskId);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@POST
    @Path("/updateTask")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommonResp updateTask(BuySaleTaskReq req) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> "+req);
			
			// 1. Validate request criteria
			validateReq(req);
			
			// 2.
			BasicDBObject dbObj = new TaskService().prepareJobData(req, 2);
			new TaskDao().update(dbObj, req.getId());
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}
	
	@GET
	@Path("/deleteTask")
	public CommonResp deleteTask(@QueryParam("taskId") String taskId, @QueryParam("userName") String userName) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> taskId: "+taskId + " userName:" + userName);
			
			// 1. Validate request criteria
			validateReq(taskId, userName);
			
			// 2.
			new TaskDao().delete(taskId, userName);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@GET
	@Path("/copyTask")
	public CommonResp copyTask(@QueryParam("taskId") String taskId, @QueryParam("userName") String userName) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> taskId: "+taskId + " userName:" + userName);
			
			// 1. Validate request criteria
			validateReq(taskId, userName);
			
			// 2.
			BuySaleTaskReq buySaleTaskReq = new TaskDao().findTask(taskId);
			
			// 3.
			BasicDBObject dbObj = new TaskService().prepareJobData(buySaleTaskReq, 1);
			dbObj.append("jobId", buySaleTaskReq.getJobId());
			new TaskDao().saveTask(dbObj);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
	@POST
    @Path("/saveVat")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public VatSaveReq saveVat(VatSaveReq req) {
		VatSaveReq resp = null;
		
		try {
			log.debug("Start");
			log.debug(req);
			
			BasicDBObject dbObj = new TaskService().prepareVatSave(req);
			
			TaskDao taskDao = new TaskDao();
			
			taskDao.update(dbObj, req.getTaskId());
			resp = taskDao.findVat(req.getTaskId());
		} catch (Exception e) {
			log.error(e.toString());
			resp = new VatSaveReq();
			resp.setStatus("1");
		}
		
		log.debug("End");
		return resp;
	}

}
*/