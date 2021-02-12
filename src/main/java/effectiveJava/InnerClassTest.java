package effectiveJava;

public class InnerClassTest {
	
	private int count;
	
	protected static String str;
	
	InnerClass innerClass;
	
	private static class InnerClass{
		
		public void test() {
			System.out.println(str);
//			System.out.println(count);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InnerClassTest.InnerClass innerClass = new InnerClassTest.InnerClass();
		innerClass.test();
	}

}
