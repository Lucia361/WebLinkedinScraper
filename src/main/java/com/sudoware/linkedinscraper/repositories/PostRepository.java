package com.sudoware.linkedinscraper.repositories;

import com.sudoware.linkedinscraper.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Long> {
}
