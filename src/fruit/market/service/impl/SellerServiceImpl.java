package fruit.market.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.GoodDao;
import fruit.market.dao.SellingDao;
import fruit.market.dao.StockInDetailDao;
import fruit.market.dao.StoreDao;
import fruit.market.data.Good;
import fruit.market.data.GoodStatus;
import fruit.market.data.Selling;
import fruit.market.data.StockInDetail;
import fruit.market.data.User;
import fruit.market.service.SellerService;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class SellerServiceImpl implements SellerService{
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private SellingDao sellingDao;
	
	@Autowired
	private StockInDetailDao stockInDetailDao;

	@Override
	public void shelfGood(HttpServletRequest request) {
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload upload = new ServletFileUpload(factory); 
			List<FileItem> items = upload.parseRequest(request);
			
			Map<String, String> params = new HashMap<String, String>();
			
			for(FileItem item : items){
				if(item.isFormField()){
					params.put(item.getFieldName(), item.getString("utf-8"));
				}else{
					String path = request.getSession().getServletContext().getRealPath("/") + "upload\\" + params.get("good_id") + ".jpg";
					File file = new File(path);
					if(!file.exists()){
						file.createNewFile();
					}
					item.write(file);
					
					params.put("good_photo_url", "/fruit/upload/"+params.get("good_id")+".jpg");
				}
			}
			
			Good good = goodDao.getData(params.get("good_id"));
			
			good.setGood_desc(params.get("good_desc"));
			good.setGood_photo_url(params.get("good_photo_url"));
			
			goodDao.update(good);
			
			Selling selling = new Selling();
			
			selling.setSelling_id(Utils.get_uuid());
			selling.setGood_id(params.get("good_id"));
			User user = CacheManager.get(params.get("token"), User.class);
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("user_id", user.getUser_id());
			selling.setStore_id(storeDao.getSingleByCondition(conditions).getStore_id());
			conditions.clear();
			conditions.put("good_id", params.get("good_id"));
			StockInDetail stockInDetail = stockInDetailDao.getSingleByCondition(conditions);
			selling.setValue_unit(stockInDetail.getValue_unit());
			selling.setOut_price(BigDecimal.valueOf(Double.valueOf(String.valueOf(params.get("sale_price")))));
			selling.setPackage_type(stockInDetail.getPackage_type());
			selling.setPackage_weight(stockInDetail.getPackage_weight());
			selling.setPackage_deposit(stockInDetail.getPackage_deposit());
			selling.setGood_status(GoodStatus.SELLING);
			selling.setCreate_time(DateUtil.getDateString());
			
			sellingDao.add(selling);
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

}
