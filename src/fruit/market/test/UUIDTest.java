package fruit.market.test;

import org.junit.Test;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDTest {
	
	@Test
	public void test(){
		
		TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();
		
		System.out.println(timeBasedGenerator.generate().toString());
		
	}

	
	
	
}
