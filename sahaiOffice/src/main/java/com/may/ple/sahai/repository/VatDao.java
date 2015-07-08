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
	public static final String collectionVatInOut = "vatInOut";
	private static final String dbName = "sahai-back-office";
	private MongoDbFactory mongo;

	@Autowired
	public VatDao(MongoDbFactory mongo) {
		this.mongo = mongo;
	}
	
	/*public static void main(String[] args) {
		testMapReduce();
	}*/
	
	/*public static void testMapReduce() {
		MongoClient dbClient = null;
		
		try {
			String map = "function() { "
					+ "    emit(this._id, {companyName: this.companyName, vatDocNo: this.vatDocNo, vatPayCondition: this.vatPayCondition,"
					+ "    vatUpdatedDateTime: this.vatUpdatedDateTime, totalPrice: this.totalPrice,"
					+ "    vatPayDate: this.vatPayDate, others: this.others}); }";
			
			dbClient = new MongoClient( "localhost" , 27017 );
			DB db = dbClient.getDB(dbName);
			DBCollection coll = db.getCollection(collection);
			DBCollection vatIn = db.getCollection(collectionVatIn);
			
			SearchVatReq req = new SearchVatReq();
			
			BasicDBObject query = new VatService().prepareSearchVat(req);
			
			MapReduceCommand reduceCommand = new MapReduceCommand(coll, map, "", "vatInOut", MapReduceCommand.OutputType.REDUCE, query);
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("value.companyName", 1);
			reduceCommand.setSort(orderBy);
			
			coll.mapReduce(reduceCommand);
			
			reduceCommand = new MapReduceCommand(vatIn, map, "", "vatInOut", MapReduceCommand.OutputType.REDUCE, query);
			
			MapReduceOutput mapReduce = coll.mapReduce(reduceCommand);
			
			
			for (DBObject obj : mapReduce.results()) {
				System.out.println(obj.toString());
			}
			
			mapReduce.drop();
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			if(dbClient != null) dbClient.close();			
		}
	}*/

	public Map<String, Object> searchVat(BasicDBObject dbObj, Integer currentPage, Integer itemsPerPage) {
		Map<String, Object> result = new HashMap<String, Object>();
		DBCursor cursor = null;
		
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collectionVatInOut);
			
			// 1 = show, 0 = not show
			BasicDBObject fields = new BasicDBObject();
			fields.put("companyName", 1);
			fields.put("vatAddress", 1);
			fields.put("vatDocNo", 1);
			fields.put("vatPayCondition", 1);
			fields.put("vatPayDate", 1);
			fields.put("vatUpdatedDateTime", 1);
			fields.put("totalPrice", 1);
			fields.put("vatType", 1);
			fields.put("vatPoNo", 1);
			fields.put("releaseVatDate", 1);
			
			// 1 = asc, -1 = desc
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("releaseVatDate", -1); 
			
			cursor = coll.find(dbObj, fields);
			long totalItems = cursor.count();		
			cursor.skip(itemsPerPage * (currentPage - 1)).limit(itemsPerPage).sort(orderBy);
			
			Vat vat;
			String dateFormat = "%1$td/%1$tm/%1$tY";
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
				vat.setVatCreatedDateTime(String.format(dateFormat, this.<Date>getValueByType(obj.get("vatUpdatedDateTime"))));
				vat.setTotalPrice(String.format("%,.2f", this.<Double>getValueByType(obj.get("totalPrice"))));
				vat.setVatType(this.<String>getValueByType(obj.get("vatType")));
				vat.setVatPoNo(this.<String>getValueByType(obj.get("vatPoNo")));
				vat.setVatAddress(this.<String>getValueByType(obj.get("vatAddress")));
				vat.setReleaseVatDate(String.format(dateFormat, this.<Date>getValueByType(obj.get("releaseVatDate"))));
				
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
	
	public void save(BasicDBObject dbObject) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collectionVatInOut);
			coll.insert(dbObject);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}
	
	public void update(BasicDBObject dbObj, String taskId) {
		try {

			DB db = mongo.getDb(dbName);
			DBCollection coll = db.getCollection(collectionVatInOut);
			
			BasicDBObject where = new BasicDBObject("taskId", taskId);
			coll.update(where, dbObj);

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
