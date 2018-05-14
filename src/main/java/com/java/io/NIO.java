package com.java.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		method2();
	}
	
	public static void method1(){
	       InputStream in = null;
	       try{
	           in = new BufferedInputStream(new FileInputStream("src/nomal_io.txt"));
	 
	           byte [] buf = new byte[1024];
	           int bytesRead = in.read(buf);
	           while(bytesRead != -1)
	           {
	               for(int i=0;i<bytesRead;i++)
	                   System.out.print((char)buf[i]);
	               bytesRead = in.read(buf);
	           }
	       }catch (IOException e)
	       {
	           e.printStackTrace();
	       }finally{
	           try{
	               if(in != null){
	                   in.close();
	               }
	           }catch (IOException e){
	               e.printStackTrace();
	           }
	       }
	   }

	public static void method2(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("src/nomal_io.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
 
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);
 
            while(bytesRead != -1)
            {
                buf.flip();
                while(buf.hasRemaining())
                {
                    System.out.print((char)buf.get());
                } //每次会把buf里的缓存数据读完
                System.out.println("before compact " + buf.position());
                buf.compact();
                System.out.println("after compact " + buf.position());
                bytesRead = fileChannel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
