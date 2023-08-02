package com.sudoware.linkedinscraper.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileScraperParameters {

    private String email;
    private String password;
    private Long totalProfilesToFetch;
    private boolean headlessMode;

    // additional filters.
    private ProfileScraperFilters filters;

}
