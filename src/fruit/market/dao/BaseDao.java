package fruit.market.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T> {

	public boolean add(T data);
	
}
