package fruit.market.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fruit.market.data.User;

@Repository
public interface BaseDao<T> {

	public boolean add(T data);
	
	public boolean batchAdd(List<T> ds);
	
	public boolean delete(Map<String, Object> data);
	
	public boolean batchDelete(Map<String, Object>[] data);
	
	public boolean update(T data);
	
	public boolean update(Map<String, Object> data);
	
	public boolean batchUpdate(List<T> datas);
	
	public boolean batchUpdate(Map<String, Object>[] data);
	
	public T getData(String key);
	
	public List<T> getOnePage(int pageNum, int pageCount);
	
	public List<Map<String, Object>> getPageData(int pageNum, int pageCount);
	
	public List<Map<String, Object>> getPageMapData(Map<String, Object> conditions, int pageNum, int pageCount);
	
	public List<T> getPageData(Map<String, Object> conditions, int pageNum, int pageCount);
	
	public T getSingleByCondition(Map<String, Object> conditions);
	
	public List<T> getByCondition(Map<String, Object> conditions);
	
}
