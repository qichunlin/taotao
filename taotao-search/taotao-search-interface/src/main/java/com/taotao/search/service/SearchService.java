package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchService {

    //导入所有商品数据到索引库中
    public TaotaoResult importAllSearchItem() throws Exception;
}
