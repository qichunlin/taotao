package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 从索引库中搜索商品的Dao
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    //根据查询的条件查询结果集
    public SearchResult search(SolrQuery query) throws Exception{

        SearchResult searchResult = new SearchResult();

        //1.创建solrserver对象,由spring管理注入
        //2.直接执行查询
        QueryResponse response = solrServer.query(query);
        //3.获取结果集
        SolrDocumentList results = response.getResults();
        //设置SearchResult的总记录数
        searchResult.setRecordCount(results.getNumFound());

        //取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        //4.遍历结果集
        //定义一个集合
        List<SearchItem> searchItemlist = new ArrayList<>();
        for (SolrDocument solrDocument:results) {
            //将solrDocument中的属性一个一个设置到searchItem中
            SearchItem searchItem = new SearchItem();

            //searchItem 封装到searchResult中的itemList属性中
            searchItem.setCategory_name(solrDocument.get("item_category_name").toString());
            searchItem.setId(Long.parseLong(solrDocument.get("id").toString()));
            searchItem.setImage(solrDocument.get("item_image").toString());
            //searchItem.setItem_desc(item_desc);
            searchItem.setPrice((Long)solrDocument.get("item_price"));
            searchItem.setSell_point(solrDocument.get("item_sell_point").toString());

            //取高亮
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            //判断list是否为空
            String gaoLiangStr = "";
            if(list != null && list.size()>0){
                //有高亮
                gaoLiangStr=list.get(0);
            }else{
                gaoLiangStr = solrDocument.get("item_title").toString();
            }
            //标题高亮
            searchItem.setTitle(gaoLiangStr);
            searchItemlist.add(searchItem);
        }

        searchResult.setItemList(searchItemlist);
        return searchResult;
    }
}
