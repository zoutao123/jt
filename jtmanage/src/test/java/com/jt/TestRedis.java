package com.jt;

import com.jt.config.RedisConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Created by Administrator on 2019/7/10.
 */
public class TestRedis {
    /**
     * 1.Spring整合Redis入门案例
     */
    @Test
    public void testRedis() {
        String host = "192.168.52.130";
        Integer port = 6379;
        Jedis jedis = new Jedis(host, port);
        jedis.set("1903", "刘昱江必死");
        System.out.println(jedis.get("1903"));

        //设定数据的超时时间
        jedis.expire("1903", 999999);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jedis.ttl("1903"));
        jedis.persist("1903");
        System.out.println(jedis.ttl("1903"));
    }

    /**
     * 简化操作数据超时问题
     */
    @Test
    public void testRedis2() {
        Jedis jedis = new Jedis("192.168.52.130", 6379);
        jedis.setex("我是你爸爸", 10, "FCK U");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jedis.ttl("我是你爸爸"));
    }

    /**
     * 3.锁机制用法
     */
    @Test
    public void testRedis3() {
        Jedis jedis = new Jedis("192.168.52.130", 6379);
//        jedis.set("yue","8点");
//        jedis.set("yue","5点");
        jedis.setnx("yue", "8点");
        jedis.setnx("yue", "5点");
        System.out.println(jedis.get("yue"));

    }

    @Test
    public void testRedis4() {
        Jedis jedis = new Jedis("192.168.52.130", 6379);
        jedis.set("yue", "8", "NX", "EX", 10);
//        int a = 1/0;
        jedis.del("yue");
        jedis.set("yue", "8", "NX", "EX", 10);
    }

    @Test
    public void testRedis5() {
        Jedis jedis = new Jedis("192.168.52.130", 6379);
        jedis.hset("chengyukai", "id", "101");
        jedis.hset("chengyukai", "name", "老狗逼");
        jedis.hset("chengyukai", "age", "199");
        System.out.println(jedis.hgetAll("chengyukai"));
    }

    /**
     * 3.List类型
     */
    @Test
    public void testList() {
        Jedis jedis = new Jedis("192.168.52.130", 6379);

        //1.当做队列
        jedis.lpush("list", "1", "2", "3", "4", "5");
        System.out.println(jedis.rpop("list"));
    }

    /**
     * 事务测试
     */
    @Test
    public void testRedis6() {
        Jedis jedis = new Jedis("192.168.52.130", 6379);
        Transaction multi = jedis.multi();
        try {
            multi.set("aa", "aaa");
            multi.set("bb", "bbb");
            multi.set("cc", "ccc");
            multi.exec();
        }catch (Exception e){
            e.printStackTrace();
            multi.discard();
        }
    }



}

