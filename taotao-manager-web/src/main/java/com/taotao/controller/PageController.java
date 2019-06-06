package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页测试
 */
@Controller
public class PageController {
	
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}

	/**
	 * 显示查询的商品
	 * @return
	 */
	//@RequestMapping("/item-list")
	//使用restful风格,动态获取页面的url
	@RequestMapping("/{page}")
	public String showItemList(@PathVariable String page){
		return page;
	}
}
