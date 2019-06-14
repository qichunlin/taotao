package com.taotao.jedis;

import com.taotao.content.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试单机版和集群版本(通过配置spring文件)
 */
public class TestJedisClient_Spring {

    //测试单机版
    //@Test
    public void testJedisDanji(){
        //1.初始化spring容器
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        //2.获取实现类实例
        JedisClient jedisClient = act.getBean(JedisClient.class);
        //3.调用方法操作
        jedisClient.set("jedisClientKey","jedisClientValue");
        System.out.println(jedisClient.get("jedisClientKey"));
    }


    //测试redis集群操作(一样的)
    //@Test
    public void testJedisCluster(){
        //1.初始化spring容器
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        //2.获取实现类实例
        JedisClient jedisClient = act.getBean(JedisClient.class);
        //3.调用方法操作
        jedisClient.set("jedisClientClusterKey","jedisClientClusterValue");
        System.out.println(jedisClient.get("jedisClientClusterKey"));
    }

}
