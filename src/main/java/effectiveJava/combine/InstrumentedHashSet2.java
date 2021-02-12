package effectiveJava.combine;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

public class InstrumentedHashSet2<E> extends HashSet<E>{
	
	private int addCount = 0;
	private final Set<E> set;
	
	public InstrumentedHashSet2(Set<E> set) {
		this.set = set;
	}
	
	@Override
	public boolean add(E e) {
		addCount++;
		return set.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return set.addAll(c);
	}
	
	public int getAddCount() {
		return addCount;
	}

	public static void main(String[] args) {
		InstrumentedHashSet2<String> set = new InstrumentedHashSet2<>(Sets.newHashSet());
		set.addAll(Arrays.asList("test", "test1", "test2"));
		System.out.println(set.getAddCount());
	}

}
