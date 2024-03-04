package com.myproject.MyService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myproject.MyService.model.MyProcess;

@Repository
public interface MyProcessRepository extends MongoRepository<MyProcess, String> {

}
