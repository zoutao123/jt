package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2019/7/19.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 3000,check = false)
    private DubboCartService cartService;

    /**
     * 当用户点击购物车按钮时.应该展现用户的购物记录
     * 业务实现：
     *     查询用户购物行为 userId
     *
     * @return
     */
    @RequestMapping("/show")
    public String show(Model model){
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);
        return "cart";  //跳转购物车页面
    }

    /**
     * 如果rest风格接收参数是与对象的属性名称一致则可以使用对象来接收
     * @param
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.delectCartItem(cart);
        return "redirect:/cart/show.html";
    }

    /**
     * 该方法利用页面的表单提交获取参数
     * @param cart
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String addCartItem(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.addCartItem(cart);
        return "redirect:/cart/show.html";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartItemNum(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.updateCartItemNum(cart);
        return SysResult.success();
    }


}
