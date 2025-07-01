package com.prm392.manga.app.data.repository;

import com.prm392.manga.app.data.api.ApiService;
import com.prm392.manga.app.data.model.CreateHistoryRequest;
import com.prm392.manga.app.data.model.History;
import com.prm392.manga.app.utils.NetworkManager;
import com.prm392.manga.app.utils.PreferenceManager;

import java.util.List;

import io.reactivex.Observable;

public class HistoryRepository {

    private ApiService apiService;
    private PreferenceManager preferenceManager;

    public HistoryRepository() {
        this.apiService = NetworkManager.getInstance().getApiService();
        this.preferenceManager = PreferenceManager.getInstance();
    }

    public Observable<List<History>> getHistory() {
        String authToken = NetworkManager.createAuthHeader(preferenceManager.getAuthToken());
        return apiService.getHistory(authToken);
    }

    public Observable<Void> createHistory(int comicId, int chapterId) {
        String authToken = NetworkManager.createAuthHeader(preferenceManager.getAuthToken());
        CreateHistoryRequest request = new CreateHistoryRequest(comicId, chapterId);
        return apiService.createHistory(authToken, request);
    }
}

