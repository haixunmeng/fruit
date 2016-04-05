package fruit.market.cache;

import com.alibaba.fastjson.JSON;

import fruit.market.exception.FruitException;
import fruit.market.utils.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class CacheManager {
	
	public static void set(String key, Object data){
		if(data == null) return;
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			jedis.set(key.getBytes("UTF-8"), JSON.toJSONString(data).getBytes("UTF-8"));
			jedis.expire(key.getBytes("UTF-8"), RedisUtils.expireTime);
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
	}
	
	public static <T>T get(String key, Class<T> clazz){
		T object = null; 
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			byte[] bytes = jedis.get(key.getBytes("UTF-8"));
			if(bytes != null){
				String value = new String(bytes,"UTF-8");
				object = JSON.parseObject(value, clazz);
			}
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
		return object;
	}
	
	public static void hashset(String key, String field, Object data){
		if(data == null) return;
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			jedis.hset(key.getBytes("UTF-8"),field.getBytes("UTF-8"), JSON.toJSONString(data).getBytes("UTF-8"));
			jedis.expire(key.getBytes("UTF-8"), RedisUtils.expireTime);//设置过期时间
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
	}
	
	public static <T> T hashget(String key, String field, Class<T> clazz){
		T object = null; 
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			byte[] bytes = jedis.hget(key.getBytes("UTF-8"), field.getBytes("UTF-8"));
			if(bytes != null){
				String value = new String(bytes,"UTF-8");
				object = JSON.parseObject(value, clazz);
			}
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
		return object;
	}
	
	public static void remove(String key){
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			jedis.del(key);
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
	}
	
	public static void clear(){
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			jedis.flushDB(); //清除缓存数据
			
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
		
	}
	
	public static void resetExpireTime(String key){
		Jedis jedis = null;
		try{
			jedis = RedisUtils.getJedisResource();
			jedis.expire(key.getBytes("UTF-8"), RedisUtils.expireTime);//设置过期时间
		}catch(JedisConnectionException e) {
			throw FruitException.REDIS_CONNECTION_FAIL;
		}catch(Exception e){
			throw FruitException.REDIS_EXCEPTION;
		}finally{
			RedisUtils.returnJedisResource(jedis); //释放jedis到池中
		}
	}
}
