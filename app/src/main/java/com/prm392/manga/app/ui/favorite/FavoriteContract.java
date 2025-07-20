package com.prm392.manga.app.ui.favorite;

import com.prm392.manga.app.data.model.Favorite;

import java.util.List;

public interface FavoriteContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showFavorites(List<Favorite> favorites);
        void showError(String message);
    }

    interface Presenter {
        void loadFavorites();
        void onDestroy();
    }
}
