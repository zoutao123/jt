package com.jt.service;

import com.jt.vo.EasyTree;

import java.util.List;

/**
 * Created by Administrator on 2019/7/3.
 */
public interface ItemTreeService {
    List<EasyTree> findTree(Long id);
    List<EasyTree> findItemCatByCache(Long id);
}
