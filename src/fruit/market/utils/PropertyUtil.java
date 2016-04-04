package fruit.market.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import fruit.market.exception.FruitException;

public class PropertyUtil {
	
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
	
	private static Properties properties;

	private static void init() {
		properties = new Properties();
		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/resources/properties.properties");
		
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.info(FruitException.LOAD_PROPERTIES_EXCEPTION);
			throw FruitException.LOAD_PROPERTIES_EXCEPTION;
		}
	}

	public static String getProperty(String property) {
		if (null == properties) {
			init();
		}

		return properties.getProperty(property).trim();
	}
}
