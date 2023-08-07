package com.sudoware.linkedinscraper.services;

import com.sudoware.linkedinscraper.models.Post;
import com.sudoware.linkedinscraper.models.Profile;
import com.sudoware.linkedinscraper.models.Search;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchService {
    List<Search> getSearches();

    List<Profile> getProfilesBySearch(String id);
    List<Post> getPostsBySearch(String id);
    List<Search> getProfileSearches();
    List<Search> getPostsSearches();
    List<Profile> getProfilesBySearchedAt(LocalDateTime searchedAt);
    List<Post> getPostsBySearchedAt(LocalDateTime searchedAt);

    Search saveSearch(Search search);
    Search updateSearch(Search search);

}
