package fruit.market.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unchecked")
	protected BaseDaoImpl() {
		rowMapper = BeanPropertyRowMapper.newInstance((Class<T>) getClassGenricType(getClass()));
	}

	/**
	 * 获得超类的参数类型，取第一个参数类型
	 * 
	 * @param <T>
	 *            类型参数
	 * @param clazz
	 *            超类类型
	 */
	private static <T> Class<?> getClassGenricType(final Class<?> clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 根据索引获得超类的参数类型
	 * 
	 * @param clazz
	 *            超类类型
	 * @param index
	 *            索引
	 */
	private static Class<?> getClassGenricType(final Class<?> clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
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
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public T getData(String key) {
		StringBuffer sql = new StringBuffer();

		sql.append("select * from ").append(tableName).append(" where ").append(primaryKey).append(" = ?");

		try {
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] { key }, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<T> getByCondition(Map<String, Object> conditions) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ").append(tableName);
			Set<String> keys = conditions.keySet();
			int size = keys.size();
			int length = size - 1;
			int index = 0;
			Object[] params = null;
			if (size > 0) {
				sql.append(" where ");
				params = new Object[size];
			}
			for (String key : keys) {
				if (index == length)
					sql.append(key).append(" =? ");
				else
					sql.append(key).append(" =? and ");
				params[index] = conditions.get(key);
				index++;
			}
			if (size == 0)
				return jdbcTemplate.query(sql.toString(), rowMapper);
			else
				return jdbcTemplate.query(sql.toString(), params, rowMapper);

		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public T getSingleByCondition(Map<String, Object> conditions) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ").append(tableName);
			Set<String> keys = conditions.keySet();
			int size = keys.size();
			int length = size - 1;
			int index = 0;
			Object[] params = null;
			if (size > 0) {
				sql.append(" where ");
				params = new Object[size];
			}
			for (String key : keys) {
				if (index == length)
					sql.append(key).append(" =? ");
				else
					sql.append(key).append(" =? and ");
				params[index] = conditions.get(key);
				index++;
			}
			if (size == 0)
				return jdbcTemplate.queryForObject(sql.toString(), rowMapper);
			else
				return jdbcTemplate.queryForObject(sql.toString(), params, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public boolean batchAdd(List<T> ds) {
		if (ds == null || ds.size() == 0)
			return true;
		SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
		SqlParameterSource[] psArray = new SqlParameterSource[ds.size()];
		for (int i = 0; i < ds.size(); i++)
			psArray[i] = new BeanPropertySqlParameterSource(ds.get(i));
		try {
			int[] result = insertActor.executeBatch(psArray);
			for (int i : result)
				if (i < 0 && i != -2)
					return false;
			return true;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<T> getOnePage(int pageNum, int pageCount) {
		StringBuffer sql = new StringBuffer();

		sql.append("select * from ").append(tableName).append(" limit ? offset ?");
		try {
			return jdbcTemplate.query(sql.toString(), new Object[] { pageCount, pageCount * (pageNum - 1) }, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
	
	@Override
	public boolean update(T data) {
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");
		
		Field[] fields = data.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			if(fieldName.equals("create_time"))
				continue;
			if(fieldName.equals("creator"))
				continue;

			sql.append(fieldName + " = :" + fieldName + ",");
		}
		sql.replace(sql.lastIndexOf(","), sql.length(), "");
		
		sql.append(" where ").append(primaryKey).append(" = :").append(primaryKey);
		
		SqlParameterSource ps = new BeanPropertySqlParameterSource(data);
		
		try{
			return namedParameterJdbcTemplate.update(sql.toString(), ps) > 0;
		}catch(DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
	
	@Override
	public boolean update(Map<String, Object> data) {
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");

		for (String key : data.keySet()) {
			if ("create_time".equals(key)) {
				continue;
			}
			sql.append(key + " = :" + key + ", ");
		}

		sql.replace(sql.lastIndexOf(","), sql.length(), "");

		sql.append(" where ").append(primaryKey).append(" = :").append(primaryKey);

		try {
			return namedParameterJdbcTemplate.update(sql.toString(), data) > 0;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public boolean batchUpdate(Map<String, Object>[] data) {
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");

		Map<String, Object> dataMap = data[0];
		for (String key : dataMap.keySet()) {
			if ("create_time".equals(key)) {
				continue;
			}
			sql.append(key + " = :" + key + ", ");
		}

		sql.replace(sql.lastIndexOf(","), sql.length(), "");

		sql.append(" where ").append(primaryKey).append(" = :").append(primaryKey);

		try {
			int[] batchUpdateResult = namedParameterJdbcTemplate.batchUpdate(sql.toString(), data);

			for (int i : batchUpdateResult) {
				if (i > 0) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
	
	@Override
	public boolean batchUpdate(List<T> datas) {
    	if(datas == null || datas.size() ==0) return true;
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");
		Field[] fields = datas.get(0).getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			sql.append(fieldName + " = :" + fieldName + ",");
		}
		sql.replace(sql.lastIndexOf(","), sql.length(), "");
		
		sql.append(" where ").append(primaryKey).append(" = :").append(primaryKey);
		
		SqlParameterSource[] psArray = new SqlParameterSource[datas.size()];
		for(int i=0; i<datas.size(); i++)
			psArray[i] = new BeanPropertySqlParameterSource(datas.get(i));
		try{
			int[] result = namedParameterJdbcTemplate.batchUpdate(sql.toString(), psArray);
			for(int i: result)
				if(i<0 && i!=-2) return false;
			return true;
		}catch(DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public boolean delete(Map<String, Object> data) {

		StringBuffer sql = new StringBuffer();

		sql.append("delete from ").append(tableName).append(" where ").append(primaryKey).append(" = :")
				.append(primaryKey);

		try {
			return namedParameterJdbcTemplate.update(sql.toString(), data) > 0;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public boolean batchDelete(Map<String, Object>[] data) {

		StringBuffer sql = new StringBuffer();

		sql.append("delete from ").append(tableName).append(" where ").append(primaryKey).append(" = :")
				.append(primaryKey);

		try {
			int[] batchUpdateResult = namedParameterJdbcTemplate.batchUpdate(sql.toString(), data);
			for (int i : batchUpdateResult) {
				if (i > 0) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<Map<String, Object>> getPageData(int pageNum, int pageCount) {
		StringBuffer sql = new StringBuffer();

		sql.append("select * from ").append(tableName).append(" limit ? offset ?");
		try {
			return jdbcTemplate.queryForList(sql.toString(), new Object[] { pageCount, pageCount * (pageNum - 1) });
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<Map<String, Object>> getPageMapData(Map<String, Object> conditions, int pageNum, int pageCount) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName);
		
		Set<String> keys = conditions.keySet();
		int size = keys.size();
		int length = size - 1;
		int index = 0;
		Object[] params = null;
		if (size > 0) {
			sql.append(" where ");
			params = new Object[size+2];
		}
		
		for (String key : keys) {
			if (index == length)
				sql.append(key).append(" =? ");
			else
				sql.append(key).append(" =? and ");
			params[index] = conditions.get(key);
			index++;
		}
		
		sql.append(" limit ? offset ?");
		params[index] = pageCount;
		params[index+1] = pageCount * (pageNum - 1);
		
		try {
			return jdbcTemplate.queryForList(sql.toString(), params);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<T> getPageData(Map<String, Object> conditions, int pageNum, int pageCount) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName);
		
		Set<String> keys = conditions.keySet();
		int size = keys.size();
		int length = size - 1;
		int index = 0;
		Object[] params = null;
		if (size > 0) {
			sql.append(" where ");
			params = new Object[size+2];
		}
		
		for (String key : keys) {
			if (index == length)
				sql.append(key).append(" =? ");
			else
				sql.append(key).append(" =? and ");
			params[index] = conditions.get(key);
			index++;
		}
		
		sql.append(" limit ? offset ?");
		params[index] = pageCount;
		params[index+1] = pageCount * (pageNum - 1);
		
		try {
			return jdbcTemplate.query(sql.toString(), params, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}


}
