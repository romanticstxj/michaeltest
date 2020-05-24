package com.michael.designpattern.chain;

public class Main {

    public static void main(String[] args){
        // 初始化责任链：handler1 -> handler2 -> handler3
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();
        handler2.setNextHandler(handler3);
        handler1.setNextHandler(handler2);
        // 处理事件
        System.out.println("--------------Message1");
        handler1.handleMessage(1);
        System.out.println("--------------Message2");
        handler1.handleMessage(2);
        System.out.println("--------------Message3");
        handler1.handleMessage(4);
        System.out.println("--------------Message4");
        handler1.handleMessage(7);
    }
}
