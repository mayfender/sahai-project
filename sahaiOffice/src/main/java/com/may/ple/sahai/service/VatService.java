package com.may.ple.sahai.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.may.ple.sahai.domain.SearchVatReq;
import com.may.ple.sahai.domain.Vat;
import com.mongodb.BasicDBObject;

@Service
public class VatService {

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

	public BasicDBObject prepareSaveVat(Vat req, int module) throws Exception {
		BasicDBObject dbObj = new BasicDBObject("companyName", req.getCompanyName())
				.append("docNo", req.getVatDocNo())
				.append("vatCreatedDate", req.getVatCreatedDateTime())
				.append("dueDate", req.getVatDueDate())
				.append("payCondition", req.getVatPayCondition())
				.append("totalPrice", req.getTotalPrice())
				.append("updatedDateTime", new Date())
				.append("others", req.getOthers());
		
		
		if(module == 1) {
			dbObj.append("createdDateTime", new Date());
			dbObj.append("createdBy", req.getUserName());
		}else if(module == 2) {
			dbObj = new BasicDBObject("$set", dbObj);			
		}
		
		return dbObj;
	}

}
