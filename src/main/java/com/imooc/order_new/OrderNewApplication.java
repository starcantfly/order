package com.imooc.order_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderNewApplication.class, args);
    }

}
