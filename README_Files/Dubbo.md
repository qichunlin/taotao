# Dubbo
## 1.什么是Dubbo
<a href="http://dubbo.apache.org/">Dubbo官方地址</a>
```
DUBBO是一个分布式服务框架,致力于提高性能和透明化的RPC(Remote Procedure Call Protoco)程服务调用方案,是阿里巴巴SOA服务化治理方案的核心框架
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617220925362-210453934.png)

### 单一应用架构
•	当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。
•	此时，用于简化增删改查工作量的 数据访问框架(ORM) 是关键。

### 垂直应用架构
•	当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。
•	此时，用于加速前端页面开发的 Web框架(MVC) 是关键。

### 分布式服务架构 
•	当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。
•	此时，用于提高业务复用及整合的 分布式服务框架(RPC) 是关键。

### 流动计算架构 
•	当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。
•	此时，用于提高机器利用率的 资源调度和治理中心(SOA) 是关键。

>Dubbo就是资源调度和治理中心的管理工具
>Dubbo就是类似于WebService的关于系统之间通信的框架,并可以统计和管理服务之间的调用情况(包括服务被谁调用了,调用的次数是如何,以及服务的使用情况)



## 2.Dubbo的架构
### 节点角色分析说明
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617221639588-1969746902.png)

```
Provicer:暴露服务的服务提供方(一般在服务层的配置文件)
Consumer:调用远程服务的消费方
Register:服务注册于发现的注册中心
Monitor:统计服务的调用次数和调用时间的监控中心
Container:服务运行容器
```

### 调用关系说明
```
0. 服务容器负责启动，加载，运行服务提供者。

1. 服务提供者在启动时，向注册中心注册自己提供的服务。

2. 服务消费者在启动时，向注册中心订阅自己所需的服务。

3. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。

4. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。

5. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。
```

## 3.使用方法
### Spring配置
```
	Dubbo采用全Spring配置方式,透明化接入应用,对应用没有任何API侵入,只需用Spring加载Dubbo的配置即可,Dubbo基于Spring的Schema扩展进行加载
```

- 单一工程中spring的配置文件
```
<!--如果项目中使用的是扫描包实现的话就可以不用写下面的实现类-->
<bean id="xxxService" class="com.xxx.XxxServiceImpl" />
<bean id="xxxAction" class="com.xxx.XxxAction">
	<property name="xxxService" ref="xxxService" />
</bean>

```

- 远程服务
`服务层发布服务`
```
<!--如果项目中使用的是扫描包实现的话就可以不用写下面的实现类-->
<!-- 和本地服务一样实现远程服务 -->
<bean id="xxxService" class="com.xxx.XxxServiceImpl" />
<!-- 增加暴露远程服务配置 -->
<dubbo:service interface="com.xxx.XxxService" ref="xxxService" />
```

`表现层调用服务`
```
<!-- 增加引用远程服务配置 -->
<dubbo:reference id="xxxService" interface="com.xxx.XxxService" />
<!-- 和本地服务一样使用远程服务 -->
<bean id="xxxAction" class="com.xxx.XxxAction">
	<property name="xxxService" ref="xxxService" />
</bean>
```

>在本地服务的基础上,只需做简单的配置,即可完成远程化，在提供方增加暴露服务配置<dubbo:service》，在消费方增加引用服务配置<dubbo:reference 》。

`真实应用服务层和表现层的两者使用方式`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617223318036-226441749.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617233857306-787601442.png)



## 4.注册中心
### zookeeper介绍
```
注册中心负责服务地址的注册与查找,，相当于目录服务,服务提供者和消费者,只在启动时与注册中心交互,注册中心不转发请求,使用dubbo-2.3.3以上版本，官方建议使用zookeeper作为注册中心。Zookeeper是Apacahe Hadoop的子项目，是一个树型的目录服务，支持变更推送，适合作为Dubbo服务的注册中心,工业强度较高(稳定性好),可用于生产环境,并推荐使用。
```

### 环境搭建
#### 导入虚拟机
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617231301879-1899139302.png)

#### 打开后，会有提示 是否 移动 和 复制。选择我已移动即可。
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617231439902-253951394.png)

#### 修改网段
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617231221115-1484511471.png)

 ![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617231539701-1649931494.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617231643911-745325293.png)


#### 使用XShell连接虚拟机
`注意:连接linux时可能会连接不上，先要关闭防火墙`
```
service 命令：只是作用于当前，系统一旦重启就失效。
	service iptables stop

可以通过命令：
	chkconfig  iptables off  #永久关闭防火墙。
```

#### 	Zookeeper的安装
```
第一步：
	安装jdk
第二步：
	解压缩zookeeper压缩包
	tar -xvf zookeeper-XXX.tar.gz
第三步：
	将conf文件夹下zoo_sample.cfg复制一份，改名为zoo.cfg
	cp zoo_sample.cfg zoo.cfg
	
第四步：
	修改配置dataDir属性，指定一个真实目录（进入zookeeper解压目录，创建data目录：mkdir data)

第五步：
	启动zookeeper：bin/zkServer.sh start
	关闭zookeeper：bin/zkServer.sh stop
	查看zookeeper状态：bin/zkServer.sh status
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617230932562-1852919748.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617232307690-306438387.png)


## 5 框架整合
### 添加Dubbo的依赖
```xml
<!-- dubbo相关 -->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>dubbo</artifactId>
	<!-- 排除依赖 -->
	<exclusions>
		<exclusion>
			<groupId>org.springframework</groupId>
			<artifactId>spring</artifactId>
		</exclusion>
		<exclusion>
			<groupId>org.jboss.netty</groupId>
			<artifactId>netty</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<dependency>
	<groupId>org.apache.zookeeper</groupId>
	<artifactId>zookeeper</artifactId>
</dependency>
<dependency>
	<groupId>com.github.sgroschupf</groupId>
	<artifactId>zkclient</artifactId>
</dependency>
```

### 整合思路
#### Dao层
`mybatis整合spring，通过spring管理SqlSessionFactory、mapper代理对象。需要mybatis和spring的整合包，由spring创建数据库连接池。`

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617233310016-132953457.png)

#### Service层
`所有的实现类都放到spring容器中管理。并由spring管理事务；发布dubbo服务`

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617233520539-231800442.png)

#### 表现层web：
`SpringMVC整合Spring框架，由SpringMVC管理Controller;引入Dubbo服务`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617233955511-691879357.png)

>web.xml 配置：配置加载spring容器


## 6.项目启动遇到的问题
### 启动报错
```
需要先启动zookeeper,再启动服务层，再启动表现层。
如果先启动表现层，后启动服务层，会报错，但是不影响使用。	
```


## 7.监控中心
### 安装Tomcat
```
1、部署监控中心：
	cp dubbo-admin-2.5.4.war apache-tomcat-7.0.47/webapps/dubbo-admin.war 

2、启动tomcat
   cd bin 目录，输入：./startup.sh 
   查询tomcat是否已经启动：ps -ef|grep tomcat 
    
3、访问http://192.168.25.128:8080/dubbo-admin/
	用户名：root
	密码：root

```
`如果监控中心和注册中心在同一台服务器上，可以不需要任何配置。如果不在同一台服务器，需要修改配置文件：/root/apache-tomcat-7.0.47/webapps/dubbo-admin/WEB-INF/dubbo.properties`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617235657014-2057252457.png)


![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190617235008977-1500665226.png)