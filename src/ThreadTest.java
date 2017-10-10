import java.util.Random;
import java.util.StringTokenizer;

public class ThreadTest {
	
	private int count0 = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String s = "0|www.impUrls1.com|-1|www.impUrls2.com|-2|www.impUrls3.com|10|www.impUrls4.com";
////		String ss = s.replaceAll("|.+|", "");
////		System.out.println(ss);
//		String[] ss = s.split("\\|");
//		StringBuilder sb = new StringBuilder();
//		for(int i=0; i<ss.length; i++){
//			if(i % 2 != 0){
//				sb.append(ss[i]).append("|");
//			}
//		}
//		sb.deleteCharAt(sb.length()-1);
//		System.out.println(sb.toString());
		
		Random ran = new Random(47);
		for(int i=0; i< 100; i++){
			int s = ran.nextInt(5);
			System.out.println(s);
		}
	}

}
