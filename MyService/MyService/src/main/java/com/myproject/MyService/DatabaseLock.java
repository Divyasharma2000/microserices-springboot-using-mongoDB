package com.myproject.MyService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLock {
	@Autowired 
	private MongoTemplate mongoTemplate;
	
//	public boolean acquireLock(String serviceName) {
//		
//		Query query = new Query(Criteria.where("id").is("lock"));
//		
//		Update update = new Update().set("name",serviceName).set("timestamp", new Date());
//		
//	}

}
