package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * 定义Mapper 关联查询3张表 查询出搜索时的商品数据
 *
 * 返回值：是列表,需要有一个POJO封装一行记录,再存放到list集合中返回
 */
public interface SearchItemMapper {

    //查询所有的商品的数据
    public List<SearchItem> getSearchItemList();
}
