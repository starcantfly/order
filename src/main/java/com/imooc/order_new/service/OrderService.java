package com.imooc.order_new.service;

import com.imooc.order_new.dto.OrderDTO;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

public interface OrderService {
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);
}
