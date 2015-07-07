package com.may.ple.sahai.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.may.ple.sahai.domain.BuySaleTaskReq;
import com.may.ple.sahai.domain.ItemInfo;
import com.may.ple.sahai.repository.TaskDao;
import com.may.ple.sahai.utils.DocTypeConstantUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@Service
public class TaskService {
	private static final Logger log = Logger.getLogger(TaskService.class.getName());
	private TaskDao taskDao;
	
	@Autowired
	public TaskService(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	public BasicDBObject prepareJobData(BuySaleTaskReq req, int module) throws Exception {
		DocTypeConstantUtil parseType = DocTypeConstantUtil.parseType(Integer.parseInt(req.getDocType()));
		String docNo = "";
		String format;
		
		if(module == 2) {
			format = req.getDocNo().substring(2);
		}else{
			int count = taskDao.countByCurrentDate("createdDateTime") + 1;
			format = String.format("%1$ty%1$tm-" + String.format("%04d", count), new Date());
		}
		
		if(DocTypeConstantUtil.QUOTATION == parseType) {			
			docNo = "QT"+format;
		}else if(DocTypeConstantUtil.QUOTATION_REQUEST == parseType) {
			docNo = "QR"+format;
		}else if(DocTypeConstantUtil.PURCHASE_ORDER == parseType) {
			docNo = "PO"+format;				
		}else{
			throw new Exception("Notfound doctype on : " + req.getDocType());
		}
		
		log.debug("docNo : " + docNo);
		
		BasicDBObject dbObj = new BasicDBObject("docNo", docNo)
		.append("docType", req.getDocType())
		.append("companyName", req.getCompanyName())
		.append("contactPersonName", req.getContactPersonName())
		.append("address", req.getAddress())
		.append("contactPersonTel", req.getContactPersonTel())
		.append("contactPersonFax", req.getContactPersonFax())
		.append("comments", req.getComments())
		.append("payCondition", req.getPayCondition())
		.append("placeToSend", req.getPlaceToSend())
		.append("dateToSend", req.getDateToSend())
		.append("firstPrice", req.getFirstPrice())
		.append("discount", req.getDiscount())
		.append("afterDiscount", req.getAfterDiscount())
		.append("vat", req.getVat())
		.append("updatedBy", req.getUserName())
		.append("userTel", req.getUserTel())
		.append("updatedDateTime", new Date())
		.append("totalPrice", req.getTotalPrice());
		
		if(module == 1) {
			dbObj.append("createdDateTime", new Date());
			dbObj.append("vatCreatedDateTime", new Date(0));
			dbObj.append("createdBy", req.getUserName());
		}
		
		BasicDBList dbObjLst = new BasicDBList();
		BasicDBObject dbObjItem;
		
		if(req.getItems() != null) {
			for (ItemInfo item : req.getItems()) {
				dbObjItem = new BasicDBObject("productNo", item.getPartNo())
				.append("description", item.getItemName())
				.append("quantity", item.getQuantity())
				.append("unit", item.getUnit())
				.append("unitPrice", item.getUnitPrice())
				.append("amount", item.getAmount());
				
				dbObjLst.add(dbObjItem);
			}
		}
		
		dbObj.append("items", dbObjLst);
		
		if(module == 2) {
			dbObj = new BasicDBObject("$set", dbObj);			
		}
		
		return dbObj;
	}

}
