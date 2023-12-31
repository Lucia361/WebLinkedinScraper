package com.sudoware.linkedinscraper.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostScraperParameters {

    private String email;
    private String password;
    private String keywords;
    private String title; // associated with @com.sudoware.linkedinscraper.models.Search.title
    private Long totalPostsToFetch;
    private boolean headlessMode;

    // additional filters
    private String datePosted;
    private String sortBy;

}