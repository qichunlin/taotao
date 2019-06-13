# Redis & 集群搭建
## 1.Redis介绍
### 1.1什么是NoSql ?
```
	为了解决高并发、高可扩展(集群)、高可用、大数据存储问题而产生的数据库解决方案，就是NoSql数据库。
	NoSql:全称 not only sql,非关系型数据库。可以作为关系型数据库的一个很好的补充。不能替代。
```

### 1.2 NoSql 数据库分类
- 键值(Key-Value)存储数据库
```
	相关产品： Redis、Voldemort、Berkeley DB。
	典型应用：内容缓存，主要用于处理大量数据的高访问负载。
	数据模型：一系列键值对
	优势：快速查询
	劣势：存储的数据缺少结构化

```

- 列存储数据库
```
	典型应用：分布式的文件系统
	数据模型：以列簇式存储，将同一列数据存在一起
	优势：查找速度快，可扩展性强，更容易进行分布式扩展
	劣势：功能相对局限

```

- 文档型数据库
```
	相关产品：CouchDB、MongoDB
	典型应用：Web应用（与Key-Value类似，Value是结构化的）
	数据模型：一系列键值对
	优势：数据结构要求不严格
	劣势：查询性能不高，而且缺乏统一的查询语法
```

- 图形(Graph)数据库
```
	相关数据库：Neo4J、InfoGrid、Infinite Graph
	典型应用：社交网络
	数据模型：图结构
	优势：利用图结构相关算法。
	劣势：需要对整个图做计算才能得出结果，不容易做分布式的集群方案
```

### 1.3 什么是Redis
```
	Redis是用C语言开发的一个开源的高性能键值对（key-value）数据库（nosql），应用在缓存。它通过提供多种键值数据类型来适应不同场景下的存储需求。
```

### 1.4 Redis的应用场景
```
缓存。
分布式集群架构中的session分离。
任务队列。（秒杀、抢购、12306等等）
应用排行榜。（SortedSet）
网站访问统计。
数据过期处理。(expire)

```


## 2.Linux系统安装redis
### 2.1 redis的下载
```
官网地址：http://redis.io/
```

`安装redis需要c语言的编译环境,如果没有gcc需要在线安装。如下命令：`

```
[root@localhost ~]# yum -y install gcc-c++
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612200930842-843745231.png)

- 测试
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612200900403-1877444620.png)

### 2.2 解压缩文件(tar -xvf redis-3.0.0.tar.gz) 并编译安装
- 1
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612193859780-719518466.png)
- 2
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612194053801-2001551260.png)
- 3
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612194016404-265884623.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612194252869-2012450212.png)

- 4 复制配置文件 （cp redis.conf /usr/local/redis/bin）
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612194741034-1818291059.png)

通过 /关键字母来实现快速查找
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612194855187-1016245795.png)


- 5 测试启动redis   （ ./redis-server redis.conf）
- 查看进程  （ps -ef|grep redis）
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612195219480-569427651.png)


- 6  连接客户端
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612195317680-432290520.png)

`注意：如果需要连接一个不在本地的redis客户端需要用下面的命令
./redis-cli -h 192.168.25.128 -p 6379`



## 3.Redis支持的键值数据类型详解
### 3.1 字符串类型(String) :key-value  
- 使用
```
set key value

redis命令不区分大小写，但是key区分的 
redis中的数据都是字符串。
redis是单线程，（不适合存储比较大的数据）
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612202108839-1588157802.png)

- incr 自动递增  前提是要key是数值类型不能使string类型，不然出错
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612202206183-899171760.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612202327437-1043316285.png)



### 3.2 散列类型(hash) :  key-field-value    命令是  h 开头
`相当于一个key 对应一个map (map中又是key- value)，即一个key可以对应多个field`

