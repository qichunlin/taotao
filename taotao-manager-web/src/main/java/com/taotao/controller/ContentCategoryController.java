package com.taotao.controller;


import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;
    /**
     * url : '/content/category/list',
     animate: true,
     method : "GET",
     参数: id
     */
    @RequestMapping(value="/content/category/list",method= RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0") Long parentId){
        //1.引入服务
        //2.注入服务
        //3调用
        return contentCategoryService.getContentCategoryList(parentId);
    }


    /**
     * 添加节点
     *
     * url:/content/category/create
     *  参数：
     *      parentId：就是新增节点的父节点的Id
     *      name：新增节点的文本
     *      返回值taotaoresult 包含分类的id
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping(value="/content/category/create",method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId, String name){
        //1.引入服务
        //2.注入服务
        //3调用
        return contentCategoryService.createContentCategory(parentId, name);
    }
}
