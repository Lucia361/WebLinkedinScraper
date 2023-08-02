package com.sudoware.linkedinscraper.models;

import com.sudoware.linkedinscraper.helper.WebDriverHelper;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "profiles")
public class Profile {

    @Id
    private Long id;
    private String name;
    private String about;
    private String experience;
    private String education;
    private String email;
    private String link;
    private Boolean isOpenToWork;

    @Getter
    @Setter
    private LocalDateTime fetchedAt;

    // business logic for fetching information and saving it to database.
    private WebDriverHelper driverHelper;

    public Profile (WebDriverHelper driverHelper, String link) {
        this.link = link;
        this.driverHelper = driverHelper;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO: better handle interrupted exception
        }
    }

    /**
     * Fetches all profile information by calling individual methods.
     */
    public void fetchInformation() {
        getName();
        getAbout();
        getExperience();
        getEducation();
        isOpenToWork();

        // always fetch email in the last.
        getEmail();
    }

    /**
     * Gets the profile name.
     *
     * @return The profile name or "Unable to get profile name" if not found.
     */
    public String getName() {
        if(name != null) return name;

        WebElement profileNameElement = driverHelper.getElementIfExist(By.xpath("//h1[@class='text-heading-xlarge inline t-24 v-align-middle break-words']"));
        if (profileNameElement == null) return "Unable to get profile name";

        name = profileNameElement.getText();
        return name;
    }

    /**
     * Gets the profile description.
     *
     * @return The profile description or "Unable to get profile description" if not found.
     */
    public String getAbout() {
        if(about != null) return about;

        WebElement descriptionElement = driverHelper.getElementIfExist(By.xpath("//div[@class='text-body-medium break-words']"));
        if(descriptionElement == null) return "Unable to get profile description";

        about = removeDuplicateLines(descriptionElement.getText());
        return about;
    }

    /**
     * Gets the profile experience.
     *
     * @return The profile experience or "Unable to get experience" if not found.
     */
    public String getExperience() {
        if(this.experience != null) return experience;

        WebElement experienceElement = driverHelper.getElementIfExist(By.id("experience"));
        if(experienceElement == null) return "Unable to get experience";

        experience = removeDuplicateLines(experienceElement.findElement(By.xpath("./parent::*")).getText());
        return experience;
    }

    /**
     * Gets the profile education.
     *
     * @return The profile education or "Unable to get education" if not found.
     */
    public String getEducation() {
        if(this.education != null) return education;

        WebElement educationElement = driverHelper.getElementIfExist(By.id("education"));
        if(educationElement == null) return "Unable to get education";

        education = removeDuplicateLines(educationElement.findElement(By.xpath("./parent::*")).getText());
        return education;
    }

    /**
     * Gets the profile email
     * @return The profile email or "Unable to get email" if not found.
     */
    public String getEmail() {
        if(this.email != null) return this.email;
        driverHelper.getDriver().get(link + "overlay/contact-info/");
        try {
            // TODO: remove fixed sleeping time
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO: better handle interruptedException
        }
        WebElement emailElement = driverHelper.getElementIfExist(By.xpath("//section[@class='pv-contact-info__contact-type ci-email']/div/a"));
        return emailElement != null ? this.email = emailElement.getText() : "Unable to get email";
    }

    /**
     * Checks if the profile is open to work.
     *
     * @return True if open to work, false otherwise.
     */
    public Boolean isOpenToWork() {
        if(isOpenToWork != null) return isOpenToWork;
        WebElement isOpenToWork = driverHelper.getElementIfExist(By.xpath("//main[@class='scaffold-layout__main']/section/section/div"));
        return this.isOpenToWork = isOpenToWork != null;
    }

    /**
     * Gets the profile link.
     *
     * @return The profile link.
     */
    public String getLink() {
        return driverHelper.getDriver().getCurrentUrl();
    }

    /**
     * Removes duplicate line from the given string.
     *
     * @param str The input string.
     * @return The string with duplicate words removed.
     */
    private static String removeDuplicateLines(String str) {
        if (str == null) return "";

        StringBuilder newStr = new StringBuilder();
        String[] lines = str.split("\n");
        Set<String> uniqueLines = new HashSet<>();

        for (String line : lines) {
            if (uniqueLines.add(line.trim())) {
                newStr.append(line).append("\n");
            }
        }

        return newStr.toString().trim();
    }

}
