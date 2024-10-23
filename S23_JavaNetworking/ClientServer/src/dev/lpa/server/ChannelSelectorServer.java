package dev.lpa.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChannelSelectorServer {

    public static void main(String[] args) {
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open();){
            serverChannel.bind(new InetSocketAddress(6000));
            serverChannel.configureBlocking(false);

            /* 1.Registration: A channel is registed with a SELECTOR. This registration creates a SELECTIONKEY for that channel.
            *  2.Polling for events: The server uses the SELECTOR to block and wait for events on the registered channels
            *  3.Process events: When an event occurs, SELECTIONKEY is retrieved from the SELECTOR, check its state(acceptable, readable, writable)
            */

            //(1)Create SELECTOR
            //SELECTOR object allows a single thread to monitor multiple channels for events
            Selector selector = Selector.open();
            //(2)Register serverChannel to selector for monitoring 'accept' event
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                //(3)Select ready channels
                selector.select();
                //(4)Retrieve SELECTIONKEYs of the channels
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                //Create Iterator for selectedKeys set
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                //Iterate through ready channels
                while(iterator.hasNext()){
                    //Retrieve the next SelectionKey from the iterator, so to say SelectionKey set
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    //(5)Process each key, check its state and take action
                    if(key.isAcceptable()){
                        //If the server channel is ready,
                        //the server creates multiple gateways (SocketChannels) for client connections,
                        //allowing simultaneous handling of multiple clients.
                        SocketChannel clientChannel = serverChannel.accept();
                        System.out.println("Client connected: " + clientChannel.socket().getRemoteSocketAddress());
                    }
                }
            }
        }catch(IOException e){
            System.out.println("SERVER ERROR :" + e.getMessage());
        }
    }
}
