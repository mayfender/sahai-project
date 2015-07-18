package com.may.ple.sahai.web;

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

import com.may.ple.sahai.domain.BuySaleTaskReq;
import com.may.ple.sahai.domain.BuySaleTaskResp;
import com.may.ple.sahai.domain.CommonResp;
import com.may.ple.sahai.repository.TaskDao;
import com.may.ple.sahai.service.TaskService;
import com.mongodb.BasicDBObject;

@Component
@Path("taskAction")
public class TaskAction extends AbstractAction {
	private static final Logger log = Logger.getLogger(TaskAction.class.getName());
	private TaskService taskService;
	private Authentication auth;
	private TaskDao taskDao;
	
	@Autowired
	public TaskAction(TaskService taskService, TaskDao taskDao) {
		this.taskService = taskService;
		this.taskDao = taskDao;
	}
	
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
			
			// 2. UserDetail
			auth = SecurityContextHolder.getContext().getAuthentication();
			req.setUserName(auth.getName());
			
			// 3. 
			BasicDBObject dbObj = taskService.prepareJobData(req, 1);
			dbObj.append("jobId", req.getJobId());
			
			taskDao.saveTask(dbObj);
			
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
			resp.setTasks(taskDao.showTasks(jobId));
			
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
			resp = taskDao.findTask(taskId);
			
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
			
			// 2. UserDetail
			auth = SecurityContextHolder.getContext().getAuthentication();
			req.setUserName(auth.getName());
			
			// 3.
			BasicDBObject dbObj = taskService.prepareJobData(req, 2);
			taskDao.update(dbObj, req.getId());
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		return resp;
	}
	
	@GET
	@Path("/deleteTask")
	public CommonResp deleteTask(@QueryParam("taskId") String taskId) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> taskId: "+taskId);
			
			// 1. Validate request criteria
			validateReq(taskId);
			
			// 2. UserDetail
			auth = SecurityContextHolder.getContext().getAuthentication();
			
			// 3.
			taskDao.delete(taskId, auth.getName());
			
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
	public CommonResp copyTask(@QueryParam("taskId") String taskId) {
		CommonResp resp = new CommonResp();
		
		try {
			log.debug("Start >> taskId: "+taskId);
			
			// 1. Validate request criteria
			validateReq(taskId);
			
			// 2. UserDetail
			auth = SecurityContextHolder.getContext().getAuthentication();
			
			// 3.
			BuySaleTaskReq buySaleTaskReq = taskDao.findTask(taskId);
			buySaleTaskReq.setUserName(auth.getName());
			
			// 4.
			BasicDBObject dbObj = taskService.prepareJobData(buySaleTaskReq, 1);
			dbObj.append("jobId", buySaleTaskReq.getJobId());
			taskDao.saveTask(dbObj);
			
			resp.setStatus("0");
		} catch (Exception e) {
			log.error(e.toString());
			resp.setStatus("1");
		}
		
		log.debug("response : " + resp);
		return resp;
	}
	
}
