package com.may.ple.sahai.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import com.may.ple.sahai.domain.BuySaleInfoReq;
import com.may.ple.sahai.domain.BuySaleJobReq;
import com.may.ple.sahai.domain.ItemInfo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class JobDao {
	private static final Logger log = Logger.getLogger(JobDao.class.getName());
	private static final String dbName = "sahai-back-office";
	private static final String collection = "buy-sale";
	private final MongoDbFactory mongo;
	
	@Autowired
	public JobDao(MongoDbFactory mongo) {
		this.mongo = mongo;
	}

	public void saveJob(BasicDBObject dbObject) {
		try {	
			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			coll.insert(dbObject);
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
	public void save(BasicDBObject dbObject) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			coll.insert(dbObject);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
	public void update(BasicDBObject dbObj, String itemId) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject where = new BasicDBObject("_id", new ObjectId(itemId));
			coll.update(where, dbObj);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	public Map<String, Object> searchJob(BasicDBObject dbObj, Integer currentPage, Integer itemsPerPage) {
		Map<String, Object> result = new HashMap<String, Object>();
		DBCursor cursor = null;
		
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			// 1 = show, 0 = not show
			BasicDBObject fields = new BasicDBObject();
			fields.put("companyName", 1);
			fields.put("jobName", 1);
			fields.put("createdBy", 1);
			fields.put("createdDateTime", 1);
			
			// 1 = asc, -1 = desc
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("createdDateTime", -1); 
			
			cursor = coll.find(dbObj, fields);
			long totalItems = cursor.count();		
			cursor.skip(itemsPerPage * (currentPage - 1)).limit(itemsPerPage).sort(orderBy);
			
			BuySaleJobReq searchInfo;
			List<BuySaleJobReq> searchLst = new ArrayList<BuySaleJobReq>();
			DBObject obj;
			
			while (cursor.hasNext()) {
				obj = cursor.next();
				
				searchInfo = new BuySaleJobReq();
				searchInfo.setId(String.valueOf(obj.get("_id")));
				searchInfo.setCompanyName(this.<String>getValueByType(obj.get("companyName")));
				searchInfo.setJobName(this.<String>getValueByType(obj.get("jobName")));
				searchInfo.setUserName(this.<String>getValueByType(obj.get("createdBy")));
				searchInfo.setCreatedDateTime(String.format("%1$td/%1$tm/%1$tY %1$tT", this.<Date>getValueByType(obj.get("createdDateTime"))));
				
				searchLst.add(searchInfo);
			}
			
			result.put("searchLst", searchLst);
			result.put("totalItems", totalItems);
			
			return result;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}
	
	/*public Map<String, Object> search(BasicDBObject dbObj, Integer currentPage, Integer itemsPerPage) {
		Map<String, Object> result = new HashMap<String, Object>();
		MongoClient mongoClient = null;
		DBCursor cursor = null;
		
		try {
			mongoClient = getConn();
			
			DB db = mongoClient.getDB(dbName);
			DBCollection coll = db.getCollection(collection);
			
			// 1 = show, 0 = not show
			BasicDBObject fields = new BasicDBObject();
			fields.put("docNo", 1);
			fields.put("docType", 1);
			fields.put("companyName", 1);
			fields.put("contactPersonName", 1);
			fields.put("userName", 1);
			fields.put("createdDateTime", 1);
			
			// 1 = asc, -1 = desc
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("createdDateTime", -1); 
			
			cursor = coll.find(dbObj, fields);
			long totalItems = cursor.count();		
			cursor.skip(itemsPerPage * (currentPage - 1)).limit(itemsPerPage).sort(orderBy);
			
			BuySaleSearchInfo searchInfo;
			List<BuySaleSearchInfo> searchLst = new ArrayList<BuySaleSearchInfo>();
			
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				
				searchInfo = new BuySaleSearchInfo();
				searchInfo.setId(String.valueOf(obj.get("_id")));
				searchInfo.setDocNo(this.<String>getValueByType(obj.get("docNo")));
				searchInfo.setDocType(this.<String>getValueByType(obj.get("docType")));
				searchInfo.setCompanyName(this.<String>getValueByType(obj.get("companyName")));
				searchInfo.setContactPersonName(this.<String>getValueByType(obj.get("contactPersonName")));
				searchInfo.setUserName(this.<String>getValueByType(obj.get("userName")));
				searchInfo.setDateTime(String.format("%1$td-%1$tm-%1$tY %1$tT", this.<Date>getValueByType(obj.get("createdDateTime"))));
				
				searchLst.add(searchInfo);
			}
			
			result.put("searchLst", searchLst);
			result.put("totalItems", totalItems);
			
			return result;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		} finally {
			if (cursor != null)
				cursor.close();
			if (mongoClient != null)
				mongoClient.close();
		}
	}*/
	
	public BuySaleJobReq viewJob(String jobId) {
		BuySaleJobReq result = null;
		DBCursor cursor = null;
		
		try {
			
			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject dbObj = new BasicDBObject("_id", new ObjectId(jobId));
			cursor = coll.find(dbObj);
			
			result = new BuySaleJobReq();
			if(cursor.hasNext()) {
				DBObject obj = cursor.next();
				result.setId(jobId);
				result.setCompanyName(this.<String>getValueByType(obj.get("companyName")));
				result.setComments(this.<String>getValueByType(obj.get("comments")));
				result.setUserName(this.<String>getValueByType(obj.get("createdBy")));
				result.setJobName(this.<String>getValueByType(obj.get("jobName")));				
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
	
	
	
	public BuySaleInfoReq view(String itemId) {
		BuySaleInfoReq result = null;
		DBCursor cursor = null;
		
		try {
			
			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
						
			BasicDBObject dbObj = new BasicDBObject("_id", new ObjectId(itemId));
			cursor = coll.find(dbObj);
			
			result = new BuySaleInfoReq();
			if(cursor.hasNext()) {
				DBObject obj = cursor.next();
				result.setDocType(this.<String>getValueByType(obj.get("docType")));
				result.setCompanyName(this.<String>getValueByType(obj.get("companyName")));
				result.setContactPersonName(this.<String>getValueByType(obj.get("contactPersonName")));
				result.setContactPersonTel(this.<String>getValueByType(obj.get("contactPersonTel")));
				result.setContactPersonFax(this.<String>getValueByType(obj.get("contactPersonFax")));
				result.setUserName(this.<String>getValueByType(obj.get("userName")));
				result.setComments(this.<String>getValueByType(obj.get("comments")));
				result.setDiscount(this.<Double>getValueByType(obj.get("discount")));
				
				BasicDBList items = (BasicDBList)obj.get("items");
				ItemInfo info;
				for (Object o : items) {
					DBObject d = ((DBObject)o);
					
					info = new ItemInfo();
					info.setQuantity(this.<Integer>getValueByType((d.get("quantity"))));
					info.setUnitPrice(this.<Double>getValueByType(d.get("unitPrice")));
					
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
	
	public void delete(String itemId, String userName) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			BasicDBObject where = new BasicDBObject("_id", new ObjectId(itemId));
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
	
	
	
	private <T>T getValueByType(Object obj) {
		if(obj == null) 
			return null;
		
		return (T)obj;
	}

}