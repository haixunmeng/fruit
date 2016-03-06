package fruit.market.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fruit.market.dao.UserDao;
import fruit.market.exception.DaoException;
import fruit.market.exception.FruitException;
import fruit.market.model.User;
import fruit.market.utils.ErrorMeg;

@Repository
public class UserDaoImpl implements UserDao {

	private String tableName = "fruit_user";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

	@Override
	public boolean addUser(User user) {

		String sql = "insert into user(id, login_name, password, type, create_time, name, phone, car_num, own_shop_name) values(?, ?, ?, ?, NOW(), ?, ?, ?, ?)";

		try {
			int res = jdbcTemplate.update(sql, new Object[] { user.getId(), user.getLogin_name(), user.getPassword(),
					user.getType(), user.getName(), user.getPhone(), user.getCar_num(), user.getOwn_shop_name() });
			return res > 0;

		} catch (DataAccessException e) {

			throw new DaoException(ErrorMeg.DB_OPERATE_ERROR);

		}
	}

	@Override
	public boolean deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from user where userId=").append(userId);

		try {
			return jdbcTemplate.queryForObject(sql.toString(), rowMapper);

		} catch (DataAccessException e) {

			throw new DaoException(ErrorMeg.DB_OPERATE_ERROR);

		}

	}

	@Override
	public void register(Map<String, Object> parameters) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into ").append(tableName).append("(user_id, user_name, pwd, phone, user_type, create_time) values('")
													.append(parameters.get("user_id"))
													.append("', '")
													.append(parameters.get("user_name"))
													.append("', '")
													.append(parameters.get("pwd"))
													.append("', '")
													.append(parameters.get("phone"))
													.append("', '")
													.append(parameters.get("user_type"))
													.append("', '")
													.append(parameters.get("create_time"))
													.append("')");
		
		try{
			jdbcTemplate.execute(sql.toString());
		} catch (Exception e) {
			throw FruitException.DB_OPTION_EXCEPTION;
		}
		
	}

}
