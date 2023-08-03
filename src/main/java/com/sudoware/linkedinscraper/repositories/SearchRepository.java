package com.sudoware.linkedinscraper.repositories;

import com.sudoware.linkedinscraper.models.Post;
import com.sudoware.linkedinscraper.models.Profile;
import com.sudoware.linkedinscraper.models.Search;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchRepository extends MongoRepository<Search, String> {
    Search findBySearchedAt(LocalDateTime searchedAt);
}
