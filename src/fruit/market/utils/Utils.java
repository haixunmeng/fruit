package fruit.market.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import fruit.market.exception.FruitException;

import com.alibaba.fastjson.JSON;

public class Utils {
	
	private static Logger logger = Logger.getLogger(Utils.class);
	
	private static Properties properties;
	
	static{
		properties = new Properties();
		InputStream in = Utils.class.getResourceAsStream("/config.properties"); 
        try { 
        	properties.load(in); 
        } catch (IOException e) {
        	throw FruitException.LOAD_PROPERTIES_EXCEPTION;
        } 
	}
	
	public static Properties getProperties(){
		return properties;
	}

	public static Map<String, Object> readParameters(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		
		try {
			request.setCharacterEncoding("utf-8");
			
			BufferedReader in = request.getReader();
			char[] buf;
			int contentLength = request.getContentLength();
			if (contentLength > 0) {
				buf = new char[contentLength];
			} else {
				buf = new char[4 * 1024];
			}
			int readLength;
			while ((readLength = in.read(buf, 0, buf.length)) > 0) {
				sb.append(buf, 0, readLength);
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.info(FruitException.UNSUPPORTED_ENCODING);
			throw FruitException.UNSUPPORTED_ENCODING;
		} catch (IOException e) {
			logger.info(FruitException.RW_PARAMETER_EXCEPTION);
			throw FruitException.RW_PARAMETER_EXCEPTION;
		}

		Map<String, Object> params =  JSON.parseObject(sb.toString());
		
		return params;
	}
	
	public static void writeMessage(HttpServletResponse response, Map<String, Object> resMeg) {
		
		String res_msg = JSON.toJSONString(resMeg);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(res_msg);
			out.flush();
		} catch (IOException e) {
			logger.info(FruitException.RW_PARAMETER_EXCEPTION);
			throw FruitException.RW_PARAMETER_EXCEPTION;
		}
	}
	
	public static String get_uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
}
