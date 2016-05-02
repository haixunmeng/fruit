package fruit.market.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.OrderDao;
import fruit.market.data.Order;
import fruit.market.exception.FruitException;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao{
	
	private static Logger logger = Logger.getLogger(OrderDaoImpl.class);

	OrderDaoImpl(){
		tableName = "fruit_order";
		primaryKey = "order_id";
	}

	@Override
	public List<Order> getUnproccessedOrder(Map<String, Object> conditions, int pageNum, int pageCount) {
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
		
		sql.append(" and order_status != 'finished' and order_status != 'canceled' order by create_time limit ? offset ? ");
		params[index] = pageCount;
		params[index+1] = pageCount * (pageNum - 1);
		try {
			return jdbcTemplate.query(sql.toString(), params, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}

	@Override
	public List<Order> getHistoryOrder(Map<String, Object> conditions, Integer pageNum, Integer pageCount) {
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
		
		sql.append(" and (order_status = 'finished' or order_status = 'canceled') order by create_time limit ? offset ? ");
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
