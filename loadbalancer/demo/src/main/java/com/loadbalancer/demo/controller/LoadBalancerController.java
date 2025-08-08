package com.loadbalancer.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loadbalancer.demo.service.ServerService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class LoadBalancerController {
    private final ServerService serverService;

    public LoadBalancerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping("/register")
    public String register(@RequestBody String server) {
        serverService.registerServer(server);
        return "Server registered successfully";
    }

    @PostMapping("/deregister")
    public String deregister(@RequestBody String server) {
        serverService.removeServer(server);
        return "Server deregistered successfully";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestBody String stringToCheck) {
        
        String server = null;
        try {
            server = serverService.getNextServer();
        }   catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service unavailable");
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + server + "/check"))
                .POST(HttpRequest.BodyPublishers.ofString(stringToCheck))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseEntity.ok(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service unavailable");
        }
        
    }
}
