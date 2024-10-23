package dev.lpa.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SimpleServerChannel {
    public static void main(String[] args) {
        //Open a ServerSocketChannel
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open()){
            //Bind the ServerSocketChannel to a specific address and port
            serverChannel.socket().bind(new InetSocketAddress(6000));
            //By default, ServerSocketChannel is in BLOCKING mode
            serverChannel.configureBlocking(false);
            System.out.println("Server is listening on port " + serverChannel.socket().getLocalPort());

            List<SocketChannel> clientChannelsList = new ArrayList<>();

            //Enable multiple clients access using SocketChannel NIO
            while(true){
                SocketChannel clientChannel = serverChannel.accept();
                if(clientChannel!=null){
                    //Set SocketChannel on the client's side to NON-BLOCKING
                    clientChannel.configureBlocking(false);
                    clientChannelsList.add(clientChannel);
                    System.out.printf("Client %s is connected%n", clientChannel.socket().getRemoteSocketAddress());
                }

                //Buffer declaration
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                for(int i = 0; i<clientChannelsList.size(); i++) {
                    SocketChannel channel = clientChannelsList.get(i);
                    //Read the data from the client using Non-Blocking I/O
                    int readBytes = channel.read(buffer);

                    if (readBytes > 0) {
                        //Flip the buffer's mode from writing to reading
                        buffer.flip();
                        //Send the data from the server to the client(1): static String message
                        channel.write(ByteBuffer.wrap("ECHO FROM SERVER: ".getBytes()));
                        while (buffer.hasRemaining()) {
                            //Send the data from the server to the client(2): dynamic content of the buffer
                            channel.write(buffer);
                        }
                        buffer.clear();
                    } else if (readBytes == -1) {
                        System.out.printf("Connection %s is disconnected%n", channel.socket().getRemoteSocketAddress());
                        channel.close();
                        clientChannelsList.remove(channel);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("[SERVER EXCEPTION] "+e.getMessage());
        }
    }
}
