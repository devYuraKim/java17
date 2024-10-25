package dev.lpa.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static java.net.HttpURLConnection.HTTP_OK;

public class SimpleHttpServer {

    private static long visitorCounter = 0;
    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", exchange->{
                String requestMethod = exchange.getRequestMethod();
                System.out.println("Request Method: " + requestMethod);

                String data = new String(exchange.getRequestBody().readAllBytes());
                System.out.println("Request Body: " + data);
                if(requestMethod.equalsIgnoreCase("post")){
                    visitorCounter++;
                }

                String response = """
                        <html>
                            <body>
                                <h1>Hello World from my Http server</h1>
                                <p>Number of visitors %d </p>
                                <form method="post">
                                    <input type="submit" value="submit">
                                </form>
                            </body>
                        </html>
                        """.formatted(visitorCounter);
                var bytes = response.getBytes();
                exchange.sendResponseHeaders(HTTP_OK, bytes.length);
                exchange.getResponseBody().write(bytes);
                exchange.close();
            });

            server.start();
            System.out.println("Server listening on port 8080");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
