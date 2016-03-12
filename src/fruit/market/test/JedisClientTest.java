package fruit.market.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisClientTest {

	@Test
	public void jedisClientTest(){
		Jedis jedis = new Jedis("192.168.23.130", 9400);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("name", "lan");
		
		System.out.println(jedis.hset("123456", "name", "lan"));
		
		System.out.println(jedis.hget("123456", "name"));
		
		jedis.close();
	}
	
}
