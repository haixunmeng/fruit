package fruit.market.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.SellingDao;
import fruit.market.data.Selling;
import fruit.market.exception.FruitException;

@Repository
public class SellingDaoImpl extends BaseDaoImpl<Selling> implements SellingDao{
	
	private static Logger logger = Logger.getLogger(SellingDaoImpl.class);

	SellingDaoImpl(){
		tableName = "fruit_selling";
		primaryKey = "good_id";
	}

	@Override
	public List<Selling> getStoreSellingGoods(String store_id) {

		StringBuffer sql = new StringBuffer();

		sql.append("select * from ").append(tableName).append(" where store_id=? and good_status='selling'");
		try {
			return jdbcTemplate.query(sql.toString(), new Object[]{store_id}, rowMapper);
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
	
}
