package fruit.market.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fruit.market.dao.UserDao;
import fruit.market.data.User;
import fruit.market.exception.FruitException;
import fruit.market.utils.DBUtils;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public UserDaoImpl(){
		tableName = "fruit_user";
		primaryKey = "user_id";
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

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
		} catch (EmptyResultDataAccessException e){
			return null;
		} catch ( DataAccessException e) {
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<User> queryListByConditions(Map<String, String> conditions) {
		try{
			return jdbcTemplate.query(DBUtils.generateSQL(tableName, conditions), rowMapper);
		}catch ( DataAccessException e) {
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

}
