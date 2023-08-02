package com.sudoware.linkedinscraper.controllers;

import com.sudoware.linkedinscraper.services.PostService;
import com.sudoware.linkedinscraper.services.ProfileService;
import com.sudoware.linkedinscraper.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AppController {

    @Autowired private PostService postService;
    @Autowired private ProfileService profileService;
    @Autowired private ScraperService scraperService;

    @PostMapping("/scrape")
    public ResponseEntity<?> startScraper() {
        return null;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() { return null; }

}
