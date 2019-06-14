package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 测试SolrJ远程添加索引库
 */
public class SolrJTest {

    @Test
    public void addIndex() throws Exception{
        //1.创建solrserver   建立连接 需要指定地址
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //2.创建solrinputdocument
        SolrInputDocument document = new SolrInputDocument();

        //3.向文档中添加域
        document.addField("id", "test001");
        document.addField("item_title", "这是一个测试");
        //4.将文档提交到索引库中
        solrServer.add(document);
        //5.提交
        solrServer.commit();
    }


    /**
     * 测试查询索引
     * @throws Exception
     */
    @Test
    public void queryIndex() throws Exception{
        //1.创建solrserver   建立连接 需要指定地址
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //2.创建solrquery对象,设置各种过滤条件,主查询条件  排序
        SolrQuery solrQuery = new SolrQuery();

        //3.设置查询的条件
        solrQuery.setQuery("阿尔卡特");
        solrQuery.addFilterQuery("item_price:[0 TO 300000000]");
        solrQuery.set("df","item_title");
        //4.执行查询
        QueryResponse query = solrServer.query(solrQuery);
        //5.获取结果集
        SolrDocumentList results = query.getResults();
        System.out.println("查询的总记录数："+results.getNumFound());

        //6.遍历结果集
        for (SolrDocument solDocument:results) {
            System.out.println(solDocument.get("id"));
            System.out.println(solDocument.get("item_title"));
        }
    }
}
