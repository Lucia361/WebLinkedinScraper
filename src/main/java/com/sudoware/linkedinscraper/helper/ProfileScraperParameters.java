package com.sudoware.linkedinscraper.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileScraperParameters {

    private String email;
    private String password;
    private String keywords;
    private String title; // associated with @com.sudoware.linkedinscraper.models.Search.title
    private Long totalProfilesToFetch;
    private boolean headlessMode = false;

    // additional filters.
    private ProfileScraperFilters filters;

}
