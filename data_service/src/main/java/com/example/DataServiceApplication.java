package com.example;

import com.example.bean.item;
import com.example.bean.largeOrder;
import com.example.bean.order;
import com.example.bean.orderItem;
import com.example.dao.itemRepository;
import com.example.dao.largeOrderRepository;
import com.example.dao.orderRepository;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class DataServiceApplication{
    @Autowired
    private itemRepository itemRepository;

    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private largeOrderRepository largeOrderRepository;

    private static final Logger LOG = Logger.getLogger(DataServiceApplication.class.getName());

    public void insert_item(item item){
        itemRepository.save(item);
    }

    public void insert_order(order order){
        orderRepository.save(order);
    }

    public void insert_large_order(largeOrder largeOrder){
        largeOrderRepository.save(largeOrder);
    }

    public largeOrder get_large_order(String id){
        return largeOrderRepository.findById(id);
    }

    public void update_large_order(largeOrder largeOrder){
        largeOrderRepository.save(largeOrder);
    }

	public static void main(String[] args) {
	    SpringApplication.run(DataServiceApplication.class, args);
	}

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

	@Value("${server.port}")
	String port;

    private  ArrayList<orderItem> json_to_item_list(String itemListStr){
        JSONArray jsonItemList = new JSONArray(itemListStr);
        ArrayList<orderItem> orderItems = new ArrayList<orderItem>();
        for (int i = 0; i < jsonItemList.length(); i++){
            String itemId = jsonItemList.getJSONObject(i).getString("item_id");
            Integer itemNum = jsonItemList.getJSONObject(i).getInt("item_num");
            orderItem orderItem = new orderItem(itemId, itemNum);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private String create_large_order(String user, String addr, order order){
        ArrayList<order> orders = new ArrayList<order>();
        orders.add(order);
        largeOrder largeOrder = new largeOrder(user, addr, orders);
        insert_large_order(largeOrder);
        return largeOrder.id;
    }

    @RequestMapping("/add_item")
    public String add_item(@RequestParam(value = "itemName")String itemName, @RequestParam(value = "price") Integer price){
        if (price <= 0 || itemName == null)
            return "false";
        item newItem = new item(itemName, price);
        System.out.println(itemName);
        System.out.println(price);
        insert_item(newItem);
        return newItem.id;
    }

	@RequestMapping("/buy")
	public String buy(@RequestParam(value = "addr")String addr, @RequestBody order order){
        insert_order(order);
        return create_large_order(order.user, addr, order);
	}

    @RequestMapping("/add_to_large_order")
	public String add_to_large_order(@RequestParam(value ="id")String id, @RequestBody order order){
//		JSONObject orderObj = new JSONObject(order_str);
//		order order = new order();
//		order.user = orderObj.getString("username");
//      order.itemList = json_to_item_list(orderObj.getString("item_list"));
        insert_order(order);
        largeOrder largeOrder = get_large_order(id);
        largeOrder.orders.add(order);
        largeOrder.peopleNum = largeOrder.peopleNum + 1;
        update_large_order(largeOrder);
		return order.id;
	}

    @Bean
    public Sampler defaultSampler(){
        return new Sampler() {
            @Override
            public boolean isSampled(long l) {
                return true;
            }
        };
    }

}
