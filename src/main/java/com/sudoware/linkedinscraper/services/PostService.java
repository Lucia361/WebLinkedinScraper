package com.sudoware.linkedinscraper.services;

import com.sudoware.linkedinscraper.helper.PostScraperParameters;

public interface PostService {
    void startScraper(PostScraperParameters postsParameters);
    String getStatus();
    boolean isScraperIsCurrentlyRunning();

    boolean isScrapedSuccess();
}
