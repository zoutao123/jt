package com.jt.config;

import org.apache.catalina.Host;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2019/7/10.
 */

@PropertySource(value = "classpath:/properties/redis.properties")
@Configuration  //标识我是一个配置类
public class RedisConfig {

    /**
     * 回顾:
     * 1.xml配置文件添加bean标签 远古时期
     * 2.配置类的形式
     * 配置:
     * 将jedis对象交给spring容器管理
     * <p>
     * 利用properties配置文件为属性动态赋值
     */
//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;

//    @Value("${redis.nodes}")
//    private String nodes;
//
//    @Bean
//    public ShardedJedis shardedJedis(){
//        ArrayList<JedisShardInfo> shard = new ArrayList<>();
//        String[] strNodes = nodes.split(","); //[node,node,node]
//        for (String strNode:strNodes)
//        {
//            String[] node = strNode.split(":");
//            String host = node[0];
//            Integer port = Integer.parseInt(node[1]);
//            shard.add(new JedisShardInfo(host,port));
//        }
//        return new ShardedJedis(shard);
//    }
    /**
     * 实现redis哨兵配置
     */
//    @Value("${redis.sentinel.masterName}")
//    private String masterName;
//    @Value("${redis.sentinels")
//    private String nodes;
//
//    @Bean()   //该对象是单例的
//    public JedisSentinelPool jedisSentinelPool(){
//        HashSet<String> sentinels = new HashSet<>();
//        sentinels.add(nodes);
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName,sentinels);
//        return jedisSentinelPool;
//    }
//    @Bean
//    @Scope("prototype")
//    public Jedis jedis(@Qualifier("jedisSentinelPool") JedisSentinelPool jedisSentinelPool){
//        Jedis jedis = jedisSentinelPool.getResource();
//        return jedis;
//    }
    /**
     * 搭建redis集群
     */
    @Value("${redis.nodes}")
    private String nodes;
    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> nodes = getNodes();
        return new JedisCluster(nodes);
    }

    //表示不要有重复数据
    private Set<HostAndPort> getNodes(){
        HashSet<HostAndPort> nodesSets = new HashSet<>();
        String[] strNode = nodes.split(",");
        for(String redisNode :strNode){
            String host = redisNode.split(":")[0];
            int port = Integer.parseInt(redisNode.split(":")[1]);
            HostAndPort hostAndPort = new HostAndPort(host,port);
            nodesSets.add(hostAndPort);
        }
        System.out.println(nodesSets);
        return nodesSets;
    }
}
