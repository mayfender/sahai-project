package com.may.ple.sahai.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.may.ple.sahai.domain.SearchVatReq;
import com.may.ple.sahai.domain.Vat;
import com.may.ple.sahai.repository.TaskDao;
import com.mongodb.BasicDBObject;

@Service
public class VatService {
	private TaskDao taskDao;
	
	@Autowired
	public VatService(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	public BasicDBObject prepareSearchVat(SearchVatReq req) throws Exception {
		try {
			BasicDBObject dbObj = new BasicDBObject();

			dbObj.append("isDeleted", new BasicDBObject("$ne", true)).append("vatDocNo",
					new BasicDBObject("$exists", true).append("$ne", null));

			if (!StringUtils.isBlank(req.getCompanyName())) {
				dbObj.append("companyName", Pattern.compile(req.getCompanyName(), Pattern.CASE_INSENSITIVE));
			}
			if (!StringUtils.isBlank(req.getDocNo())) {
				dbObj.append("vatDocNo", Pattern.compile(req.getDocNo(), Pattern.CASE_INSENSITIVE));
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:SSS");
			BasicDBObject dateTime = null;
			Date date;
			if (!StringUtils.isBlank(req.getDateTimeStart())) {
				date = dateFormat.parse(req.getDateTimeStart() + " 00:00:000");
				dateTime = new BasicDBObject("$gte", date);
			}
			if (!StringUtils.isBlank(req.getDateTimeEnd())) {
				date = dateFormat.parse(req.getDateTimeEnd() + " 23:59:999");

				if (dateTime == null) {
					dateTime = new BasicDBObject("$lt", date);
				} else {
					dateTime.append("$lt", date);
				}
			}

			if (dateTime != null) {
				dbObj.append("createdDateTime", dateTime);
			}

			return dbObj;
		} catch (Exception e) {
			throw e;
		}
	}

	public BasicDBObject prepareVatInSave(Vat req, int module) throws Exception {
		BasicDBObject dbObj = new BasicDBObject("companyName", req.getCompanyName())
				.append("vatDocNo", req.getVatDocNo())
				.append("vatCreatedDate", req.getVatCreatedDateTime())
				.append("vatPayDate", req.getVatDueDate())
				.append("vatPayCondition", req.getVatPayCondition())
				.append("totalPrice", Double.parseDouble(req.getTotalPrice()))
				.append("vatUpdatedDateTime", new Date())
				.append("vatType", "1") // 1:vatIn, other:vatOut
				.append("others", req.getOthers());
		
		if(module == 1) {
			dbObj.append("createdDateTime", new Date());
			dbObj.append("createdBy", req.getUserName());
		}else if(module == 2) {
			dbObj = new BasicDBObject("$set", dbObj);			
		}
		
		return dbObj;
	}
	
	public BasicDBObject prepareVatOutSave(Vat req) throws Exception {
		Date currentDate = new Date();
		BasicDBObject dbObj = new BasicDBObject();
		
		try {
			if(req.isCreatedVat()) {
				int count = taskDao.countByCurrentDate("vatCreatedDateTime") + 1;
				String docNo = "SH"+String.format("%1$ty%1$tm-" + String.format("%04d", count), new Date());	
				
				dbObj.append("vatDocNo", docNo);
				dbObj.append("vatCreatedDateTime", currentDate);
				dbObj.append("vatType", "2"); // 1:vatIn, other:vatOut
				dbObj.append("taskId", req.getTaskId()); // 1:vatIn, other:vatOut
			}
		} catch (Exception e) {
			throw e;
		}
		
		dbObj.append("companyName", req.getCompanyName());
		dbObj.append("vatPayDate", req.getVatCreatedDateTime());
		dbObj.append("vatPayCondition", req.getVatPayCondition());
		dbObj.append("totalPrice", req.getTotalPrice());
		dbObj.append("vatUpdatedDateTime", currentDate);
		
		return new BasicDBObject("$set", dbObj);
	}

}
