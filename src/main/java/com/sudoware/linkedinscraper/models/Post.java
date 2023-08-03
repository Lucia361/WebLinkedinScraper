package com.sudoware.linkedinscraper.models;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
@NoArgsConstructor
public class Post {
    @Id
    private String id;
    private String by;
    private String content;
    private String matchedKeywords;
    private String link;
}
