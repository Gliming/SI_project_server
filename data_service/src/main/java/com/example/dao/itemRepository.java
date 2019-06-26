package com.example.dao;

import com.example.bean.item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface itemRepository extends MongoRepository<item, ObjectId> {
    item findByItemName(String itemName);
    List<item> findByPrice(Integer price);
}