# 整合TaoTao项目测试Redis
## 1.添加缓存
#### 在门户系统的内容中添加
```
public List<TbContent> getContentListByCatId(Long categoryId) {
		//添加缓存不能影响到正常的业务逻辑
	//判断redis中是否有数据,如果有直接从redis中获取数据直接返回
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

	//1.注入mapper
	//2.创建example
	TbContentExample example = new TbContentExample();
	//3.设置查询条件
	//select * from tbcontent where category_id=1
	example.createCriteria().andCategoryIdEqualTo(categoryId);
	//4.执行查询
	List<TbContent> list = mapper.selectByExample(example);

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
```

### 测试单机版
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613091729625-1002964714.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613091838897-2048218019.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613091906310-1781135975.png)

### 测试集群版
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613090911853-1874520258.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613090807306-2093588635.png)

## 2.同步缓存
### 删除缓存
`如果添加了新的内容就把旧的缓存删掉`
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613093946831-1388035454.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613093810074-450952524.png)

![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190613094111180-676954680.png)