package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;

/**
 * 商品相关的service
 */
public interface ItemService {

    /**
     * 根据当前页码和每页的行数进行分页查询
     * @param page
     * @param rows
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page,Integer rows);
}
