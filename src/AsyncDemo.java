import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncDemo {
	
	private int i=0;
    private static void doSomeTask() {
        System.out.println("Hello World");
    }
    private static void onCompletion() {
        System.out.println("All tasks finished");
    }
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final CountDownLatch latch = new CountDownLatch(2);
        System.out.println(" caller thread is: " + Thread.currentThread());
        executor.execute(new Task(latch));
        executor.execute(new Task(latch));
        executor.execute(() -> {
            try {
            	System.out.println(" caller thread is: " + Thread.currentThread());
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onCompletion();
        });
        executor.shutdown();
    }
    private static class Task implements Runnable {
        /**
         * CountDownLatch 是JDK提供的一个简单的线程监测工具
         * 基于简单的计数，调用countDown()方法表明当前线程已经终止
         * 在监测线程中调用await()方法,该方法会一直挂起直到所有其它线程终止
         */
    	private int j=0;
        private final CountDownLatch latch;
        public Task(CountDownLatch latch) {
            this.latch = latch;
        }
        @Override
        public void run() {
            try {
            	System.out.println(" caller thread is: " + Thread.currentThread());
                doSomeTask();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}