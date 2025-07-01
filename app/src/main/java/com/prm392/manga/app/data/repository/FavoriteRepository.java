package com.prm392.manga.app.data.repository;

import com.prm392.manga.app.data.api.ApiService;
import com.prm392.manga.app.data.model.AddFavoriteRequest;
import com.prm392.manga.app.data.model.Favorite;
import com.prm392.manga.app.utils.NetworkManager;
import com.prm392.manga.app.utils.PreferenceManager;

import java.util.List;

import io.reactivex.Observable;

public class FavoriteRepository {

    private ApiService apiService;
    private PreferenceManager preferenceManager;

    public FavoriteRepository() {
        this.apiService = NetworkManager.getInstance().getApiService();
        this.preferenceManager = PreferenceManager.getInstance();
    }

    public Observable<List<Favorite>> getFavorites() {
        String authToken = NetworkManager.createAuthHeader(preferenceManager.getAuthToken());
        return apiService.getFavorites(authToken);
    }

    public Observable<Void> addToFavorites(int comicId) {
        String authToken = NetworkManager.createAuthHeader(preferenceManager.getAuthToken());
        AddFavoriteRequest request = new AddFavoriteRequest(comicId);
        return apiService.addToFavorites(authToken, request);
    }

    public Observable<Void> removeFromFavorites(int comicId) {
        String authToken = NetworkManager.createAuthHeader(preferenceManager.getAuthToken());
        return apiService.removeFromFavorites(authToken, comicId);
    }
}