- 使用
```
hset  key field value  设置值
hget  key field       获取值
hincrby key field num  设置增数量
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612210659610-53245743.png)


### 3.3 列表类型(List)   命令是  l 开头
`List是有顺序可重复(数据结构中的：双链表，队列)
	可作为链表 ，从左添加元素  也可以从右添加元素。`
	
- 使用
```powershell
lpush list1 a b c d    (从左添加元素)
rpush list1 e f g      (从右边添加元素)
lrange list1 0 -1      (从0 到 -1 元素查看：也就表示查看所有)

list1 是定义的集合名

lpop list （从左边取，删除）
rpop list  (从右边取，删除)


```
- push的情况
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612210334381-1644098846.png)

- pop情况
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612211305518-1863263368.png)
`pop弹出元素相当于是删除元素，集合里面是不存在的了`


### 3.4  Set无顺序，不能重复   命令是 s 开头
- 使用
```
sadd set1 a b c d d   (向set1中添加元素) 元素不重复
smembers set1 （查询元素）
srem set1 a （删除元素）
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612211801833-89357325.png)


### 3.5 有序集合类型(SortSet)  SortedSet（zset）
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612212312806-233813052.png)

- 使用
```
zadd zset1 9 a 8 c 10 d 1 e (添加元素 zadd key score member)
如果要查看分数，加上withscores.

zrange zset1 0 -1 (从小到大)

zrevrange zset1 0 -1 (从大到小)

zincrby zset2 score member (对元素member 增加 score)

```

### 3.5 key 命令
```
expire key second  (设置key的过期时间)
ttl key (查看剩余时间)（-2 表示不存在，-1 表示已被持久化，正数表示剩余的时间）
persist key (清除过期时间，也即是持久化 持久化成功体提示 1 不成功0)。
del key: 删除key  
EXISTS key
	若key存在，返回1，否则返回0。
select 0 表示：选择0号数据库。默认是0号数据库

```

## 2.Redis集群搭建
###  2.1 redis-cluster架构图
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612212835323-310437415.png)

#### 架构分析
```
(1)所有的redis节点彼此互联(ping-pone)机制,内部使用二进制协议优化传输速度和带宽

(2)节点的fail是通过集群中超过半数的节点检测失效时才生效.通过投票机制

(3)客户端与redis节点直连,不需要中间proxy层.客户端不需要连接集群所有节点,连接集群中任何一个可用节点即可

(4)redis-cluster把所有的物理节点映射到[0-16383]slot上,cluster 负责维护node<->slot<->value
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612213257879-362852483.png)

### 2.2 Redis集群的搭建	
```
	至少3个节点，为了集群的高可用,为每一个节点增加一个备份机.(6台服务器).

	搭建伪分布式集群方案:在一台机器里面运行6个redis实例.端口需要不同(7001-7006)
```

#### 2.2.1 使用ruby脚本搭建集群。需要ruby的运行环境。
```
yum install ruby
yum install rubygems

需要文件redis-3.0.0.gem
```
```
需要6台redis服务器。搭建伪分布式。
需要6个redis实例。
需要运行在不同的端口7001-7006
```

- 通过rubby安装 (官方推荐)
```
gem install redis-3.0.0.gem
cp redis /usr/local/redis-cluster/redis01 -r
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612214527498-1030991983.png)

#### 2.2.2 复制第一台服务器模板配置
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612214611284-945419599.png)

#### 2.2.3 开启集群
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612214837335-310029691.png)

#### 2.2.4 复制6台服务器 
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612215444788-1297535630.png)

#### 2.2.5 修改其他5个服务器的端口号
省略

#### 2.2.6 启动6台服务器(做一些配置)
```
cd /usr/local/redis-cluster/redis01/bin
 ./redis-server redis.conf

cd /usr/local/redis-cluster/redis02/bin
 ./redis-server redis.conf

cd /usr/local/redis-cluster/redis03/bin
 ./redis-server redis.conf

cd /usr/local/redis-cluster/redis04/bin
 ./redis-server redis.conf

cd /usr/local/redis-cluster/redis05/bin
 ./redis-server redis.conf

cd /usr/local/redis-cluster/redis06/bin
 ./redis-server redis.conf

```
`做一个批处理文件`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612220558813-45862522.png)

