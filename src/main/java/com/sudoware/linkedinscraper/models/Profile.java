package com.sudoware.linkedinscraper.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private boolean isOpenToWork;

}
