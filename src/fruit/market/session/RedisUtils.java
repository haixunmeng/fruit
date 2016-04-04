package fruit.market.session;

import org.apache.log4j.Logger;

import fruit.market.exception.FruitException;
import fruit.market.utils.PropertyUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {

	private static Logger logger = Logger.getLogger(RedisUtils.class);
	
	private static JedisPool jedisPool = null; //Jedis连接池
	private static JedisPoolConfig poolConfig = null;//Jedis连接池配置
	private static String redisHost;//主机
	private static int redisPort;//端口
	public static int expireTime;//key过期时间(过期后被自动删除)
	
	private static void loadConfig(){
		redisHost = PropertyUtil.getProperty("redis.host").trim();
		redisPort = Integer.parseInt(PropertyUtil.getProperty("redis.port").trim());
		
		poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(Integer.parseInt(PropertyUtil.getProperty("redis.pool.maxIdle").trim()));
		poolConfig.setMaxTotal(Integer.parseInt(PropertyUtil.getProperty("redis.pool.maxActive").trim()));
		poolConfig.setMaxWaitMillis(Long.parseLong(PropertyUtil.getProperty("redis.pool.maxWait").trim()));
		poolConfig.setMinEvictableIdleTimeMillis(Long.parseLong(PropertyUtil.getProperty("redis.pool.minEvictableIdleTimeMillis").trim()));
		poolConfig.setTimeBetweenEvictionRunsMillis(Long.parseLong(PropertyUtil.getProperty("redis.pool.timeBetweenEvictableRunsMillis").trim()));
		poolConfig.setTestOnBorrow(Boolean.parseBoolean(PropertyUtil.getProperty("redis.pool.testOnBorrow").trim()));
		poolConfig.setTestOnReturn(Boolean.parseBoolean(PropertyUtil.getProperty("redis.pool.testOnReturn").trim()));
		poolConfig.setTestWhileIdle(Boolean.parseBoolean(PropertyUtil.getProperty("redis.pool.testWhileIdle").trim()));
		
		expireTime = Integer.parseInt(PropertyUtil.getProperty("redis.expire.seconds").trim());
	}
	
	/**
	 * 获取redis连接
	 * @return
	 * @throws Exception
	 */
	public static Jedis getJedisResource(){
		if(poolConfig == null) {
			loadConfig();
		}
		
		if(jedisPool == null) {
			jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
		}
		
		try{
			return jedisPool.getResource();
		}catch(Exception e) {
			jedisPool = null;
			logger.error(FruitException.GET_REDIS_SERVICE_FAIL);
			throw FruitException.GET_REDIS_SERVICE_FAIL;
		}
	}
	
	/**
	 * 释放redis连接
	 * @param jedis
	 */
	public static void returnJedisResource(Jedis jedis) {
		if(jedis != null) {
			jedis.close();
		}
		
	}
}
