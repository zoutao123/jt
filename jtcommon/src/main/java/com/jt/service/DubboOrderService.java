package com.jt.service;

import com.jt.pojo.Order;

/**
 * Created by Administrator on 2019/7/22.
 */
public interface DubboOrderService {
    String saveOrder(Order order);

    Order findOrderByOrderId(String orderId);
}
