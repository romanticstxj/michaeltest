package com.java.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class NIOServer {
    private static final int BUF_SIZE=1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;
 
    public static void main(String[] args) throws InterruptedException
    {
        selector();
    }
 
    public static void handleAccept(SelectionKey key) throws IOException{
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
//        Selector selector = key.selector();
//        selector.select();
//        System.out.println(sc);
        sc.configureBlocking(false);
        SelectionKey selectionKey = sc.register(key.selector(), SelectionKey.OP_READ + SelectionKey.OP_WRITE, 
        		ByteBuffer.allocateDirect(BUF_SIZE));
//        System.out.println(selectionKey);
    }
 
    public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer)key.attachment();
        long bytesRead = sc.read(buf);
        while(bytesRead>0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }
 
    public static void handleWrite(SelectionKey key, int count) throws IOException, InterruptedException{
        ByteBuffer buf = (ByteBuffer)key.attachment();
//        System.out.println(buf);
        TimeUnit.SECONDS.sleep(5);
        String info = "I'm "+ count +"-th information from server";
        buf.put(info.getBytes());
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }
 
    public static void selector() throws InterruptedException {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        int count = 0;
        try{
            selector = Selector.open();
            ssc= ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
//            System.out.println(selectionKey);
 
            while(true){
            	int selected = selector.select(TIMEOUT);
//            	System.out.println("selected = " + selected);
                if(selected == 0){
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
//                System.out.println("begin selectedKeys");
                while(iter.hasNext()){
                    SelectionKey key = iter.next();
//                    System.out.println("interestOpts: " + key.interestOps());
//                    System.out.println("readyOps: " + key.readyOps());
//                    System.out.println("channel: " + key.channel());
//                    System.out.println("selector: " + key.selector());
                    if(key.isAcceptable()){
                    	System.out.println(key.channel() + "accept");
                        handleAccept(key);
                    }
                    if(key.isReadable()){
//                    	System.out.println(key.channel() + "read");
                        handleRead(key);
                    }
                    if(key.isWritable() && key.isValid()){
//                    	System.out.println(key.channel() + "write");
                        handleWrite(key, count++);
                    }
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }
//                System.out.println("end selectedKeys");
            }
 
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(selector!=null){
                    selector.close();
                }
                if(ssc!=null){
                    ssc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
