package com.sudoware.linkedinscraper.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileScraperFilters {

    private boolean isFirstConnectionChecked;
    private boolean isSecondConnectionChecked;
    private boolean isThirdConnectionChecked;

    private List<String> selectedLocations;
    private List<String> selectedIndustries;
}
