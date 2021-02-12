
public class Test222 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(climb2(10));
	}
	
	public static int climb2(int n) {
        if (n == 1) {
            return 1;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second =third;
        }

        return second;
    }

}
