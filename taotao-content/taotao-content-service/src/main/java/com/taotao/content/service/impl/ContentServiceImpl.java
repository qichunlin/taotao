package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import com.taotao.common.util.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	//注入mapper
	@Autowired
	private TbContentMapper mapper;

	@Override
	public TaotaoResult saveContent(TbContent content) {
		//1.注入mapper
		//2.补全其他的属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());

		//3.插入内容表中
		mapper.insertSelective(content);

		//做数据同步
		//当添加内容的时候,需要清空此内容所属的分类下的所有缓存
		try{
			jedisClient.hdel(CONTENT_KEY, content.getCategoryId() + "");
			System.out.println("当插入时清空缓存。。。。。。");
		} catch (Exception e){
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		//添加缓存不能影响到正常的业务逻辑
		//判断redis中是否有数据,如果有直接从redis中中获取数据直接返回
		try{
			String jsonstr = jedisClient.hget(CONTENT_KEY, categoryId + "");//从redis中获取内容分类下的所有 内容
			if (StringUtils.isNoneBlank(jsonstr)){
				System.out.println("有缓存");
				List<TbContent> tbContents = JsonUtils.jsonToList(jsonstr, TbContent.class);
				return tbContents;
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		//=================================================
		//1.注入mapper
		//2.创建example
		TbContentExample example = new TbContentExample();
		//3.设置查询条件
		//select * from tbcontent where category_id=1
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		//4.执行查询
		List<TbContent> list = mapper.selectByExample(example);
		//=================================================

		//将数据写入redis数据库中
		//注入jedisClient
		//调用方法写入redis   key -value
		try{
			//jedisClient.hset("CONTENT_KEY",categoryId+"", JsonUtils.objectToJson(list));
			System.out.println("没有缓存");
			jedisClient.hset(CONTENT_KEY,categoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e){
			e.printStackTrace();
		}
		//5.返回
		return list;
	}

}
