package com.may.ple.sahai.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import com.may.ple.sahai.domain.Vat;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class VatDao {
	private static final Logger log = Logger.getLogger(JobDao.class.getName());
	private static final String dbName = "sahai-back-office";
	private static final String collection = "task_db";
	private MongoDbFactory mongo;

	@Autowired
	public VatDao(MongoDbFactory mongo) {
		this.mongo = mongo;
	}

	public Map<String, Object> searchVat(BasicDBObject dbObj, Integer currentPage, Integer itemsPerPage) {
		Map<String, Object> result = new HashMap<String, Object>();
		DBCursor cursor = null;
		
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collection);
			
			// 1 = show, 0 = not show
			BasicDBObject fields = new BasicDBObject();
			fields.put("companyName", 1);
			fields.put("vatDocNo", 1);
			fields.put("vatPayCondition", 1);
			fields.put("vatPayDate", 1);
			fields.put("vatUpdatedDateTime", 1);
			
			// 1 = asc, -1 = desc
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("vatUpdatedDateTime", -1); 
			
			cursor = coll.find(dbObj, fields);
			long totalItems = cursor.count();		
			cursor.skip(itemsPerPage * (currentPage - 1)).limit(itemsPerPage).sort(orderBy);
			
			Vat vat;
			List<Vat> searchLst = new ArrayList<Vat>();
			DBObject obj;
			
			while (cursor.hasNext()) {
				obj = cursor.next();
				
				vat = new Vat();
				vat.setId(String.valueOf(obj.get("_id")));
				vat.setCompanyName(this.<String>getValueByType(obj.get("companyName")));
				vat.setVatDocNo(this.<String>getValueByType(obj.get("vatDocNo")));
				vat.setVatPayCondition(this.<String>getValueByType(obj.get("vatPayCondition")));
				vat.setVatDueDate(this.<String>getValueByType(obj.get("vatPayDate")));
				vat.setVatCreatedDateTime(String.format("%1$td/%1$tm/%1$tY", this.<Date>getValueByType(obj.get("vatUpdatedDateTime"))));
				
				searchLst.add(vat);
			}
			
			result.put("vatLst", searchLst);
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
	
	private <T>T getValueByType(Object obj) {
		if(obj == null) 
			return null;
		
		return (T)obj;
	}

}
