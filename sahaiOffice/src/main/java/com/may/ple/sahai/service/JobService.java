package com.may.ple.sahai.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.may.ple.sahai.domain.BuySaleJobReq;
import com.mongodb.BasicDBObject;

@Service
public class JobService {
	
	public BasicDBObject prepareSaveJob(BuySaleJobReq req) {
		
		BasicDBObject dbObj = new BasicDBObject("companyName", req.getCompanyName())
		.append("jobName", req.getJobName())
		.append("comments", req.getComments())
		.append("createdDateTime", new Date())
		.append("updatedDateTime", new Date())
		.append("createdBy", req.getUserName());
		
		return dbObj;
	}
	
	public BasicDBObject prepareSearchJob(BuySaleJobReq req) throws Exception {
		try {
			BasicDBObject dbObj = new BasicDBObject();
			
			dbObj.append("isDeleted", new BasicDBObject("$ne", true));
			
			if(!StringUtils.isBlank(req.getCompanyName())) {
				dbObj.append("companyName", Pattern.compile(req.getCompanyName(), Pattern.CASE_INSENSITIVE));
			}
			if(!StringUtils.isBlank(req.getJobName())) {
				dbObj.append("jobName", Pattern.compile(req.getJobName(), Pattern.CASE_INSENSITIVE));
			}
			if(!StringUtils.isBlank(req.getUserName())) {
				dbObj.append("createdBy", Pattern.compile(req.getUserName()));
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:SSS");
			BasicDBObject dateTime = null;
			Date date;
			if(!StringUtils.isBlank(req.getDateTimeStart())) {
				date = dateFormat.parse(req.getDateTimeStart() + " 00:00:000");
				dateTime = new BasicDBObject("$gte", date);
			}
			if(!StringUtils.isBlank(req.getDateTimeEnd())) {
				date = dateFormat.parse(req.getDateTimeEnd()+ " 23:59:999");
				
				if(dateTime == null){
					dateTime = new BasicDBObject("$lt", date);					
				}else{
					dateTime.append("$lt", date);
				}
			}
			
			if(dateTime != null) {
				dbObj.append("createdDateTime", dateTime);				
			}
			
			return dbObj;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public BasicDBObject prepareUpdateJob(BuySaleJobReq req) {
		
		BasicDBObject dbObj = new BasicDBObject("companyName", req.getCompanyName())
		.append("jobName", req.getJobName())
		.append("comments", req.getComments())
		.append("updatedDateTime", new Date())
		.append("updatedBy", req.getUserName());
		
		return dbObj;
	}

}
