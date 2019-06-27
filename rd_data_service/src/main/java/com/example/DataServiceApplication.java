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


    @RequestMapping("/get_items")
    public String get_items(){
        ArrayList<item> allItemLists = (ArrayList<item>)itemRepository.findAll();
        System.out.println(allItemLists.toString());
        return allItemLists.toString();
    }

    @RequestMapping("/get_large_orders")
    public String get_large_orders(){
        ArrayList<largeOrder> allLargeOrders = (ArrayList<largeOrder>)largeOrderRepository.findAll();
        System.out.println(allLargeOrders.toString());
        return allLargeOrders.toString();
    }

    @RequestMapping("/get_orders")
    public String get_orders() {
        ArrayList<order> orders = (ArrayList<order>) orderRepository.findAll();
        System.out.println(orders.toString());
        return orders.toString();
    }

    @RequestMapping("/get_orders_by_user")
	public String get_orders_by_user(@RequestParam(value = "username")String user) {
        ArrayList<order> orders = (ArrayList<order>) orderRepository.findByUser(user);
        System.out.println(orders.toString());
        return orders.toString();
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
