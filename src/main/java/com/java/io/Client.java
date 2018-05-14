package com.java.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try
        {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
//            socketChannel.connect(new InetSocketAddress("localhost",8080));
            socketChannel.connect(new InetSocketAddress("172.16.25.48", 1234));
 
            if(socketChannel.finishConnect())
            {
                int i=0;
                while(true)
                {
                    TimeUnit.SECONDS.sleep(10);
                    String info = "I'm "+i+++"-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while(buffer.hasRemaining()){
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }
                    
                    long bytesRead = socketChannel.read(buffer2);
                    while(bytesRead>0){
                    	buffer2.flip();
                        while(buffer2.hasRemaining()){
                            System.out.print((char)buffer2.get());
                        }
                        System.out.println();
                        buffer2.clear();
                        bytesRead = socketChannel.read(buffer2);
                    }
//                    if(bytesRead == -1){
//                    	socketChannel.close();
//                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
	}

}
