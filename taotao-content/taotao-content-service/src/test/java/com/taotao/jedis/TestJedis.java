package com.taotao.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试单机版和集群版本
 */
public class TestJedis {

    //测试单机版
    //@Test
    public void testJedis(){
        //1.创建jedis对象,需要指定连接的地址和端口
        Jedis jedis = new Jedis("192.168.25.128",6379);
        //2.直接操作redis  set
        jedis.set("key1","value");
        System.out.println(jedis.get("key1"));
        //3.关闭jedis
        jedis.close();
    }

    //测试连接池(推荐)
    //@Test
    public void testJedisPool(){
        //1.创建jedisPool对象,需要指定连接的地址和端口
        JedisPool jedisPool = new JedisPool("192.168.25.128",6379);
        //2.获取jedis对象
        Jedis jedis = jedisPool.getResource();
        //3.操作redis  set
        jedis.set("key2","legend");
        System.out.println(jedis.get("key2"));
        //4.关闭jedis 释放资源到连接池中
        jedis.close();
        //5.关闭连接池
        jedisPool.close();
    }

    //测试redis集群操作
    //@Test
    public void testJedisCluster(){
        //1.创建JedisCluster对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.128",7001));
        nodes.add(new HostAndPort("192.168.25.128",7002));
        nodes.add(new HostAndPort("192.168.25.128",7003));
        nodes.add(new HostAndPort("192.168.25.128",7004));
        nodes.add(new HostAndPort("192.168.25.128",7005));
        nodes.add(new HostAndPort("192.168.25.128",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        //2.直接根据JedisCluster操作jedis集群
        jedisCluster.set("key3","jedisCluster");
        System.out.println(jedisCluster.get("key3"));

        //3.关闭JedisCluster对象   是在应用系统关闭的时候关闭  封装了连接池
        jedisCluster.close();
    }

}
