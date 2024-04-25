package com.runtik;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create("http://localhost:8080/temperature"))
                                         .GET()
                                         .build();
        Gson gson = new Gson();

        while (true) {
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                String body = response.body();
                System.out.println(body);

                HttpRequest sendRequest = HttpRequest.newBuilder()
                                                     .uri(URI.create("http://localhost:8081/adapter"))
                                                     .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(new Data(body, Type.TEMPERATURE))))
                                                     .header("Content-Type", "application/json")
                                                     .build();

                HttpResponse<String> sendResponse = httpClient.send(sendRequest, HttpResponse.BodyHandlers.ofString());
                // Process the sendResponse if needed

                Thread.sleep(10000); // Pause execution for 10 seconds
            } catch (IOException | InterruptedException e) {
                e.printStackTrace(); // Handle the exception gracefully
                break; // Exit the loop on exception
            }
        }
    }
}
