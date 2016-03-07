package fruit.market.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fruit.market.dao.UserDao;
import fruit.market.exception.FruitException;
import fruit.market.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	private String tableName = "fruit_user";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

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
		} catch (DataAccessException e) {
			throw FruitException.DB_OPTION_EXCEPTION;
		}
		
	}

	@Override
	public User queryByConditions(String... conditions) {

		StringBuffer sql = new StringBuffer();
		
		if(0 == conditions.length || 0 != conditions.length % 2){
			throw FruitException.SEARCH_CONDITION_EXCEPTION;
		}
		
		sql.append("select * from ").append(tableName).append(" where ");
		
		for(int i=0;;i++){
			sql.append(conditions[i++]).append(" = '").append(conditions[i]).append("'");
			
			if(i<conditions.length-1){
				sql.append(" and ");
			}else{
				break;
			}
		}
		
		try{
			return jdbcTemplate.queryForObject(sql.toString(), rowMapper);
		} catch ( DataAccessException e) {
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
}
