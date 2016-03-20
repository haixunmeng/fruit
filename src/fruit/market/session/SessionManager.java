package fruit.market.session;

import org.apache.log4j.Logger;

import fruit.market.model.User;
import fruit.market.utils.DateUtil;
import fruit.market.utils.PropertyUtil;
import redis.clients.jedis.Jedis;

public class SessionManager {
	
	private static Logger logger = Logger.getLogger(SessionManager.class);
	
	public static Jedis jedis = null;
	
	static {
		
		String host = PropertyUtil.getProperties("redis.host");
		int port = Integer.valueOf(PropertyUtil.getProperties("redis.port"));
		int expiretime = Integer.valueOf(PropertyUtil.getProperties("redis.expiretime"));
		
		jedis = new Jedis(host, port, expiretime);
		
		logger.info("redis " + host + ":" + port + " 连接成功 .....................");
	}
	
	public static void save2session(String key, String field, String value){
		jedis.hset(key, field, value);
	}
	
	public static void save2session(String key, User user){
		jedis.hset(key, "userid", user.getUser_id());
		jedis.hset(key, "username", user.getUser_name());
		jedis.hset(key, "pwd", user.getPwd());
		jedis.hset(key, "type", user.getUser_type());
		jedis.hset(key, "loginTime", DateUtil.getDateString());
	}
	
	public static String get4session(String key, String field){
		return jedis.hget(key, field);
	}

}
