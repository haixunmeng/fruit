package fruit.market.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fruit.market.dao.UserDao;
import fruit.market.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:resources/applicationContext.xml")
public class UserDaoImplTest {

	@Autowired
	private UserDao userDao;
	
	
	@Test
	public void testGetUser() {
		User user = userDao.getUser(3);
		
		System.out.println(user.getName());
	}

}
