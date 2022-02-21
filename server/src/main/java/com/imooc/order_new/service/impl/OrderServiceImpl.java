package com.imooc.order_new.service.impl;

import com.imooc.product.client.ProductClient;
import com.imooc.order_new.dataobject.OrderDetail;
import com.imooc.order_new.dataobject.OrderMaster;
import com.imooc.order_new.dto.OrderDTO;
import com.imooc.order_new.enums.OrderStatusEnum;
import com.imooc.order_new.enums.PayStatusEnum;
import com.imooc.order_new.repository.OrderDetailRepository;
import com.imooc.order_new.repository.OrderMasterRepository;
import com.imooc.order_new.service.OrderService;
import com.imooc.order_new.utils.KeyUtil;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        //查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoOutputList = productClient.listForOrder(productIdList);
        //计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO); //总价
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for (ProductInfoOutput productInfo : productInfoOutputList) {
                if (orderDetail.getProductId().equals(productInfo.getProductId())){
                    //总价=单价*数量
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //扣库存（调用商品服务）
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
