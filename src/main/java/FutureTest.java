import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/***********************************************************************************************************************
 * FileName - FutureTest.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: Aug 19, 2019 $
 **********************************************************************************************************************/

public class FutureTest {

        public static void main(String[] args) {
            long startTime = System.currentTimeMillis();
            Callable<Integer> calculateCallable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    // TODO Auto-generated method stub
                    Thread.sleep(10000);//模拟耗时时间
                    int result = 1+2;
                    return result;
                }
            };
            FutureTask<Integer> calculateFutureTask = new FutureTask<>(calculateCallable);
            Thread t1 = new Thread(calculateFutureTask);
            t1.start();
            //现在加入Thread运行的是一个模拟远程调用耗时的服务，并且依赖他的计算结果（比如网络计算器）
            try {
                //模拟耗时任务，主线程做自己的事情，体现多线程的优势
                Thread.sleep(1000);
                int a = 3+5;
                Integer result = calculateFutureTask.get(5, TimeUnit.SECONDS);
                System.out.println("result = "+(a+result));//模拟主线程依赖子线程的运行结果
                long endTime = System.currentTimeMillis();
                System.out.println("time = "+(endTime-startTime)+"ms");
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TimeoutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println("end program");
        }

}
