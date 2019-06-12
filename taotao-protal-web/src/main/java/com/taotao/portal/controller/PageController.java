package com.taotao.portal.controller;


import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示首页数据
 */
@Controller
public class PageController {

    @Autowired
    private ContentService contentService;

    @Value("${AD1_CATAGORY_ID}")
    private Long catagoryId;

    @Value("${AD1_HEIGHT_B}")
    private String AD1_HEIGHT_B;

    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;

    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;

    @Value("${AD1_WIDTH_B}")
    private String AD1_WIDTH_B;

    /**
     * 展示首页
     * @return
     */
    //接收请求url  http://localhost:8085/
    @RequestMapping("/index")
    public String showIndex(Model model){
        //引入服务dubbo
        //注入服务pom
        //添加业务逻辑,根据内容分类的id查询内容列表
        //contentService.getContentListByCatId(89l);//硬编码不好  放到属性配置文件中
        List<TbContent> contentListByCatId = contentService.getContentListByCatId(catagoryId);

        //转为自定义的pojo的列表   Ad1Node的列表
        List<Ad1Node> nodes = new ArrayList<>();
        for (TbContent tbContent:contentListByCatId) {
            Ad1Node node = new Ad1Node();
            node.setAlt(tbContent.getSubTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);

            nodes.add(node);
        }
        //传递数据给jsp  转为json
        model.addAttribute("ad1", JsonUtils.objectToJson(nodes));
        return "index";//响应jsp
    }

}
