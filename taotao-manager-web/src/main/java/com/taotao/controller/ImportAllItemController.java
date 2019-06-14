package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 一键导入所有的商品到索引库
 */
@Controller
public class ImportAllItemController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/index/importAll")
    @ResponseBody
    public TaotaoResult importAll() throws Exception{
        //1.引入服务
        //2.注入服务
        //3.调用服务
        return searchService.importAllSearchItem();
    }
}
