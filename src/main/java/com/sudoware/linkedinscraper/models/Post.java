package com.sudoware.linkedinscraper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
    @Id
    private Long id;
    private String by;
    private String content;
    private String matchedKeywords;
    private String link;
}
