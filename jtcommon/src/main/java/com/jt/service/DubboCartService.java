package com.jt.service;

import com.jt.pojo.Cart;

import java.util.List;

/**
 * Created by Administrator on 2019/7/19.
 */
public interface DubboCartService {
    List<Cart> findCartListByUserId(Long userId);

    void delectCartItem(Cart cart);

    void addCartItem(Cart cart);

    void updateCartItemNum(Cart cart);
}
