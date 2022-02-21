package com.imooc.order_new.service;

import com.imooc.order_new.dto.OrderDTO;

public interface OrderService {
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);
}
