/***********************************************************************************************************************
 * FileName - VolatileTest.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: May 24, 2019 $
 **********************************************************************************************************************/

public class VolatileTest implements Runnable{
    
    boolean running = true;
    
    int i =0;

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        VolatileTest task = new VolatileTest();
        Thread th = new Thread(task);
        th.start();
        Thread.sleep(10);
        task.running = false;
        Thread.sleep(1000);
        System.out.println(task.i);
        System.out.println("end game");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(running) {
            i++;
        }
    }

}
