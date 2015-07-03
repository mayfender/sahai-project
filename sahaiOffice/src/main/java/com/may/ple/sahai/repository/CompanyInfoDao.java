package com.may.ple.sahai.repository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@Repository
public class CompanyInfoDao {
	private static final Logger log = Logger.getLogger(CompanyInfoDao.class.getName());
	private static final String dbName = "mayfender";
	private static final String collection = "user";
	private final MongoDbFactory mongo;
	
	@Autowired
	public CompanyInfoDao(MongoDbFactory mongo) {
		this.mongo = mongo;
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
	
}
