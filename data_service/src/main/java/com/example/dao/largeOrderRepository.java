package com.example.dao;

import com.example.bean.largeOrder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface largeOrderRepository extends MongoRepository<largeOrder, Object> {
    largeOrder findById(String id);
}
