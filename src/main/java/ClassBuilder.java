import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassBuilder {

	private String name;
	
	private int age;
	
	public ClassBuilder setName(String name){
		this.name = name;
		return this;
	}
	
	public ClassBuilder setAge(int age){
		this.age = age;
		return this;
	}
	
	@Override
	public String toString(){
		return name + age;
	}
	
	public static void main(String[] args){
//		ClassBuilder cb = new ClassBuilder().setAge(20).setName("michael");
//		System.out.println(cb);
		
		List<String> list = new ArrayList<String>();
		list.add("3");
		list.add("dd");
		Set<String> result = new HashSet<>();
		Set<String> result2 = new HashSet<>();
		result.addAll(list);
		result2.add("fff");
		result2.add("3");
		if(result2.retainAll(result)){
			for(String s : result2){
				System.out.println(s);
			}
		}
		
		
	}
}
