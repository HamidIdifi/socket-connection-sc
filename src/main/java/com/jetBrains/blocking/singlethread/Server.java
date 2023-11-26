package com.jetBrains.blocking.singlethread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("Server is waiting for new connection");
        Socket socket = ss.accept();//this method is blocking
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        System.out.println("Server is waiting for data to come");
        int nb = is.read();
        System.out.println("generating the response");
        int res = nb * 23;
        os.write(res);
        socket.close();
    }
}
