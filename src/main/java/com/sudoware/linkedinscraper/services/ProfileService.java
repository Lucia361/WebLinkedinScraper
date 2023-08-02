package com.sudoware.linkedinscraper.services;

import com.sudoware.linkedinscraper.helper.ProfileScraperParameters;
import com.sudoware.linkedinscraper.helper.WebDriverHelper;
import com.sudoware.linkedinscraper.models.Profile;

import java.util.List;

public interface ProfileService {

    List<Profile> getProfiles();
    void startScraper(ProfileScraperParameters profileParameters);

}
