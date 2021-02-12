package com.java.multithread;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***********************************************************************************************************************
 * FileName - CompletableFutureTest.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: Aug 19, 2019 $
 **********************************************************************************************************************/

public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        // Run a task specified by a Supplier object asynchronously
        // Using Lambda Expression
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });
        
        future.thenAccept(result -> {
        	System.out.println(Thread.currentThread() + "  " + result);
        });
        
//        // Block and get the result of the Future
        System.out.println(Thread.currentThread() + "ready to end program");
        
        while(true) {
            
        }
    	
//    	acceptEitherTest();
//    	testException();
    }
    
    public static void acceptEitherTest() {
    	Random random = new Random();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(()->{

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "from future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "from future2";
        });

        CompletableFuture<Void> future =  future1.acceptEither(future2,str->System.out.println("The future is "+str));

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    public static void testException() {
    	CompletableFuture.supplyAsync(() -> "hello world")
        .thenApply(s -> {
            s = null;
            int length = s.length();
            return length;
        }).thenAccept(i -> System.out.println(i))
        .exceptionally(t -> {
            System.out.println("Unexpected error:" + t);
            return null;
        });
    }

}
