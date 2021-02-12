package effectiveJava;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GenericTypeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add("test");
		list.add(1);
		
		for(Object s: list) {
			
		}
		
		List<String> list2 = new ArrayList<>();
		test(list2);
	}
	
	public void test(List<Object> list) {
		
	}

}
