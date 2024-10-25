package dev.lpa.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;

public class WebSocketClient {

    public static void main(String[] args) throws URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket webSocket = client.newWebSocketBuilder().
                buildAsync(new URI("ws://localhost:9090"),
                        new WebSocket.Listener(){}).join();
    }
}