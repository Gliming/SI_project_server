package com.example.dao;

import com.example.bean.order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface orderRepository extends MongoRepository<order, ObjectId> {
    List<order> findByUser(String user);
}
