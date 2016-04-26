package fruit.market.test.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fruit.market.dao.UserDao;
import fruit.market.dao.impl.UserDaoImpl;
import fruit.market.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/resources/applicationContext.xml")
public class UserDaoTest {

	@Test
	public void testUpdate(){
		
		UserDao userDao = new UserDaoImpl();
		
		Map<String, Object> userData = new HashMap<String, Object>();
		
		userData.put("user_id", "62766021f99011e5930d2f0546e01958");
		userData.put("user_name", "柴效刚");
		userData.put("pwd", "bf8d434c3eabec92d389609affefd6f2");
		userData.put("phone", "13208919466");
		userData.put("user_type", "B");
		userData.put("create_time", "2016-04-03 19:37:02");
		userData.put("update_time", DateUtil.getDateString());
		
		boolean result = userDao.update(userData);
		
		Assert.assertEquals("更新成功", result, true);
	}
}
