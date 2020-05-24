package com.java.socket;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

public class NIOTest {
    public static void main(String[] args) throws IOException {
        test6();
    }

//    public static void test7(){
//        AsynchronousFileChannel fileChannel =
//                AsynchronousFileChannel.open(path, StandardOpenOption.READ);
//
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        long position = 0;
//
//        Future<Integer> operation = fileChannel.read(buffer, position);
//
//        while(!operation.isDone());
//
//        buffer.flip();
//        byte[] data = new byte[buffer.limit()];
//        buffer.get(data);
//        System.out.println(new String(data));
//        buffer.clear();
//    }

    public static void test6() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("baidu.com", 80));

        ByteBuffer buffer = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(buffer);

        while(bytesRead != -1){
            System.out.println("Read " + bytesRead);
            buffer.flip();

            while(buffer.hasRemaining()){
                System.out.println((char)buffer.get()); //whether can remove char
            }

            buffer.clear();
            bytesRead = socketChannel.read(buffer);
        }

        socketChannel.close();
    }

    public static void test() throws IOException {
        RandomAccessFile file = new RandomAccessFile("nomal_io.txt", "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(48);
        int bufReads = channel.read(buffer);
        while(bufReads != -1){
            System.out.println("Read " + bufReads);
            buffer.flip();

            while(buffer.hasRemaining()){
                System.out.println((char)buffer.get()); //whether can remove char
            }

            buffer.clear();
            bufReads = channel.read(buffer);
        }

        file.close(); //whether channel is closed
    }
    public static void test1() throws IOException {

        RandomAccessFile file = new RandomAccessFile("nomal_io.txt", "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body   = ByteBuffer.allocate(10);

        ByteBuffer[] bufferArray = { header, body };

        long bufReads = channel.read(bufferArray);
        System.out.println("Read " + bufReads);

        header.flip();
        while(header.hasRemaining()){
            System.out.println("head " + (char)header.get());
        }

        body.flip();
        while (body.hasRemaining()){
            System.out.println("body " + (char) body.get());
        }

        body.clear();
        body.flip();
        while (body.hasRemaining()){
            System.out.println("body " + (char) body.get());
        }


        file.close(); //whether channel is closed


    }
    
    public static void test3() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("nomal_io.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("nomal_io1.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();
        System.out.println("count" + count);

        toChannel.transferFrom(fromChannel, position, count);
//
//        ByteBuffer buffer = ByteBuffer.allocate(1000);
//        int bufReads = toChannel.read(buffer);
//        while(bufReads != -1){
//            System.out.println("Read " + bufReads);
//            buffer.flip();
//
//            while(buffer.hasRemaining()){
//                System.out.println((char)buffer.get()); //whether can remove char
//            }
//
//            buffer.clear();
//            bufReads = toChannel.read(buffer);
//        }

        fromFile.close(); //whether channel is closed
        toFile.close();
    }

    public static void test4() throws IOException {
        Selector selector = Selector.open();
        SelectableChannel channel = new SelectableChannel() {
            @Override
            public SelectorProvider provider() {
                return null;
            }

            @Override
            public int validOps() {
                return 0;
            }

            @Override
            public boolean isRegistered() {
                return false;
            }

            @Override
            public SelectionKey keyFor(Selector sel) {
                return null;
            }

            @Override
            public SelectionKey register(Selector sel, int ops, Object att) throws ClosedChannelException {
                return null;
            }

            @Override
            public SelectableChannel configureBlocking(boolean block) throws IOException {
                return null;
            }

            @Override
            public boolean isBlocking() {
                return false;
            }

            @Override
            public Object blockingLock() {
                return null;
            }

            @Override
            protected void implCloseChannel() throws IOException {

            }
        };
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = (SelectionKey) keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }

    public static void test5() throws IOException {
        String test = "test michael hello new year";

        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.put(test.getBytes());

        RandomAccessFile toFile = new RandomAccessFile("nomal_io.txt", "rw");
        FileChannel channel = toFile.getChannel();

        System.out.println(channel.size());
//        channel.position(channel.position() +200);


        buffer.flip();
        while(buffer.hasRemaining()){
            channel.write(buffer);
        }

        System.out.println(channel.size());

        toFile.close();

    }
}
