package com.imooc.order_new.service.impl;

import com.imooc.order_new.dataobject.OrderMaster;
import com.imooc.order_new.dto.OrderDTO;
import com.imooc.order_new.enums.OrderStatusEnum;
import com.imooc.order_new.enums.PayStatusEnum;
import com.imooc.order_new.repository.OrderDetailRepository;
import com.imooc.order_new.repository.OrderMasterRepository;
import com.imooc.order_new.service.OrderService;
import com.imooc.order_new.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //TODO 查询商品信息(调用商品服务)
        //TODO 计算总价
        //TODO 扣库存（调用商品服务）
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
