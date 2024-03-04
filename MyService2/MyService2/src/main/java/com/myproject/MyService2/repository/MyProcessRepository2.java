package com.myproject.MyService2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myproject.MyService2.model.MyProcess2;

@Repository
public interface MyProcessRepository2 extends MongoRepository<MyProcess2, String> {

}
