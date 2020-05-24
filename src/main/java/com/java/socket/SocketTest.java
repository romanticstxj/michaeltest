package com.java.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketTest {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("jenkov.com", 80);
        OutputStream out = socket.getOutputStream();

        out.write("some data".getBytes());
        out.flush();
        out.close();

        socket.close();
    }
}
