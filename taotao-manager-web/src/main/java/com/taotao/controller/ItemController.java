package com.taotao.controller;


import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品Controller
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    //url:/item/list
    //method:get
    //参数：page,rows
    //返回值:json
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer rows, Integer page){
        //1.引入服务
        //2.注入服务
        //3.调用服务的方法
        return itemService.getItemList(page,rows);
    }
}
