package com.jt;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2019/7/11.
 */
public class TestRedisSuper {
    /**
     * 测试Redis分片机制
     */
    @Test
    public void testShards(){
        ArrayList<JedisShardInfo> shard = new ArrayList<>();
        String host = "192.168.52.130";
        shard.add(new JedisShardInfo(host,6379));
        shard.add(new JedisShardInfo(host,6380));
        shard.add(new JedisShardInfo(host,6381));
        ShardedJedis jedis = new ShardedJedis(shard);
        jedis.set("1903","分片测试");
        System.out.println("获取数据"+jedis.get("1903"));
    }

    /**
     * 测试redis哨兵机制
     * 说明：连接哨兵时,host和端口写的是哨兵的地址
     *  内部由哨兵将请求转给主机
     */
    @Test
    public void testSentinel(){
        HashSet<String> sentinels = new HashSet<>();
        sentinels.add("192.168.52.130:26379");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",sentinels);
        Jedis jedis = jedisSentinelPool.getResource();
        jedis.set("1903aaa","aaaa");
        System.out.println(jedis.get("1903aaa"));
    }

    /**
     * 测试redis集群
     *
     */
    @Test
    public void testCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.52.130",7000));
        nodes.add(new HostAndPort("192.168.52.130",7001));
        nodes.add(new HostAndPort("192.168.52.130",7002));
        nodes.add(new HostAndPort("192.168.52.130",7003));
        nodes.add(new HostAndPort("192.168.52.130",7004));
        nodes.add(new HostAndPort("192.168.52.130",7005));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("1903","FUCK LYJ");
        System.out.println(jedisCluster.get("1903"));

    }
}
