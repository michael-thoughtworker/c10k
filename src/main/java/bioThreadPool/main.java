package bioThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class main {

    static  HttpClient client = HttpClient.newHttpClient();
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        int port = 10000;
        final ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("server is listing on " + port);

        final ExecutorService executorService = Executors.newFixedThreadPool(50);
        while (true) {
            final Socket socket = serverSocket.accept(); // Blocking until someone connects
            executorService.submit(() -> {
                try {
                    handle(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void handle(final Socket socket) throws IOException, InterruptedException, URISyntaxException {


        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        String s;
        while ((s = in.readLine()) != null) {
            if (s.isEmpty()) {
                break;
            }
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.coindesk.com/v1/bpi/currentprice.json"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        out.write("HTTP/1.0 200 OK\r\n");
        out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.write("Content-Length: 19\r\n");
        out.write("Content-Type: application/json\r\n");
        out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
        out.write("\r\n");
        out.write("{status: \"success\"}");


        out.close();
        in.close();
        socket.close();
    }
}

