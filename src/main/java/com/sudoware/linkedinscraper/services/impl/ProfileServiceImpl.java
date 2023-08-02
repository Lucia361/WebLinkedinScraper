package com.sudoware.linkedinscraper.services.impl;

import com.sudoware.linkedinscraper.helper.ProfileScraperFilters;
import com.sudoware.linkedinscraper.helper.ProfileScraperParameters;
import com.sudoware.linkedinscraper.helper.WebDriverHelper;
import com.sudoware.linkedinscraper.models.Profile;
import com.sudoware.linkedinscraper.repositories.ProfileRepository;
import com.sudoware.linkedinscraper.services.ProfileService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired private ProfileRepository profileRepository;

    private WebDriverHelper driverHelper;

    private Set<Profile> scrapProfiles(Set<String> profileLinks) throws InterruptedException {
        Set<Profile> profiles = profileLinks.stream().map(profileLink -> scrapeProfile(driverHelper, profileLink)).collect(Collectors.toSet());

        // Add timestamp to each profile after fetching them
        profiles.forEach(profile -> profile.setFetchedAt(LocalDateTime.now()));

        return profiles;
    }

    private Profile scrapeProfile(WebDriverHelper driverHelper, String profileLink) {
        Profile profile = new Profile(driverHelper, profileLink);
        profile.fetchInformation();
        return profile;
    }


    private String getFilters(ProfileScraperFilters profileFilters) {

        StringJoiner locationsUrns = new StringJoiner("%2C", "&location=", "");
        StringJoiner industriesUrns = new StringJoiner("%2C", "&industry=", "");
        StringJoiner connectionFilter = new StringJoiner("%2C", "&network=%5B", "%5D");

        if (profileFilters.isFirstConnectionChecked()) connectionFilter.add("%22F%22");
        if (profileFilters.isSecondConnectionChecked()) connectionFilter.add("%22S%22");
        if (profileFilters.isThirdConnectionChecked()) connectionFilter.add("%22O%22");

        for (String location : profileFilters.getSelectedLocations()) {
            locationsUrns.add(String.format("%22%s%22", location));
        }

        for (String industry : profileFilters.getSelectedIndustries()) {
            industriesUrns.add(String.format("%22%s%22", industry));
        }

        return connectionFilter.toString() + locationsUrns.toString() + industriesUrns.toString();
    }

    private Set<String> retrieveProfileLinks(ProfileScraperParameters profileParameters) throws InterruptedException {

        Set<String> profileLinks = new HashSet<>();

        boolean isNextPageAvailable = true;
        int pageToGoNext = 1;
        int profilesRetrieved = 0;
        long totalProfilesToRetrieve = profileParameters.getTotalProfilesToFetch();
        String filters = getFilters(profileParameters.getFilters());


        while ((isNextPageAvailable && profilesRetrieved < totalProfilesToRetrieve) || (isNextPageAvailable && totalProfilesToRetrieve == -1)) {
            // going to search results
            driverHelper.getDriver().get(String.format("https://www.linkedin.com/search/results/people/?page=%d%s", pageToGoNext, filters));
            Thread.sleep(1000);
            pageToGoNext++;

            List<WebElement> profilesElements = driverHelper.getDriver().findElements(By.xpath("//a[contains(@class, 'app-aware-link') and contains(@href, '/in/')]"));


            for (WebElement profileElement : profilesElements) {
                String profileLink = profileElement.getAttribute("href");
                int endIndex = profileLink.indexOf("?miniProfile");

                if (endIndex != -1 && profileLinks.add(profileLink.substring(0, endIndex))) {
                    profilesRetrieved++;
                    if (profilesRetrieved >= totalProfilesToRetrieve && totalProfilesToRetrieve != -1) {
                        break;
                    }
                }
            }

            WebElement noResultPageElement = driverHelper.getElementIfExist(By.xpath("//div[@class='search-reusable-search-no-results artdeco-card mb2']"));
            if(noResultPageElement != null) isNextPageAvailable = false;
        }

        return profileLinks;
    }

    private boolean loginToLinkedIn(String emailAddress, String password) throws InterruptedException {
        // going to LinkedIn login page.
        driverHelper.getDriver().get("https://www.linkedin.com/login");
        Thread.sleep(1000);

        // entering details and then clicking on login button to logged in.
        driverHelper.getDriver().findElement(By.xpath("/html/body/div/main/div[2]/div[1]/form/div[1]/input")).sendKeys(emailAddress);
        driverHelper.getDriver().findElement(By.xpath("/html/body/div/main/div[2]/div[1]/form/div[2]/input")).sendKeys(password);
        driverHelper.getDriver().findElement(By.xpath("/html/body/div/main/div[2]/div[1]/form/div[3]/button")).click();
        Thread.sleep(2500);

        // if verification captcha required make it sleep for 15 seconds to verify manually.
        if(driverHelper.getDriver().getCurrentUrl().contains("checkpoint")) Thread.sleep(15000);

        WebElement wrongCredentialsElement = driverHelper.getElementIfExist(By.id("error-for-password"));
        return wrongCredentialsElement == null;
    }

    @Override
    public void startScraper(WebDriverHelper driverHelper, ProfileScraperParameters profileParameters) {
        try {

            this.driverHelper = driverHelper;

            boolean isLoggedIn = loginToLinkedIn(profileParameters.getEmail(), profileParameters.getPassword());
            if(!isLoggedIn) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong LinkedIn login credentials.");
            }

        } catch (InterruptedException e) {
            // TODO: handle the exception better.
        }
    }

    // below all methods communicate to database
    @Override
    public List<Profile> getProfiles() {
        return profileRepository.findAll();
    }
    private void saveToDatabase(Set<Profile> profiles) {
        try {
            profileRepository.saveAll(profiles);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fetched all profiles but unable to save it to database.");
        }
    }
}