- 修改文件的权限 
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612220753179-1014999009.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612221016680-1644754920.png)

#### 2.2.7 使用rubby脚本搭建集群
```
从解压目录下的src下的拷贝redis-trib.rb文件到redis-cluster目录中
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612221642501-2027804118.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612221713000-29621261.png)

- 执行创建
```
表示一个节点做一个备份

./redis-trib.rb create --replicas 1 192.168.25.128:7001 192.168.25.128:7002 192.168.25.128:7003 192.168.25.128:7004 192.168.25.128:7005  192.168.25.128:7006
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612222355188-1881884854.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612222439535-1043581568.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612222312555-1685227806.png)



## 3.测试集群和单机版
`面向接口开发`
- JedisClient

```java
package com.taotao.content.jedis;

/**
 * JedisClient接口
 */
public interface JedisClient {

	String set(String key, String value);//字符串设置值
	String get(String key);//字符串获取值
	Boolean exists(String key);//是否存在key值
	Long expire(String key, int seconds);//设置key的过期时间
	Long ttl(String key);//过期时间点
	Long incr(String key);//自动递增
	Long hset(String key, String field, String value);//hash set方式
	String hget(String key, String field);	//hash get方式
	Long hdel(String key, String... field);//删除hkey
	
}

```

- JedisClientPool 实现类
```java
package com.taotao.content.jedis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单机版实现Jedis接口
 */
public class JedisClientPool implements JedisClient {
	
	@Autowired
	private JedisPool jedisPool;

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
		Long hdel = jedis.hdel(key, field);
		jedis.close();
		return hdel;
	}	

}

```

- applicationContext-redis.xml 配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

	<!--配置注解驱动-->
	<context:annotation-config></context:annotation-config>

	<!-- 配置单机版jedis对象 -->
	<!--<bean class="redis.clients.jedis.JedisPool">
		<constructor-arg name="host" value="192.168.25.128" />
		<constructor-arg name="port" value="6379" />
	</bean>
	<bean class="com.taotao.content.jedis.JedisClientPool" />-->

	<!-- 配置集群版 -->
	<!--两个版本不能同时存在-->
	<bean class="redis.clients.jedis.JedisCluster">
		<constructor-arg name="nodes" >
			<set>
				<bean class="redis.clients.jedis.HostAndPort" >
					<constructor-arg name="host" value="192.168.25.128"/>
					<constructor-arg name="port" value="7001"/>
				</bean>
				<bean class="redis.clients.jedis.HostAndPort" >
					<constructor-arg name="host" value="192.168.25.128"/>
					<constructor-arg name="port" value="7002"/>
				</bean>
				<bean class="redis.clients.jedis.HostAndPort" >
					<constructor-arg name="host" value="192.168.25.128"/>
					<constructor-arg name="port" value="7003"/>
				</bean>
				<bean class="redis.clients.jedis.HostAndPort" >
					<constructor-arg name="host" value="192.168.25.128"/>
					<constructor-arg name="port" value="7004"/>
				</bean>
				<bean class="redis.clients.jedis.HostAndPort" >
					<constructor-arg name="host" value="192.168.25.128"/>
					<constructor-arg name="port" value="7005"/>
				</bean>
				<bean class="redis.clients.jedis.HostAndPort" >
					<constructor-arg name="host" value="192.168.25.128"/>
					<constructor-arg name="port" value="7006"/>
				</bean>
			</set>
		</constructor-arg>
	</bean>
	<bean class="com.taotao.content.jedis.JedisClientCluster" />

</beans>
```


- TestJedisClient_Spring 测试
```java
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
    @Test
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
    @Test
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

```

- 查看单机版的数据
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612230827902-1341724448.png)


- 查看集群的数据
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190612225929394-1352904873.png)


![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613082527165-151757164.png)
