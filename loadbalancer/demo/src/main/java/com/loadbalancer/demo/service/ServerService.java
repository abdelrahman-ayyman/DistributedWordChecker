package com.loadbalancer.demo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerService {
    private List<String> servers = new ArrayList<>();
    private int index = 0;

    public void registerServer(String server) {
        servers.add(server);
    }

    public String getNextServer() {
        if (servers.isEmpty()) {
            throw new IllegalStateException("No servers available");
        }
        String server = servers.get(index);
        index = (index + 1) % servers.size();
        return server;
    }

    public void removeServer(String server) {
        servers.remove(server);
    }
}