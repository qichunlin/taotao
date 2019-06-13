package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;
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
}
