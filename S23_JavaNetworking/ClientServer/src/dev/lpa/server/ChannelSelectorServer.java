package dev.lpa.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChannelSelectorServer {

    public static void main(String[] args) {
        /* [ServerSocketChannel]
        Used to listen for incoming client connection requests
        Only used for accepting new client connections
        Not responsible for handling data communication directly with clients
        */
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open();){
            serverChannel.bind(new InetSocketAddress(6000));

            /* [non-blocking ServerSocketChannel]
            When a ServerSocketChannel is non-blocking, the channel's operations (accpet, read and write) will return immediately instead of waiting for an event to complete.
            If there is no incoming connection when accept() is called, it immediately returns null rather than blocking.
            Therefor, being able to manage multiple channels without getting stuck waiting for events.*/
            serverChannel.configureBlocking(false);

            /* 1.Registration: A channel is registed with a SELECTOR. This registration creates a SELECTIONKEY for that channel.
            *  2.Polling for events: The server uses the SELECTOR to block and wait for events on the registered channels
            *  3.Process events: When an event occurs, SELECTIONKEY is retrieved from the SELECTOR, check its state(acceptable, readable, writable)
            */

            //(1)Create SELECTOR
            //SELECTOR object allows a single thread to monitor multiple channels for events
            //In my code, there will be only one ServerSocketChannel registered
            Selector selector = Selector.open();
            //(2)Register ServerSocketChannel to Selector
            //SELECTOR will monitor the ServerSocketChannel for any incoming OP_ACCEPT(eg new client connection request)
            //When registered, a selectionKey is created to represent the registration between the channel and the selector
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                //(3)Put SELECTOR into action
                /* When a client attempts to connect to the server, the Selector checks the ServerSocketChannel.
                If the ServerSocketChannel is ready to accept a new client connection,
                the existing SelectionKey (the one created during registration) is added to the selectedKeys set. */
                selector.select();
                //(4)Retrieve SelectionKeys of the channel
                //The SelectionKey is added when the ServerSocketChannel is ready to accept a connection, not when a new connection is made
                //During the first connection attempt, there will be only one SelectionKey in the selectedKeys set.
                /*Usage of set ensure that each SelectionKey(if there are multiple channels connected) is unique within the collection.*/
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                //Create Iterator for selectedKeys set
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                //Iterate through the SelectionKey set and process the key
                /*Because there's an entry in the selectedKeys set, the iterator will execute.
                The key will be saved to a separate variable and the removed from the set to prevent reprocessing.
                If the key's state is acceptable, then a new SocketChannel will be created for the incoming client connection. */
                while(iterator.hasNext()){
                    //Save the key to a variable
                    SelectionKey key = iterator.next();
                    //Remove the key from the set to avoid reprocessing
                    //Enable new connection processing using a single SelectionKey for the serverChannel
                    iterator.remove();

                    /* [actual client channel registration]
                    * */
                    //(5)Process each key, check its state and take action
                    if(key.isAcceptable()) {
                        /*[Actual creation of client SocketChannel]*/
                        //If the server channel is ready to accept a connection from a client,
                        //call serverChannel.accept() to accept the incoming connection and establish a client channel
                        SocketChannel clientChannel = serverChannel.accept();
                        System.out.println("Client connected: " + clientChannel.socket().getRemoteSocketAddress());
                        //If the channels are not ready for an operation, it will return immediately and not wait
                        clientChannel.configureBlocking(false);
                        //Selector will notify the server code when the channel is ready to be read
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    }else if(key.isReadable()){
                        echoData(key);
                    }
                }
            }
        }catch(IOException e){
            System.out.println("SERVER ERROR :" + e.getMessage());
        }
    }

    private static void echoData(SelectionKey key) throws IOException{
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int bytesRead = clientChannel.read(buffer);
        if(bytesRead>0){
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String message = "Echo: " + new String(data);
            clientChannel.write(ByteBuffer.wrap(message.getBytes()));
        }else if (bytesRead == -1){
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            key.cancel();
            clientChannel.close();
        }
    }
}
