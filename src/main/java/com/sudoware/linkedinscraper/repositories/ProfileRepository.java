package com.sudoware.linkedinscraper.repositories;

import com.sudoware.linkedinscraper.models.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, Long> {}
