package com.jt.service.impl;

import com.jt.mapper.ItemTreeMapper;
import com.jt.service.ItemTreeService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/3.
 */
@Service
public class ItemTreeServiceImpl implements ItemTreeService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ItemTreeMapper itemTreeMapper;
//    @Autowired
//    private Jedis jedis; //从哨兵池中动态获取

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public List<EasyTree> findItemCatByCache(Long id){
        List<EasyTree> treeList = new ArrayList<>();
        String key = "ITEM_CAT_"+id;
        String result = jedisCluster.get(key);
        if (StringUtils.isEmpty(result)){
            treeList = findTree(id);
            String jsonResult = ObjectMapperUtil.toJson(treeList);
            System.out.println(jsonResult);
            //将数据保存到缓存中
            jedisCluster.set(key,jsonResult);
        } else {
            //表示缓存中有数据,将json串转换为对象
            treeList = ObjectMapperUtil.toObject(result, treeList.getClass());
        }
        return treeList;

    }


    @Override
    public List<EasyTree> findTree(Long id) {
        List<EasyTree> tree = itemTreeMapper.findTree(id);
        for(int i=0;i<tree.size();i++){
            tree.get(i).setText(tree.get(i).getName());//给text属性赋值
            if(tree.get(i).getIsParent()==0){//判断是否是父类
                tree.get(i).setState("open");//不存在则设置为open,并跳过给children属性赋值
                continue;
            }
            tree.get(i).setState("closed");
            long ids = tree.get(i).getId();
            List<EasyTree> tree2 = findTree(ids);
            tree.get(i).setChildren(tree2);
    //
    //            List<EasyTree> tree1 = itemTreeMapper.findTree(ids);
    //            for(int a=0;a<tree1.size();a++){
    //                tree1.get(a).setText(tree1.get(a).getName());
    //            }

        }
        return tree;
    }
}
