package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2019/7/22.
 */
@Controller
@RequestMapping("order")
public class OrderController {
    @Reference(timeout = 3000,check = false)
    private DubboCartService cartService;
    @Reference(timeout = 3000,check = false)
    private DubboOrderService orderService;

    @RequestMapping("/create")
    public String createOrder(Model model){
        //1.获取用户购物车信息
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }

    /**
     * 实现订单入库操作
     *
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order){
        Long userId = UserThreadLocal.get().getId();
        order.setUserId(userId);
        //1.业务要求返回orderId号
        String orderId = orderService.saveOrder(order);

        //2.校验orderId是否有值
        if (StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }

    @RequestMapping("/success")
    public String successOrder(String id,Model model){
        Order order = orderService.findOrderByOrderId(id);
        model.addAttribute("order",order);
        return "success";
    }

    @RequestMapping("/myOrder")
    public String myOrder(){
        return "my-orders";
    }
}
