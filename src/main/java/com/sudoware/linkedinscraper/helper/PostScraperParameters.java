package com.sudoware.linkedinscraper.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostScraperParameters {

    private String email;
    private String password;
    private String keywords;
    private Long totalPostsToFetch;

    // additional filters
    private String datePosted;
    private String sortBy;

}