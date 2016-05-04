package fruit.market.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import fruit.market.dao.NoticeDao;
import fruit.market.data.Notice;
import fruit.market.exception.FruitException;

@Repository
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDao{
	
	private static Logger logger = Logger.getLogger(NoticeDaoImpl.class);
	
	NoticeDaoImpl(){
		tableName = "fruit_notice";
		primaryKey = "notice_id";
	}

	@Override
	public Notice getLatestNotice() {
		StringBuffer sql = new StringBuffer();

		sql.append("select * from ").append(tableName).append(" where ").append("create_time = (select max(create_time) from ").append(tableName).append(")");

		try {
			return jdbcTemplate.queryForObject(sql.toString(), rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException e) {
			logger.error(FruitException.DB_OPTION_EXCEPTION);
			throw FruitException.DB_OPTION_EXCEPTION;
		}
	}
}
