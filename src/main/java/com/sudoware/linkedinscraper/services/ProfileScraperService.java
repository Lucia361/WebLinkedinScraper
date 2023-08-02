package com.sudoware.linkedinscraper.services;

import com.sudoware.linkedinscraper.helper.ProfileScraperParameters;
import com.sudoware.linkedinscraper.helper.WebDriverHelper;
import com.sudoware.linkedinscraper.models.Profile;

import java.util.List;

public interface ProfileScraperService {

    List<Profile> getProfiles();
    void startScraper(WebDriverHelper driverHelper, ProfileScraperParameters profileParameters);

}
