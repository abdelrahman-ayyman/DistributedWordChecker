package com.server.demo.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class LogService {
    private List<String> logs = new ArrayList<>();

    public void addLog(String log) {
        logs.add(log);
    }

    public String getLogs() {
        if (logs.isEmpty()) {
            return "No logs available";
        }
        Gson gson = new Gson();
        String jsonArray = gson.toJson(logs);
        return jsonArray;
    }
}
