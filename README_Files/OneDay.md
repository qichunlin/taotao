# 第一天
## 1.工程搭建(基于Maven方式构建)
### 1.1 打开IDEA，新建一个空项目 文件名叫作 taotao
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210626570-1751913126.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609194327554-545502105.png)

### 1.2 接下来创建父工程taotao-parent，File->New->Module
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210027161-648458629.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210142909-186688213.png)

`点击右边的 + 然后输入信息,name=archetypeCatalog  value=internal  这两个信息可以加快idea创建maven项目的速度,加不加也可以`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210249559-1954841631.png)


![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210333507-263417482.png)


### 1.3 创建taotao-common工程,一样的操作File->New->Module
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210823378-1557720505.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210904814-1945081177.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210941601-2144167034.png)


### 1.4 创建taotao-manager工程,步骤和创建taotao-common工程一模一样
```
taotao-manager的pom文件,注意打包为pom
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609210823378-1557720505.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609220840998-1332567872.png)

`如果父工程不写打包方式,特别是需要打成war包的时候就无法使用运行 clean tomcat7:run`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609220533469-175698692.png)



#### 1.4.1接下来创建taotao-manager-pojo工程
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609221050648-1212561283.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609221152263-2069830666.png)


![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609221556429-593663172.png)


`注意修改路径，将pojo放在manager的目录下`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609221719490-1532647683.png)


#### 1.4.2 taotao-manager-interface,taotao-manager-service,taotao-manager-dao这三个的步骤是一模一样的
`注意修改路径，将interface\dao\service放在manager的目录下,还有service的打包方式是war包`

`taotao-service的pom文件是war`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609222740665-2099723752.png)

### 1.5 创建taotao-manager-web工程
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609223401685-314847355.png)


`选择Maven,勾上对号，选择maven-webapp，注意是maven的webapp`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609223500456-526053234.png)

`输入信息,name=archetypeCatalog  value=internal  这两个信息可以加快idea创建maven项目的速度`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609223521499-1705634949.png)


`注意路径的修改，web是和parent是同级的,打包方式也是war`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609223546353-557794275.png)


### 1.6 最终项目搭建效果
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609194052676-991171014.png)


### 1.7 修改工程文件目录
```
这样工程都创建完毕了，接下来需要做一些修改
删除下图中自动生成的包和类，注意需要将每个工程下的都删除,
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609224146541-1760774781.png)


### 1.8 taotao-manager-web需要生成web.xml文件
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609224225607-307126134.png)
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609224112493-262875463.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609224441133-1932067000.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609224404931-1688042079.png)


### 1.9 生成mvn命令,并启动项目测试是否搭建成功
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609225613446-86927521.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609224517905-1717665889.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609225416515-216952499.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190609225324269-1950504134.png)
