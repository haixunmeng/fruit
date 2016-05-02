package fruit.market.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.OrderDetailDao;
import fruit.market.data.OrderDetail;
import fruit.market.exception.FruitException;

@Repository
public class OrderDetailDaoImpl extends BaseDaoImpl<OrderDetail> implements OrderDetailDao{
	
	private static Logger logger = Logger.getLogger(OrderDetailDaoImpl.class);

	OrderDetailDaoImpl(){
		tableName = "fruit_order_detail";
		primaryKey = "order_detail_id";
	}

	@Override
	public List<OrderDetail> getOrderDetail(String order_id) {

		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName).append(" where order_id=?");
		
		try {
			return jdbcTemplate.query(sql.toString(), new Object[] {order_id}, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
}
