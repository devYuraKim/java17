package dev.lpa.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleServerChannel {
    public static void main(String[] args) {
        //Open a ServerSocketChannel
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open()){
            //Bind the ServerSocketChannel to a specific address and port
            serverChannel.socket().bind(new InetSocketAddress(6000));
            System.out.println("Server is listening on port " + serverChannel.socket().getLocalPort());

            //Enable multiple clients access using SocketChannel NIO
            while(true){
                SocketChannel clientChannel = serverChannel.accept();
                System.out.printf("Client %s is connected%n", clientChannel.socket().getRemoteSocketAddress());

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketChannel channel = clientChannel;
                //Read the data from the client using Non-Blocking I/O
                int readBytes = channel.read(buffer);

                if(readBytes>0) {
                    //Flip the buffer's mode from writing to reading
                    buffer.flip();
                    //Send the data from the server to the client(1): static String message
                    channel.write(ByteBuffer.wrap("ECHO FROM SERVER: ".getBytes()));
                    while (buffer.hasRemaining()) {
                        //Send the data from the server to the client(2): dynamic content of the buffer
                        channel.write(buffer);
                    }
                    buffer.clear();
                }else if(readBytes == -1){
                    System.out.printf("Connection %s is connected%n", channel.socket().getRemoteSocketAddress());
                    channel.close();
                }
            }
        } catch (IOException e){
            System.out.println("[SERVER EXCEPTION] "+e.getMessage());
        }
    }
}
