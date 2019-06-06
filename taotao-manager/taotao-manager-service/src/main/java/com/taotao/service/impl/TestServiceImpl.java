package com.taotao.service.impl;

import com.taotao.mapper.TestMapper;
import com.taotao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    //注入mapper
    @Autowired
    private TestMapper testMapper;

    @Override
    public String queryNow() {
        //调用mapper方法
        return testMapper.queryNow();
    }
}
