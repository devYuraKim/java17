package dev.lpa.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSimpleServer {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newCachedThreadPool();

        try(ServerSocket serverSocket = new ServerSocket(6000)){
            System.out.println("===========Server is running===========");
            while(true) {
                //wait for a client connection.
                //ServerSocket.accept() will return a Socket instance when a client connects
                Socket socket = serverSocket.accept();
                    System.out.println("Client has connected to the server");
                    socket.setSoTimeout(900_000);
                    executorService.submit(()->handleClientRequest(socket));
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket socket){
        try(socket;
            //way for the SERVER to receive INPUT from the CLIENT
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //way for the SERVER to send OUTPUT to the CLIENT
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            ){
            while (true) {
                //BLOCKING I/O
                String echoString = input.readLine();
                //printed on the server side
                System.out.println("[client] " + echoString);
                if (echoString.equals("exit")) break;
                //printed on the client side
                //output.println("[server] String echoed from server to client(response): " + echoString);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter a string to be sent to client(response): ");
                output.println("[server] " + scanner.nextLine());
                System.out.println("Please wait for the client's request");
            }
        }catch(Exception e){
            System.out.println("Client socket shut down here");
        }

    }


}
