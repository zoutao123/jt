package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/7/19.
 */
@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService {

    @Autowired

    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        return cartList;
    }

    @Override
    public void delectCartItem(Cart cart) {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>(cart);
        cartMapper.delete(cartQueryWrapper);
    }

    /**
     * 思路:
     *      根据user_id
     * @param cart
     */
    @Override
    public void addCartItem(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id",cart.getItemId());
        queryWrapper.eq("user_id",cart.getUserId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);

        if (cartDB == null){
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        }else {
            int num = cartDB.getNum()+cart.getNum();
            Cart cartTemp = new Cart();
            cartTemp.setId(cartDB.getId())
                    .setNum(num)
                    .setUpdated(new Date());
            cartMapper.updateById(cartTemp);
        }


    }

    //应该更新数量
    @Override
    public void updateCartItemNum(Cart cart) {
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum())
                .setUpdated(new Date());
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("item_id",cart.getItemId());
        cartMapper.update(cartTemp,queryWrapper);
    }
}
