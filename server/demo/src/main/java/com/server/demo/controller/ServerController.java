package com.server.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.server.demo.service.LogService;


@RestController
public class ServerController {

    private String portNumber;

    @Autowired
    private LogService logService;

    ServerController(@Value("${server.port}") String portNumber) {
        this.portNumber = portNumber;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/deregister"))
                .POST(HttpRequest.BodyPublishers.ofString(portNumber))
                .build(), HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }));

        register(portNumber);
    }
        
    public void register(@RequestBody String server) {
        String serverAddress = portNumber;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/register"))
                .POST(HttpRequest.BodyPublishers.ofString(serverAddress))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                // Get the response body as a string
                String responseBody = response.body();
                System.out.println("Message from load balancer:");
                System.out.println(responseBody);
            } else {
                System.err.println("Request failed with status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/api/logs")
    public String getLogs() {
        return logService.getLogs();
    }

    @PostMapping("/check")
    public boolean check(@RequestBody String str) {
        System.out.println("Received request to check string: " + str);
        logService.addLog("Received string: " + str);
        return str.matches("[A-Za-z0-9]+");
    }

}
