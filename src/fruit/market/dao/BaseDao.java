package fruit.market.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T> {

	public boolean add(T data);
	
	public boolean batchAdd(List<T> ds);
	
	public T getData(String key);
	
	public T getSingleByCondition(Map<String, Object> conditions);
	
	public List<T> getByCondition(Map<String, Object> conditions);
	
}
