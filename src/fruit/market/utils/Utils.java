package fruit.market.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import fruit.market.exception.FruitException;
import fruit.market.session.SessionManager;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.fasterxml.uuid.Generators;

public class Utils {

	private static Logger logger = Logger.getLogger(Utils.class);

	private static Properties properties;

	private static void init() {
		properties = new Properties();
		InputStream in = Utils.class.getResourceAsStream("/resources/properties.property");
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.info(FruitException.LOAD_PROPERTIES_EXCEPTION);
			throw FruitException.LOAD_PROPERTIES_EXCEPTION;
		}
	}

	public static String getProperties(String property) {
		if (null == properties) {
			init();
		}

		return properties.getProperty(property).trim();
	}

	public static Map<String, Object> readParameters(HttpServletRequest request) {
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

		Map<String, Object> params = JSON.parseObject(sb.toString());

		if(null == params){
			params = new HashMap<String, Object>();
		}
		
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

	public static String get_uuid() {
		
		return Generators.timeBasedGenerator().generate().toString().replaceAll("-", "");
	}

	public static String encrypt(String pwd) {
		
		String encryptSalt = DigestUtils.md5Hex(getProperties("fruit.encrypt.salt"));
		
		StringBuffer mixedPwd = new StringBuffer();

		mixedPwd.append(encryptSalt.substring(0, 16)).append(pwd.substring(0, 8))
				.append(encryptSalt.substring(16, 24)).append(pwd.substring(8, 16))
				.append(encryptSalt.substring(24, 28)).append(pwd.substring(16, 32))
				.append(encryptSalt.substring(28, 32));
		
		String encryptedPwd = DigestUtils.md5Hex(mixedPwd.toString());
		
		return encryptedPwd;
	}

	public static boolean checkPwd(String pwd, String commit_pwd) {

		if(pwd.equals(encrypt(commit_pwd))){
			return true;
		}else{
			return false;
		}
	}
	
	public static String getPassCode(){
		// 在内存中创建图象
		int width = 75, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(5位数字)
		String sRand = "";
		for (int i = 0; i < 5; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}

		// 图象生效
		g.dispose();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			ImageIO.write(image, "JPEG", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BASE64Encoder encoder = new BASE64Encoder();
		
		return sRand + ":" + encoder.encode(outputStream.toByteArray());
	}
	
	private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static void checkPassCode(Map<String, Object> params) {

		String passCodeCommited = (String) params.get("passCode");
		
		String sessionId = (String) params.get("sessionId");
		
		String localPassCode = SessionManager.get4session(sessionId, "passCode");
		
		if(!localPassCode.equals(passCodeCommited)){
			logger.info(FruitException.PASSCODE_ERROR_EXCEPTION);
			throw FruitException.PASSCODE_ERROR_EXCEPTION;
		}
		
		SessionManager.save2session(sessionId, "passed", "true");
		
	}
}
