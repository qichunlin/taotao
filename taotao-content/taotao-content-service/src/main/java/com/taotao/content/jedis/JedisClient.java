package com.taotao.content.jedis;

/**
 * JedisClient接口
 */
public interface JedisClient {

	String set(String key, String value);//字符串设置值
	String get(String key);//字符串获取值
	Boolean exists(String key);//是否存在key值
	Long expire(String key, int seconds);//设置key的过期时间
	Long ttl(String key);//过期时间点
	Long incr(String key);//自动递增
	Long hset(String key, String field, String value);//hash set方式
	String hget(String key, String field);	//hash get方式
	Long hdel(String key, String... field);//删除hkey
	
}
