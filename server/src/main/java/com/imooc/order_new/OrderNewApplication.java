package com.imooc.order_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.imooc.product.client")
public class OrderNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderNewApplication.class, args);
    }

}
