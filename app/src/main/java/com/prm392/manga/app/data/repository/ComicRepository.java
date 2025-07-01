package com.prm392.manga.app.data.repository;

import com.prm392.manga.app.data.api.ApiService;
import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.model.ODataResponse;
import com.prm392.manga.app.utils.NetworkManager;

import java.util.List;

import io.reactivex.Observable;

public class ComicRepository {

    private ApiService apiService;

    public ComicRepository() {
        this.apiService = NetworkManager.getInstance().getApiService();
    }

    public Observable<List<Comic>> getAllComics() {
        return apiService.getAllComics()
                .map(ODataResponse::getValue);
    }

    public Observable<List<Comic>> getFeaturedComics() {
        return apiService.getFeaturedComics(
                "View desc",
                6,
                "IsPublic eq true"
        ).map(ODataResponse::getValue);
    }

    public Observable<List<Comic>> getLatestComics() {
        return apiService.getLatestComics(
                "UpdatedAt desc",
                6,
                "IsPublic eq true"
        ).map(ODataResponse::getValue);
    }

    public Observable<List<Comic>> getComicsByType(String type, int page, int pageSize) {
        int skip = (page - 1) * pageSize;
        String typeFilter = String.format("Type eq '%s' and IsPublic eq true", type);

        return apiService.getComicsByType(
                typeFilter,
                "UpdatedAt desc",
                pageSize,
                skip
        ).map(ODataResponse::getValue);
    }

    public Observable<List<Comic>> getComicsByGenre(String genreName, int page, int pageSize) {
        int skip = (page - 1) * pageSize;
        String genreFilter = String.format("GenreNames/any(g: g eq '%s') and IsPublic eq true", genreName);

        return apiService.getComicsByGenre(
                genreFilter,
                "UpdatedAt desc",
                pageSize,
                skip
        ).map(ODataResponse::getValue);
    }

    public Observable<List<Comic>> searchComics(String query, int page, int pageSize) {
        int skip = (page - 1) * pageSize;
        String searchFilter = String.format("contains(tolower(Name), '%s') and IsPublic eq true",
                query.toLowerCase());

        return apiService.searchComics(
                searchFilter,
                "UpdatedAt desc",
                pageSize,
                skip
        ).map(ODataResponse::getValue);
    }

    public Observable<Comic> getComicById(int comicId) {
        return apiService.getComicById(comicId);
    }

    public Observable<List<Comic>> getTrendingComics() {
        return apiService.getFeaturedComics(
                "View desc",
                10,
                "IsPublic eq true"
        ).map(ODataResponse::getValue);
    }

    public Observable<List<Comic>> getComicsByStatus(String status, int page, int pageSize) {
        int skip = (page - 1) * pageSize;
        String statusFilter = String.format("ComicStatus eq '%s' and IsPublic eq true", status);

        return apiService.getComicsByType(
                statusFilter,
                "UpdatedAt desc",
                pageSize,
                skip
        ).map(ODataResponse::getValue);
    }
}

