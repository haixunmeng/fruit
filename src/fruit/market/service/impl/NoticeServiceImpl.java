package fruit.market.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fruit.market.cache.CacheManager;
import fruit.market.dao.NoticeDao;
import fruit.market.data.Notice;
import fruit.market.data.User;
import fruit.market.service.NoticeService;
import fruit.market.utils.DateUtil;
import fruit.market.utils.Utils;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeDao noticeDao;

	@Override
	public void addNotice(Map<String, String> params) {
		
		Notice notice = new Notice();
		
		notice.setNotice_id(Utils.get_uuid());
		notice.setContent(params.get("notice"));
		notice.setCreate_time(DateUtil.getTimestamp());
		
		User user = CacheManager.get(params.get("token"), User.class);
		notice.setCreator(user.getUser_id());
		
		noticeDao.add(notice);
	}

	@Override
	public String getLatestNotice() {
		
		Notice notice = noticeDao.getLatestNotice();
		
		if(notice == null){
			return "";
		}
		
		return notice.getContent();
	}

}
