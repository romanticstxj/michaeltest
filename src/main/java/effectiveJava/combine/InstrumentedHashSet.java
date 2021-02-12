package effectiveJava.combine;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class InstrumentedHashSet<E> extends HashSet<E>{
	
	private int addCount = 0;
	
	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}
	
	public int getAddCount() {
		return addCount;
	}
	
	public static void main(String[] argsStrings) {
		InstrumentedHashSet<String> set = new InstrumentedHashSet<>();
		set.addAll(Arrays.asList("test", "test1", "test2"));
		System.out.println(set.getAddCount());
	}

}
