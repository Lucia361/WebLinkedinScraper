package com.sudoware.linkedinscraper.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateRenderController {

    @GetMapping("/")
    public String renderHomePage() {
        return "index";
    }
}
