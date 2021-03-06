package com.may.ple.sahai.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import com.may.ple.sahai.domain.BuySaleTaskReq;
import com.may.ple.sahai.domain.ItemInfo;
import com.may.ple.sahai.domain.TaskInfoResp;
import com.may.ple.sahai.domain.Vat;
import com.may.ple.sahai.utils.DbDataconvert;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class TaskDao {
	private static final Logger log = Logger.getLogger(TaskDao.class.getName());
	public static final String collection = "task";
	private static final String dbName = "sahai-back-office";
	private MongoDbFactory mongo;
	
	@Autowired
	public TaskDao(MongoDbFactory mongo) {
		this.mongo = mongo;
	}
		
	public int countByCurrentDate() throws Exception {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject project = new BasicDBObject()
			.append("$project", new BasicDBObject("year", new BasicDBObject("$year", "$createdDateTime"))
			.append("month", new BasicDBObject("$month", "$createdDateTime")).append("isDeleted", "$isDeleted"));
			
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH);
			
			BasicDBObject match = new BasicDBObject();
			match.put("$match", new BasicDBObject("year", year).append("month", month + 1).append("isDeleted", new BasicDBObject("$ne", true)));
			
			AggregationOutput output = coll.aggregate(project, match);
			
			return ((List)output.results()).size();
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
	public void saveTask(BasicDBObject dbObject) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			coll.insert(dbObject);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
	public List<TaskInfoResp> showTasks(String jobId) {
		List<TaskInfoResp> result = null;
		DBCursor cursor = null;
		
		try {
			
			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject query = new BasicDBObject("jobId", jobId)
			.append("isDeleted", new BasicDBObject("$ne", true));
			
			BasicDBObject fields = new BasicDBObject();
			fields.put("docNo", 1);
			fields.put("docType", 1);
			fields.put("createdDateTime", 1);
			fields.put("contactPersonName", 1);
			fields.put("updatedBy", 1);
			fields.put("companyName", 1);
			
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("createdDateTime", 1); 
			
			cursor = coll.find(query, fields).sort(orderBy);
			result = new ArrayList<TaskInfoResp>();
			TaskInfoResp task = null;
			DBObject obj;
			
			while (cursor.hasNext()) {
				obj = cursor.next();
				
				task = new TaskInfoResp();
				task.setId(String.valueOf(obj.get("_id")));
				task.setDocNo(DbDataconvert.<String>getValueByType(obj.get("docNo")));
				task.setDocType(DbDataconvert.<String>getValueByType(obj.get("docType")));	
				task.setContactPersonName(DbDataconvert.<String>getValueByType(obj.get("contactPersonName")));	
				task.setUserName(DbDataconvert.<String>getValueByType(obj.get("updatedBy")));	
				task.setCompanyName(DbDataconvert.<String>getValueByType(obj.get("companyName")));	
				task.setCreatedDateTime(String.format("%1$td-%1$tm-%1$tY %1$tT", DbDataconvert.<Date>getValueByType(obj.get("createdDateTime"))));
				result.add(task);
			}
			
			return result;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}
	
	public BuySaleTaskReq findTask(String taskId) {
		DBCursor cursor = null;
		BuySaleTaskReq result = null;
		
		try {
			
			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject query = new BasicDBObject("_id", new ObjectId(taskId));
			BasicDBObject fields = new BasicDBObject();
			fields.put("docNo", 1);
			fields.put("docType", 1);
			fields.put("companyName", 1);
			fields.put("address", 1);
			fields.put("createdDateTime", 1);
			fields.put("contactPersonName", 1);
			fields.put("contactPersonTel", 1);
			fields.put("contactPersonFax", 1);
			fields.put("updatedBy", 1);
			fields.put("userTel", 1);
			fields.put("comments", 1);
			fields.put("payCondition", 1);
			fields.put("placeToSend", 1);
			fields.put("dateToSend", 1);
			fields.put("items", 1);
			fields.put("firstPrice", 1);
			fields.put("discount", 1);
			fields.put("afterDiscount", 1);
			fields.put("vat", 1);
			fields.put("totalPrice", 1);
			fields.put("jobId", 1);
			
			cursor = coll.find(query, fields);
			DBObject obj;
			
			if(cursor.hasNext()) {
				obj = cursor.next();
				
				result = new BuySaleTaskReq();
				result.setId(String.valueOf(obj.get("_id")));
				result.setDocNo(DbDataconvert.<String>getValueByType(obj.get("docNo")));	
				result.setDocType(DbDataconvert.<String>getValueByType(obj.get("docType")));	
				result.setCompanyName(DbDataconvert.<String>getValueByType(obj.get("companyName")));	
				result.setAddress(DbDataconvert.<String>getValueByType(obj.get("address")));	
				result.setContactPersonName(DbDataconvert.<String>getValueByType(obj.get("contactPersonName")));	
				result.setContactPersonTel(DbDataconvert.<String>getValueByType(obj.get("contactPersonTel")));	
				result.setContactPersonFax(DbDataconvert.<String>getValueByType(obj.get("contactPersonFax")));	
				result.setUserName(DbDataconvert.<String>getValueByType(obj.get("updatedBy")));	
				result.setUserTel(DbDataconvert.<String>getValueByType(obj.get("userTel")));	
				result.setCreatedDateTime(String.format("%1$td/%1$tm/%1$tY", DbDataconvert.<Date>getValueByType(obj.get("createdDateTime"))));
				result.setComments(DbDataconvert.<String>getValueByType(obj.get("comments")));	
				result.setPayCondition(DbDataconvert.<String>getValueByType(obj.get("payCondition")));	
				result.setPlaceToSend(DbDataconvert.<String>getValueByType(obj.get("placeToSend")));	
				result.setDateToSend(DbDataconvert.<String>getValueByType(obj.get("dateToSend")));	
				result.setFirstPrice(DbDataconvert.<Double>getValueByType(obj.get("firstPrice")));	
				result.setDiscount(DbDataconvert.<Double>getValueByType(obj.get("discount")));	
				result.setAfterDiscount(DbDataconvert.<Double>getValueByType(obj.get("afterDiscount")));	
				result.setVat(DbDataconvert.<Double>getValueByType(obj.get("vat")));	
				result.setTotalPrice(DbDataconvert.<Double>getValueByType(obj.get("totalPrice")));	
				result.setJobId(DbDataconvert.<String>getValueByType(obj.get("jobId")));
				
				//-----------: Find vat
				cursor.close();
				query = new BasicDBObject("taskId", taskId);
				
				fields = new BasicDBObject();
				fields.put("vatAddress", 1);
				fields.put("vatPayCondition", 1);
				fields.put("vatPayDate", 1);
				fields.put("vatPoNo", 1);
				fields.put("vatCreatedDateTime", 1);
				fields.put("vatDocNo", 1);
				fields.put("releaseVatDate", 1);
				
				
				coll = db.getCollection(VatDao.collectionVatInOut);
				cursor = coll.find(query, fields);
				DBObject vatObj;
				
				if(cursor.hasNext()) {
					String dateFormat = "%1$td/%1$tm/%1$tY";
					vatObj = cursor.next();
				
					String id = String.valueOf(vatObj.get("_id"));
					String address = DbDataconvert.<String>getValueByType(vatObj.get("vatAddress"));
					String payCondition = DbDataconvert.<String>getValueByType(vatObj.get("vatPayCondition"));
					String payDate = DbDataconvert.<String>getValueByType(vatObj.get("vatPayDate"));
					String poNo = DbDataconvert.<String>getValueByType(vatObj.get("vatPoNo"));
					String vatCreateDateTime = String.format(dateFormat, DbDataconvert.<String>getValueByType(vatObj.get("vatCreatedDateTime")));
					String vatDocNo = DbDataconvert.<String>getValueByType(vatObj.get("vatDocNo"));
					String releaseVatDate = String.format(dateFormat, DbDataconvert.<Date>getValueByType(vatObj.get("releaseVatDate")));
					
					if(!StringUtils.isBlank(address)) {
						Vat vat = new Vat();
						vat.setId(id);
						vat.setVatAddress(address);
						vat.setVatPayCondition(payCondition);
						vat.setVatDueDate(payDate);
						vat.setVatPoNo(poNo);
						vat.setVatCreatedDateTime(vatCreateDateTime);
						vat.setVatDocNo(vatDocNo);
						vat.setReleaseVatDate(releaseVatDate);
						result.setVatObj(vat);
					}
				}
				//-- end vat
				
				BasicDBList items = (BasicDBList)obj.get("items");
				ItemInfo info;
				for (Object o : items) {
					DBObject d = ((DBObject)o);
					
					info = new ItemInfo();
					info.setPartNo(DbDataconvert.<String>getValueByType(d.get("productNo")));
					info.setItemName(DbDataconvert.<String>getValueByType(d.get("description")));
					info.setUnit(DbDataconvert.<String>getValueByType(d.get("unit")));
					info.setQuantity(DbDataconvert.<Integer>getValueByType(d.get("quantity")));
					info.setUnitPrice(DbDataconvert.<Double>getValueByType(d.get("unitPrice")));
					info.setAmount(DbDataconvert.<Double>getValueByType(d.get("amount")));
					
					result.getItems().add(info);
				}
				
			}
			
			return result;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}
	
	public void update(BasicDBObject dbObj, String id) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject where = new BasicDBObject("_id", new ObjectId(id));
			coll.update(where, dbObj);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
	public void delete(String id, String userName) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject where = new BasicDBObject("_id", new ObjectId(id));
			BasicDBObject dbObj = new BasicDBObject("isDeleted", true)
			.append("updatedBy", userName)
			.append("updatedDateTime", new Date());
			
			BasicDBObject data = new BasicDBObject("$set", dbObj);
			coll.update(where, data);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
}
