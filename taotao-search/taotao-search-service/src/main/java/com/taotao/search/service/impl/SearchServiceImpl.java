package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;


    @Autowired
    private SearchDao searchDao;

    @Override
    public TaotaoResult importAllSearchItem() throws Exception{
        //1.注入mapper
        //2.调用mapper
        List<SearchItem> searchItemList = searchItemMapper.getSearchItemList();

        //3.通过solrj 将数据写入到索引库中
        //3.1创建httpsolrserver
        //3.2 创建solrinputdocument  将 列表中的元素一个个放到索引库中
        for (SearchItem searchItem: searchItemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId().toString());//这里是字符串需要转换
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            //添加到索引库
            solrServer.add(document);
        }

        //提交
        solrServer.commit();
        return TaotaoResult.ok();
    }


    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        //1.创建solrquery对象
        SolrQuery solrQuery = new SolrQuery();

        //2.设置主查询条件
        if (StringUtils.isNotBlank(queryString)){
            solrQuery.setQuery(queryString);
        } else {
            solrQuery.setQuery("*:*");
        }
        //2.1 设置过滤条件
        //分页参数
        if (page == null) page = 1;
        if (rows == null) rows = 60;
        solrQuery.setStart((page - 1)*rows);//page -1 * rows
        solrQuery.setRows(rows);

        //2.2 设置默认的搜索域
        solrQuery.set("df","item_keywords");
        //2.3设置高亮
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.addHighlightField("item_title");

        // 调用Dao的方法 返回的是SearchResult  只包含了总记录数和商品的列表
        SearchResult search = searchDao.search(solrQuery);
        //4.设置SearchResult的总页数
        long pageCount = 0l;
        pageCount = search.getPageCount();//rows
        if (search.getRecordCount()%rows > 0){
            pageCount++;
        }
        search.setPageCount(pageCount);
        //5.返回
        return search;
    }
}
