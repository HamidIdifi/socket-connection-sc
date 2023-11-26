package com.jetBrains.blocking.multithread;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        new Thread(() -> {
            try {
                String request;
                while ((request = br.readLine()) != null) {
                    String response = br.readLine();
                    System.out.println(response);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String request = scanner.nextLine();
            pw.println(request);
        }
        //dans la partie client il faut toujours separe qui ecoute les reponse et la partie qui envoi les requets
    }
}
