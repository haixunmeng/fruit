package fruit.market.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fruit.market.data.Selling;

@Repository
public interface SellingDao extends BaseDao<Selling>{

	public List<Selling> getStoreSellingGoods(String store_id);

}
