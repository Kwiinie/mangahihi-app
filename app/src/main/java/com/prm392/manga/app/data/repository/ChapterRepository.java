package com.prm392.manga.app.data.repository;

import com.prm392.manga.app.data.api.ApiService;
import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.utils.NetworkManager;

import io.reactivex.Observable;

public class ChapterRepository {

    private ApiService apiService;

    public ChapterRepository() {
        this.apiService = NetworkManager.getInstance().getApiService();
    }

    public Observable<Chapter> getChapterById(int chapterId) {
        return apiService.getChapterById(chapterId);
    }
}