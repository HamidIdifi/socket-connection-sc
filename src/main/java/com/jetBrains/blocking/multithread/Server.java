package com.jetBrains.blocking.multithread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final List<Socket> sockets = new ArrayList<>();
    static int clientCount;

    public static void main(String[] args) {

        //create a thread that wait for a new connection here
        new Thread(() -> {
            System.out.println("Server is waiting for new connection");
            try {
                ServerSocket ss = new ServerSocket(1234);
                while (true) {
                    Socket socket = ss.accept();
                    clientCount++;
                    Server.sockets.add(socket);
                    String ip = socket.getRemoteSocketAddress().toString();
                    System.out.println("New Client Connection =>" + clientCount + " IP=" + ip);
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os, true);
                    pw.println("Welcome,your Id is" + clientCount + " and i'm waiting for data from you");
                    //create a thread to listen to each connected client
                    new Thread(() -> {
                        try {
                            String data;
                            while ((data = br.readLine()) != null) {
                                System.out.println("generating the response");
                                broadCastMessage(data, socket);

                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }

            } catch (IOException e) {
                throw new RuntimeException();
            }


        }
        ).start();

        //we can do whenever logic we want here

    }

    public static void broadCastMessage(String message, Socket from) {
        sockets.stream()
                .filter(socket -> !socket.equals(from))
                .forEach(socket -> {
                    try {
                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter printWriter = new PrintWriter(outputStream, true);
                        printWriter.println(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
    }


}
