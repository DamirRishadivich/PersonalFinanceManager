package org.example;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws ParseException {
        final String host = "127.0.0.1";
        final int port = 8989;
        JSONParser parser = new JSONParser();
        try (Socket socket = new Socket(host, port)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request = "{\"title\": \"булка\", \"date\": \"2023.12.01\", \"sum\": 2000}";
            writer.println(request);
            JSONObject object = (JSONObject) parser.parse(reader.readLine());
            System.out.println(object);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
