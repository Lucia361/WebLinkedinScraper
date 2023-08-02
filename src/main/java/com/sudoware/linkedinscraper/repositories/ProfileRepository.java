package com.sudoware.linkedinscraper.repositories;

import com.sudoware.linkedinscraper.models.Profile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, ObjectId> {}
