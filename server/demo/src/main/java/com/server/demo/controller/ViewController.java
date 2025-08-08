package com.server.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.server.demo.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class ViewController {

    @Value("${server.port}")
    private String portNumber;

    @Autowired
    private LogService logService;

    @GetMapping("/log")
    public String showLogView(Model model) {
        model.addAttribute("serverPort", portNumber);
        model.addAttribute("logs", logService.getLogs());
        return "index";
    }
}