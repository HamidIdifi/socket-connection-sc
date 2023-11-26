package com.jetBrains.blocking.singlethread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",1234);
        InputStream is = socket.getInputStream();
        OutputStream os=socket.getOutputStream();
        System.out.println("sending a number to the client...");
        os.write(10);
        int result = is.read();
        System.out.println("the result is :"+result);
    }
}
