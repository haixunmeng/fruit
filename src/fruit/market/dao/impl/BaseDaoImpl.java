package fruit.market.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import fruit.market.dao.BaseDao;
import fruit.market.exception.FruitException;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

	protected String tableName;

	protected String primaryKey;
	
	protected BeanPropertyRowMapper<T> rowMapper;
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	protected BaseDaoImpl(){
		rowMapper = BeanPropertyRowMapper.newInstance((Class<T>) getClassGenricType(getClass()));
	}
	
	/**
	 * 获得超类的参数类型，取第一个参数类型
	 * @param <T> 类型参数
	 * @param clazz 超类类型
	 */
	private static <T> Class<?> getClassGenricType(final Class<?> clazz) {
		return getClassGenricType(clazz, 0);
	}
	
	/**
	 * 根据索引获得超类的参数类型
	 * @param clazz 超类类型
	 * @param index 索引
	 */
	private static Class<?> getClassGenricType(final Class<?> clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}

	@Override
	public boolean add(T data) {

		SimpleJdbcInsert insertor = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);

		try {
			return insertor.execute(new BeanPropertySqlParameterSource(data)) > 0;
		} catch (DataAccessException e) {
			logger.info(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public T getData(String key) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName).append(" where ").append(primaryKey).append(" = ").append(key);
		
		try {
			return jdbcTemplate.queryForObject(sql.toString(), rowMapper);
		} catch (EmptyResultDataAccessException e){
			return null;
		} catch (DataAccessException e) {
			logger.info(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
	
	

}
