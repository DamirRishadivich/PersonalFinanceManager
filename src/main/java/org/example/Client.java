package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 8989;

        try (Socket socket = new Socket(host, port)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = "{\"title\": \"шапка\", \"date\": \"2022.02.08\", \"sum\": 200}";
            writer.println(request);
            System.out.println(reader.readLine());

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
