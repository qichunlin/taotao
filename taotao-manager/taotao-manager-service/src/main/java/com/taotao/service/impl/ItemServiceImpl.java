package com.taotao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //1.设置分页的信息,使用pageHelper
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 30;
        }
        PageHelper.startPage(page, rows);

        //2.注入mapper
        //3.创建一个example对象
        TbItemExample tbItemExample = new TbItemExample();

        //4.根据mapper调用查询所有数据的方法
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        //5.获取分页的信息
        PageInfo<TbItem> info = new PageInfo<>(tbItems);

        //6.封装到EasyUIDataGridResult
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int)info.getTotal());
        result.setRows(info.getList());

        //7.返回
        return result;
    }
}
