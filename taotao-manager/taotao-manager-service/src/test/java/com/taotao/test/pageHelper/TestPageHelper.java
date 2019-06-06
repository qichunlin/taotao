package com.taotao.test.pageHelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 分页测试
 */
public class TestPageHelper {

    @Test
    public void testPageHelper(){
        //1.初始化spring容器
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //2.获取mapper的方法查询对象
        TbItemMapper tbItemMapper = act.getBean(TbItemMapper.class);
        //3.设置分页信息
        PageHelper.startPage(1,3);//3行
        //4.调用mapper的方法查询数据
        TbItemExample example = new TbItemExample();//设置查询条件使用
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        List<TbItem> tbItems2 = tbItemMapper.selectByExample(example);

        //取分页信息
        PageInfo<TbItem> info = new PageInfo<>(tbItems);
        System.out.println("第一个tbItem的长度"+tbItems.size());
        System.out.println("第二个tbItem的长度"+tbItems2.size());

        //5.遍历结果集,打印
        System.out.println("第一个TbItem查询总记录数："+info.getTotal());
        for (TbItem tbItem: tbItems) {
            System.out.println("ID"+tbItem.getId()+"match:"+tbItem.getTitle());
        }
    }
}
