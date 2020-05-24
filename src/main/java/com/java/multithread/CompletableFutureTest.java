package com.java.multithread;
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
        
        future.thenAcceptAsync(result -> {System.out.println(Thread.currentThread() + "  " + result);});
        
//        // Block and get the result of the Future
        System.out.println("ready to end program");
        
        while(true) {
            
        }
    }

}
