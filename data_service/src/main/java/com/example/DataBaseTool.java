package com.example;

import com.example.bean.item;
import com.example.bean.largeOrder;
import com.example.bean.order;
import com.example.dao.itemRepository;
import com.example.dao.orderRepository;
import com.example.dao.largeOrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

public class DataBaseTool {
    private itemRepository itemRepository;

    private orderRepository orderRepository;

    private largeOrderRepository largeOrderRepository;

    DataBaseTool(itemRepository ir, orderRepository or, largeOrderRepository lor){
        this.itemRepository = ir;
        this.orderRepository = or;
        this.largeOrderRepository = lor;
    }

    public void run(String... args) throws Exception {
        itemRepository.deleteAll();
        itemRepository.save(new item("qq", 2));
        itemRepository.save(new item("dd", 2));

        item item = itemRepository.findByItemName("qq");
        System.out.println(item.itemName);
        System.out.println(item.price);

        List<item> items = itemRepository.findByPrice(2);
        System.out.println(items.size());

        //repository.deleteAll();

        // save a couple of customers
//        orderRepository.deleteAll();
//		ArrayList<orderItem> orderItemArray = new ArrayList<orderItem>();
//		orderItem orderItem1 = new orderItem("1", 2);
//		orderItem orderItem2 = new orderItem("2", 2);
//        orderItemArray.add(orderItem1);
//        orderItemArray.add(orderItem2);
//        orderRepository.save(new order("zsj", orderItemArray));
//
//		// fetch all customers
//		System.out.println("orderItem found with findAll():");
//		System.out.println("-------------------------------");
//		for (order order : orderRepository.findAll()) {
//			System.out.println(order.user);
//			for (int i = 0; i < order.itemList.size(); i++) {
//                System.out.println(order.itemList.get(i).itemId);
//                System.out.println(order.itemList.get(i).itemNum);
//            }
//		}
//		System.out.println();

        // fetch an individual customer
//		System.out.println("item found with findByFirstName('banana'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByItemName("banana"));
    }
}
