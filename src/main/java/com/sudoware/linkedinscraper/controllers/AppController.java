package com.sudoware.linkedinscraper.controllers;

import com.sudoware.linkedinscraper.helper.PostScraperParameters;
import com.sudoware.linkedinscraper.helper.ProfileScraperParameters;
import com.sudoware.linkedinscraper.services.PostService;
import com.sudoware.linkedinscraper.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AppController {

    @Autowired private PostService postService;
    @Autowired private ProfileService profileService;

    private final String PROFILE_SCRAPER = "profile-scraper";
    private final String POST_SCRAPER = "post-scraper";

    @PostMapping("/profile-scraper")
    public ResponseEntity<?> profileScraper(@RequestBody ProfileScraperParameters profileParameters) {
        profileService.startScraper(profileParameters);
        return ResponseEntity.ok("Successfully fetched and save profiles to database.");
    }

    @PostMapping("/post-scraper")
    public ResponseEntity<?> postScraper(@RequestBody PostScraperParameters postParameters) {
        return null;
    }

    @GetMapping("/status/{scraper-type}")
    public ResponseEntity<?> getStatus(@PathVariable String scraperType) {
        return null;
    }

}
