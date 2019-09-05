package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2019/7/22.
 */
@Service
public class DubboOrderServiceImpl implements DubboOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    private OrderShipping orderShipping;

    /**
     * 三张表入库
     * 将多表操作转化为单表操作
     * 入库三张表
     *
     * 1.要求返回orderId号
     * 2.要求同时入库
     * 3.三张表分别入库
     * @param order
     * @return
     */
    @Override
    @Transactional
    public String saveOrder(Order order) {

        String orderId = System.currentTimeMillis()+""+order.getUserId();
//        String orderId = UUID.randomUUID().toString().replace("-","");
        Date now = new Date();
        //1.入库订单信息 status 1 表示未付款
        order.setOrderId(orderId).setStatus(1).setCreated(now).setUpdated(now);
        orderMapper.insert(order);
        System.out.println("订单入库成功！");

        //2.订单物流入库
        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId).setCreated(now).setUpdated(now);
        orderShippingMapper.insert(orderShipping);
        System.out.println("订单物流入库成功！");

        //3.订单商品入库
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem:orderItems){
            orderItem.setOrderId(orderId).setCreated(now).setUpdated(now);
            orderItemMapper.insert(orderItem);
        }
        System.out.println("订单入库成功！");
        return orderId;
    }

    @Override
    public Order findOrderByOrderId(String orderId) {
        Order order = orderMapper.selectById(orderId);
        OrderShipping shipping = orderShippingMapper.selectById(orderId);
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<OrderItem>();
        queryWrapper.eq("order_id",orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
        order.setOrderItems(orderItems).setOrderShipping(shipping);
        System.out.println(order);
        return order;
    }
}
