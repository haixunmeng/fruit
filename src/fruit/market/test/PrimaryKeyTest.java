package fruit.market.test;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

public class PrimaryKeyTest {
	
	@Test
	public void primaryKeyTest(){
		
		System.out.println(new Date().getTime());
		
		System.out.println(RandomStringUtils.randomAlphanumeric(5));
		
		System.out.println(RandomStringUtils.randomAscii(5));
		
		System.out.println(RandomStringUtils.randomNumeric(5));
		
		System.out.println(RandomStringUtils.randomAlphabetic(5));
	}

}
