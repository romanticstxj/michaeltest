import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIConcurrentIterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String item = iterator.next();
			if ("2".equals(item)) {
				iterator.remove();
			}
		}

//		for (String item : list) {
//			if ("2".equals(item)) {
//				list.remove(item);
//			}
//		}
//		
		System.out.println(list);
		
		List<>
	}

}
