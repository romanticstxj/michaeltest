import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		test1();
		test2();
		
	}
	
	public static void test2(){
		ExecutorService es = Executors.newCachedThreadPool();
		final Map<Long, Integer> map = new HashMap<>();
		final Random ran = new Random(47);
		for(int i=0; i<10; i++){
			es.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Map map2 = new HashMap();
					for(int i=0; i< 10; i++){
						int value = ran.nextInt();
						System.out.println("Thread id " + Thread.currentThread().getId() + " put value " + value);
						map.put(Thread.currentThread().getId(), value);
						System.out.println(map.get(Thread.currentThread().getId()) == value);
					}
				}
			});
		}
		es.shutdown();
	}
	
	public static void test1(){
		ExecutorService es = Executors.newCachedThreadPool();
		final Map map = new ConcurrentHashMap<>();
		for(int i=0; i<100; i++){
			es.execute(new MyTask(i, map));
		}
		es.shutdown();
		
		System.out.println("final count: " + map.get(1));
	}
	
	private static class MyTask implements Runnable{
		
		private int count;
		private Map map;
		public MyTask(int count, Map map) {
			this.count = count;
			this.map = map;
		}
		
		@Override
		public void run() {
			System.out.println(count);
			synchronized (map) {
				map.put(1, count);
			}
		}
	}


}
