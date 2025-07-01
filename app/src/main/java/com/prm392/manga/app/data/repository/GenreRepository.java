package com.prm392.manga.app.data.repository;

import com.prm392.manga.app.data.api.ApiService;
import com.prm392.manga.app.data.model.Genre;
import com.prm392.manga.app.utils.NetworkManager;

import java.util.List;

import io.reactivex.Observable;

public class GenreRepository {

    private ApiService apiService;

    public GenreRepository() {
        this.apiService = NetworkManager.getInstance().getApiService();
    }

    public Observable<List<Genre>> getAllGenres() {
        return apiService.getAllGenres();
    }
}
