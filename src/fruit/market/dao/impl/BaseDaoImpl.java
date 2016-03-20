package fruit.market.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import fruit.market.dao.BaseDao;
import fruit.market.exception.FruitException;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

	protected String tableName;

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	protected JdbcTemplate jdbcTemplate;

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

}
