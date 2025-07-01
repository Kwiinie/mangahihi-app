package com.prm392.manga.app.ui.comic;

import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.data.model.Comic;

public interface ComicDetailContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showComicDetail(Comic comic);

        void navigateToChapter(Chapter chapter);
    }

    interface Presenter {
        void loadComicDetail(int comicId);
        void onDestroy();
    }
}