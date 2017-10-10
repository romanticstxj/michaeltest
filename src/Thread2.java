import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Thread2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
//		demo2();
		demo8();
	}

	private static void demo2() {
	    Thread A = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            printNumber("A");
	        }
	    });
	    Thread B = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            System.out.println("B 开始等待 A");
	            try {
	                A.join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            printNumber("B");
	        }
	    });
	    Thread C = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            System.out.println("C 开始等待 B");
	            try {
	                B.join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            printNumber("C");
	        }
	    });
	    B.start();
	    A.start();
	    C.start();
	}
	
	private static void printNumber(String threadName) {
	    int i=0;
	    while (i++ < 3) {
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println(threadName + "print:" + i);
	    }
	}
	
	private static void demo3() {
	    Object lock = new Object();
	    Thread A = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            synchronized (lock) {
	                System.out.println("A 1");
	                try {
	                    lock.wait();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                System.out.println("A 2");
	                try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                System.out.println("A 3");
	                lock.notify();
	            }
	        }
	    });
	    Thread B = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            synchronized (lock) {
	                System.out.println("B 1");
	                System.out.println("B 2");
	                lock.notify();
	                try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                System.out.println("B 3");
	                System.out.println("B end");
	            }
	        }
	    });
	    A.start();
	    B.start();
	}
	
	private static void demo4(){
		int worker = 3;
		CountDownLatch latch = new CountDownLatch(worker);
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("waiting for other workers");
				try {
					latch.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("D begin to work");
			}
		}).start();
		for(char from = 'A'; from <= 'C'; from ++){
			final String fromStr = String.valueOf(from);
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(fromStr + " is working");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(fromStr + " has finished");
					latch.countDown();
				}
				
			}).start();
		}
		
	}
	
	private static void demo5(){
		int runner = 4;
		final Random random = new Random(47);
		CyclicBarrier barrier = new CyclicBarrier(runner);
		for(char from = 'A'; from <= 'D'; from ++){
			final String fromStr = String.valueOf(from);
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					long prepareTime = random.nextInt(10000) + 100;
					System.out.println(fromStr + " begin to prepare");
					try {
						Thread.sleep(prepareTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						System.out.println(fromStr + " has prepared");
						barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(fromStr + " begin to run");
				}
				
			}).start();
		}
	}
	
	private static void demo6(){
		Callable<Integer> callable = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread() + "begin to work");
				Thread.sleep(1000);
				System.out.println(Thread.currentThread() + "work finished");
				int result = 5050;
				return result;
			}
		};
		
		FutureTask<Integer> futureTask = new FutureTask<>(callable);
		new Thread(futureTask).start();
		
		try {
			System.out.println(Thread.currentThread()+ " Result = " + futureTask.get(0, TimeUnit.SECONDS));
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
		}
		System.out.println(Thread.currentThread()+ " task finished");
		
	}
	
	private static void demo7(){
		int num = 5;
		ExecutorService es = Executors.newFixedThreadPool(num);
		final Random random = new Random(47);
		List<Future<String>> futures = new ArrayList<>();
		for(int i=0; i<num; i++){
			final int ii = i;
			Future<String> future = es.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					long prepareTime = random.nextInt(10000) + 100;
					Thread.sleep(prepareTime);
					return Thread.currentThread().getName() + "执行完任务：" + ii;
				}});
			futures.add(future);
		}
		
		while(num > 0){
			for(int i=0; i<futures.size(); i++){
				String result = null;
				try {
					result = futures.get(i).get(0, TimeUnit.SECONDS);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
				}
				
				if(result != null){
					System.out.println(num + " future finished for " + result);
					futures.remove(i);
					num --;
//					break;
				}
			}
		}
		
		es.shutdown();
	}
	
	private static void demo8() throws InterruptedException, ExecutionException{
		int num = 5;
		ExecutorService es = Executors.newFixedThreadPool(num);
		final Random random = new Random(47);
		CompletionService<String> cs = new ExecutorCompletionService<>(es);
		for(int i=0; i<num; i++){
			final int ii = i;
			cs.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					long prepareTime = random.nextInt(10000) + 100;
					Thread.sleep(prepareTime);
					return Thread.currentThread().getName() + "执行完任务：" + ii;
				}});
		}
		
		for(int i=0; i<num; i++){
			System.out.println(cs.take().get());
		}
		
		System.out.println("main thread end");
		es.shutdown();
	}
}
