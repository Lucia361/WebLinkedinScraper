package com.sudoware.linkedinscraper.controllers;

import com.sudoware.linkedinscraper.helper.PostScraperParameters;
import com.sudoware.linkedinscraper.helper.ProfileScraperParameters;
import com.sudoware.linkedinscraper.services.PostService;
import com.sudoware.linkedinscraper.services.ProfileService;
import com.sudoware.linkedinscraper.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AppController {

    @Autowired private PostService postService;
    @Autowired private ProfileService profileService;
    @Autowired private ScraperService scraperService;

    @PostMapping("/profile-scraper")
    public ResponseEntity<?> startProfileScraper(@RequestBody ProfileScraperParameters profileParameters) {
        return null;
    }

    @PostMapping("/post-scraper")
    public ResponseEntity<?> startPostScraper(@RequestBody PostScraperParameters postParameters) {
        return null;
    }

    @GetMapping("/status/{scraper-type}")
    public ResponseEntity<?> getStatus(@PathVariable String scraperType) {
        return null;
    }

}
