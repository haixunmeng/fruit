package fruit.market.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fruit.market.dao.UserDao;
import fruit.market.data.User;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Controller
@RequestMapping("/test")
public class Controller_4_test {

	@Autowired
	private UserDao userDao;

	@RequestMapping("/add")
	@ResponseBody
	public String addTest() {
		
		User user = new User();
		
		user.setUser_id("62766021f99011e5930d2f0546e01958");
		user.setPhone("123456");
		user.setPwd("123456");
		user.setUser_name("test");
		user.setCreate_time(DateUtil.getTimestamp());
		
		System.out.println(new Timestamp(new Date().getTime()).toString());
		
		userDao.add(user);
		
		User userout = userDao.getData("62766021f99011e5930d2f0546e01958");
		
		System.out.println(userout.getCreate_time());
		
		return "{\"msg\":\"更新成功\"}";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateTest() {

		Map<String, Object> userData = new HashMap<String, Object>();

		userData.put("user_id", "62766021f99011e5930d2f0546e01958");
		userData.put("user_name", "柴效刚");
		userData.put("pwd", "bf8d434c3eabec92d389609affefd6f2");
		userData.put("phone", "13208919466");
		userData.put("user_type", "B");
		userData.put("create_time", "2016-04-03 19:37:02");
		userData.put("update_time", DateUtil.getDateString());

		userDao.update(userData);
		
		return "{\"msg\":\"更新成功\"}";
	}
	
	@RequestMapping("/batchUpdate")
	@ResponseBody
	public String batchUpdateTest() {
		
		int[] a = new int[3];
		@SuppressWarnings("unchecked")
		Map<String, Object>[] users = new Map[3];
		
		Map<String, Object> user1 = new HashMap<String, Object>();
		
		user1.put("user_id", "62766021f99011e5930d2f0546e01958");
		user1.put("user_name", "柴效刚");
		user1.put("pwd", "bf8d434c3eabec92d389609affefd6f2");
		user1.put("phone", "13208919466");
		user1.put("user_type", "B");
		user1.put("create_time", "2016-04-03 19:37:02");
		user1.put("update_time", DateUtil.getDateString());
		
		users[0] = user1;
		
		Map<String, Object> user2 = new HashMap<String, Object>();
		
		user2.put("user_id", "cdc046b1ee5111e5aad2f3342f964f7a");
		user2.put("user_name", "兰登科");
		user2.put("pwd", "38d6c8e42b423fd7e11ea73b0cec93ed");
		user2.put("phone", "13052576330");
		user2.put("user_type", "S");
		user2.put("create_time", "2016-03-20 12:11:21");
		user2.put("update_time", DateUtil.getDateString());
		
		users[1] = user2;
		
		Map<String, Object> user3 = new HashMap<String, Object>();
		
		user3.put("user_id", "ffd4f692f3f211e5bca2bfb0c61de68b");
		user3.put("user_name", "landengke");
		user3.put("pwd", "bf8d434c3eabec92d389609affefd6f2");
		user3.put("phone", "13460183550");
		user3.put("user_type", "M");
		user3.put("create_time", "2016-03-27 16:08:15");
		user3.put("update_time", DateUtil.getDateString());
		
		users[2] = user3;
		
		userDao.batchUpdate(users);
		
		return "{\"msg\":\"更新成功\"}";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteTest() {
		
		Map<String, Object> userData = new HashMap<String, Object>();
		
		userData.put("user_id", "62766021f99011e5930d2f0546e01958");
		
		userDao.delete(userData);
		
		return "{\"msg\":\"更新成功\"}";
	}
	
	@RequestMapping("/batchDelete")
	@ResponseBody
	public String batchDeleteTest() {
		
		@SuppressWarnings("unchecked")
		Map<String, Object>[] user_ids = new Map[3];
		
		Map<String, Object> user_id1 = new HashMap<String, Object>();
		user_id1.put("user_id", "62766021f99011e5930d2f0546e01958");
		
		user_ids[0] = user_id1;
		
		Map<String, Object> user_id2 = new HashMap<String, Object>();
		user_id2.put("user_id", "cdc046b1ee5111e5aad2f3342f964f7a");
		
		user_ids[1] = user_id2;
		
		Map<String, Object> user_id3 = new HashMap<String, Object>();
		user_id3.put("user_id", "f083944cee4f11e591a20196ab91cd04");
		
		user_ids[2] = user_id3;
		
		userDao.batchDelete(user_ids);
		
		return "{\"msg\":\"更新成功\"}";
	}
}
