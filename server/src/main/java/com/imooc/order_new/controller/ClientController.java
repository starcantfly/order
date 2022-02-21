package com.imooc.order_new.controller;

import com.imooc.product.client.ProductClient;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

//    @Autowired
//    LoadBalancerClient loadBalancerClient;
//
//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        //1.resttemplate第一种方式（直接使用restTemplate,url写死）
        /*RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8081/msg", String.class);
        log.info("response={}",response);*/

        //2.resttemplate第二种方式（利用loadBalancerClient通过应用名获取URL，然后再使用restTemplate）
        /*RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(),
                serviceInstance.getPort() + "/msg");
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}" + response);*/

        //3、resttemplate第二种方式（将RestTemplate作为bean注入）
//        String response = restTemplate.getForObject("http://PRODUCT/msg",String.class);
        //4、feign实现服务间接口调用
//        String response = productClient.productMsg();
//        log.info("response={}",response);
        return null;
    }

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfoOutput> productInfoOutputList = productClient.listForOrder(Arrays.asList("164103465734242707","157875227953464068"));
        log.info("response={}",productInfoOutputList);
        return "ok";
    }
    @GetMapping("/decreaseStock")
    void decreaseStock(){
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("164103465734242707", 2);
        productClient.decreaseStock(Arrays.asList(decreaseStockInput));
    }
}
