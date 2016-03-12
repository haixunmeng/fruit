package fruit.market.session;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import fruit.market.utils.Utils;
import redis.clients.jedis.Jedis;

public class SessionManager {
	
	private static Logger logger = Logger.getLogger(SessionManager.class);
	
	public static Jedis jedis = null;
	
	static {
		
		String host = Utils.getProperties("redis.host");
		int port = Integer.valueOf(Utils.getProperties("redis.port"));
		int expiretime = Integer.valueOf(Utils.getProperties("redis.expiretime"));
		
		jedis = new Jedis(host, port, expiretime);
		
		logger.info("redis " + host + ":" + port + " 连接成功 .....................");
	}
	
	public static void save2session(String key, String field, String value){
		jedis.hset(key, field, value);
	}
	
	public static String get4session(String key, String field){
		return jedis.hget(key, field);
	}

}
