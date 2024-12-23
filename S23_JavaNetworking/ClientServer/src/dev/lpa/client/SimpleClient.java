package dev.lpa.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        //a new Socket to connect to "localhost" server is created on port 5000
        try (Socket socket = new Socket("localhost", 6000)) {
            //read responses from the server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //send requests to the server
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String requestString;
            String responseString;

            do {
                System.out.println("Enter a string to be sent to server(request): ");
                requestString = scanner.nextLine();
                output.println(requestString);
                System.out.println("Please wait for the server's response");
                if (!requestString.equals("exit")) {
                    responseString = input.readLine();
                    System.out.println(responseString);
                }
            } while (!requestString.equals("exit"));

        } catch (IOException e) {
            System.out.println("client error: " + e.getMessage());
        } finally {
            System.out.println("Client Disconnected");
        }
    }
}
