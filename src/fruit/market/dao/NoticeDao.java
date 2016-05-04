package fruit.market.dao;

import org.springframework.stereotype.Repository;

import fruit.market.data.Notice;

@Repository
public interface NoticeDao extends BaseDao<Notice>{

	public Notice getLatestNotice();
	
}
