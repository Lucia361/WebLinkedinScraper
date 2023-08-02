package com.sudoware.linkedinscraper.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileScraperParameters {

    private String email;
    private String password;
    private Long totalProfilesToFetch;
    private ProfileScraperFilters filters;

}
