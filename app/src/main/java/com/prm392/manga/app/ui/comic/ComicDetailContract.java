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

        // --- Favorite Related ---
        void setFavoriteIcon(boolean isFavorite);
        void setIsFavorite(boolean isFavorite);
        void updateFavoriteStatus(boolean added);
        void showMessage(String message);
    }

    interface Presenter {
        void loadComicDetail(int comicId);
        void onDestroy();
        void checkIsFavorite(int comicId);
        void toggleFavorite(Comic comic, boolean isCurrentlyFavorite);
        void addToFavorites(int comicId);
        void removeFromFavorites(int comicId);
    }
}