package com.taotao.search.controller;


import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @Autowired
    private SearchService searchService;

    /**
     * 根据条件搜索商品的数据
     * @param page
     * @param queryString
     * @return
     */
    @RequestMapping("/search")
    public String search(Integer page, @RequestParam(value = "q")String queryString, Model model) throws Exception{
        System.out.println("进来搜索......"+queryString);
        //1.注入
        //2.引用
        //3.调用

        //处理乱码
        queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
        System.out.println("进来搜索......"+queryString);
        SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
        System.out.println("长度是"+result.getItemList().size());
        //4.设置数据传递到jsp中
        model.addAttribute("query",queryString);//回显查询的字符串
        model.addAttribute("totalPages",result.getPageCount());//总页数
        model.addAttribute("itemList",result.getItemList());//商品列表
        model.addAttribute("page",page);//页面
        return "search";
    }
}
