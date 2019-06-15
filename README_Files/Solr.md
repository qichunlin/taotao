# Solr服务搭建  & 项目实现全局搜索
## 1.Solr的环境
```
Solr是java开发
solr的安装文件
需要安装jdk
安装环境Linux
需要安装Tomcat
```

## 2.搭建步骤
### 2.1  把solr 的压缩包上传到Linux系统
```
使用xshell的xftp方式即可
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613101121386-1813336039.png)

### 2.2  解压solr
```powershell
tar -xvf solr-4.10.3.tgz.tgz
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613101256715-1193451156.png)

### 2.3 安装Tomcat，解压缩即可
```powershell
tar -xvf apache-tomcat-7.0.47.tar.gz
```

### 2.4  把solr部署到Tomcat下
```
进入到tomcat的bin目录下启动tomcat,会自动解压war包

./startup.sh     启动tomcat
./shutdown.sh    关闭tomcat
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613100933944-283184352.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613102422070-956810852.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613102448992-750809285.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613102458616-683655048.png)


### 2.5 把/root/solr-4.10.3/example/lib/ext目录下的所有的jar包，添加到solr工程中
```
cp /root/solr-4.10.3/example/lib/ext/*.jar /usr/local/solr/apache-tomcat-7.0.47/webapps/solr/WEB-INF/lib/




[root@localhost example]# pwd
/root/solr-4.10.3/example
[root@localhost example]# cp -r solr /usr/local/solr/solrhome
[root@localhost example]# 
```

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613102626348-1287834010.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613102802403-34402591.png)

### 2.6 创建一个solrhome。/example/solr目录就是一个solrhome。复制此目录到/usr/local/solr/solrhome
```powershell
cp /root/solr-4.10.3/example/solr /usr/local/solr/solrhome -r
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613103016546-1069623115.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613103156134-1092996762.png)

### 2.7 修改配置关联
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613103411015-1358862479.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613103649144-161453916.png)

### 2.8 启动Tomcat
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613103959342-724328794.png)

`http://192.168.25.128:8080/solr/`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613104248210-1805097310.png)

## 3 配置业务域
### 3.1 配置分析器
#### 把中文分析器添加到solr工程中
- 把IKAnalyzer2012FF_u1.jar添加到solr工程的lib目录下
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613105341837-1986369565.png)

- 把扩展词典、配置文件放到solr工程的WEB-INF/classes目录下
`cp /root/IKAnalyzer/*.dic *.xml ./classes/`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613105745661-401459313.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613110050293-1629644163.png)


#### 配置一个FieldType，制定使用IKAnalyzer
- 修改Solr的schema.xml文件，添加FieldType
```xml
<fieldType name="text_ik" class="solr.TextField">
  <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
</fieldType>
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613112450970-1385767007.png)


### 配置业务域，type制定使用自定义的FieldType。
`设置业务系统Field`
```xml
<field name="item_title" type="text_ik" indexed="true" stored="true"/>
<field name="item_sell_point" type="text_ik" indexed="true" stored="true"/>
<field name="item_price"  type="long" indexed="true" stored="true"/>
<field name="item_image" type="string" indexed="false" stored="true" />
<field name="item_category_name" type="string" indexed="true" stored="true" />
<field name="item_desc" type="text_ik" indexed="true" stored="false" />

<field name="item_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
<copyField source="item_title" dest="item_keywords"/>
<copyField source="item_sell_point" dest="item_keywords"/>
<copyField source="item_category_name" dest="item_keywords"/>
<copyField source="item_desc" dest="item_keywords"/>

```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613113528555-762526545.png)


## 创建工程
### taotao-search 父工程
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613152322289-140888764.png)


![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613152445606-1856041326.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613152530837-788065486.png)


### taotao-search-interface子工程
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613152322289-140888764.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613152837000-1870238912.png)

