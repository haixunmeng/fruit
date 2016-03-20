package fruit.market.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getDateString(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
}
