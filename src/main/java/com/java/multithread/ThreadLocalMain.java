package com.java.multithread;

public class ThreadLocalMain {
	/**
     * ThreadLocal变量，每个线程都有一个副本，互不干扰
     */
    public static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        new ThreadLocalMain().execute();
    }

    public void execute() throws Exception {
        // 主线程设置值
        HOLDER.set("程序新视界");
        System.out.println(Thread.currentThread().getName() + "线程ThreadLocal中的值：" + HOLDER.get());

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程ThreadLocal中的值：" + HOLDER.get());
            // 设置当前线程中的值
            HOLDER.set("《程序新视界》");
            System.out.println("重新设置之后，" + Thread.currentThread().getName() + "线程ThreadLocal中的值：" + HOLDER.get());
            System.out.println(Thread.currentThread().getName() + "线程执行结束");
        }).start();
        // 等待所有线程执行结束
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() + "线程ThreadLocal中的值：" + HOLDER.get());
    }

}
