package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Main {
    public static void main(String[] args) throws IOException {

        initGraph(g);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/direct", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
//
//        System.out.println("1 --> 2 = " + g.isPathExists(1, 2)); // true
//        System.out.println("4 --> 0 = " + g.isPathExists(4, 0)); // false
//        System.out.println("3 --> 1 = " + g.isPathExists(3, 1)); // true
//        System.out.println("3 --> 2 = " + g.isPathExists(3, 2)); // false
        // try bfs
    }

    static void initGraph(Graph g) {
        // pass the path to the file as a parameter
        File file = new File(
                "src\\main\\resources\\routes.txt");

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                Scanner line_scan = new Scanner(sc.nextLine());
                if (line_scan.hasNextInt())
                {
                    int route_numbr = line_scan.nextInt();
                    int start_vertex = 0;
                    if (line_scan.hasNextInt()) {
                        start_vertex = line_scan.nextInt();
                        g.addVertex(start_vertex, route_numbr);
                    }
                    else {
                        throw new RuntimeException("!!!!!!!!!!!!!!");
                    }
                    int end_vertex = 0;
                    while (line_scan.hasNextInt()) {
                        end_vertex = line_scan.nextInt();
                        // create vertex start --> end
                        g.addEdge(start_vertex, end_vertex, route_numbr);
                        start_vertex = end_vertex;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            int from = -1, to = -1;
            String query = t.getRequestURI().getQuery();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                try {
                    if (entry[0].equals("from")) {
                        from = Integer.parseInt(entry[1]);
                    }

                    if (entry[0].equals("to")) {
                        to = Integer.parseInt(entry[1]);
                    }
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
            }

            String resp_str = "Check get params please!!!";
            if(to != -1 && from != -1) {

                resp_str = "{\n" +
                        "\t\"from\": " + from + ",\n" +
                        "\t\"to\": " + to + ",\n" +
                        "\t\"direct\": " + g.isPathExists(from, to) + "\n}";
            }

            t.sendResponseHeaders(200, resp_str.length());
            OutputStream os = t.getResponseBody();
            os.write(resp_str.getBytes());
            os.close();
        }
    }

    static Graph g = new Graph();
}