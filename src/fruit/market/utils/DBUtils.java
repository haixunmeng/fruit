package fruit.market.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DBUtils {
	
	private static List<String> sqlKeyWords = new ArrayList<String>(Arrays.asList("limit", "offset", "order by"));
	
	public static String generateSQL(String tableName, Map<String, String> conditions){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ").append(tableName);
		
		if(!(null == conditions || conditions.isEmpty())){
			if(!(conditions.size() <= 3 && sqlKeyWords.containsAll(conditions.keySet()))){
				sql.append(" where ");
			}
			
			for(Map.Entry<String, String> entry : conditions.entrySet()){
				String key = entry.getKey();
				if("limit".equals(key) || "offset".equals(key) || "order by".equals(key)){
					sql.append(" ").append(entry.getKey()).append(" ").append(entry.getValue());
				}else{
					sql.append(entry.getKey()).append(" = '").append(entry.getValue()).append("' ");
				}
			}
		}
		
		return sql.toString();
	}

}
