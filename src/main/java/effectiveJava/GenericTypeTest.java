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
		//List<Object>是可以容纳所有类型的list，而list2只能容纳String
//		test(list2);
//		List<String>.class;
		Object o = null;
		if(o instanceof List<?>) {
			List<?> list3 = (List<?>) o;
		}
	}
	
	public void test(List<?> list) {
		list.add(null);
	}
	
	public void test2(List list) {
		
	}

}
