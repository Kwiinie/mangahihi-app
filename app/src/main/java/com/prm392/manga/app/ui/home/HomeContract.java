package com.prm392.manga.app.ui.home;

import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.model.Genre;

import java.util.List;

public interface HomeContract {

    interface View {
        void showGenres(List<Genre> genres);
        void showFeaturedComics(List<Comic> comics);
        void showLatestComics(List<Comic> comics);
        void showLoading();
        void hideLoading();
        void showError(String message);
        void navigateToComicDetail(Comic comic);
        void navigateToComicList(String type);
        void navigateToGenreList(Genre genre);
    }

    interface Presenter {
        void loadHomeData();
        void onComicTypeSelected(String comicType);
        void onGenreSelected(Genre genre);
        void onMangaSelected(Comic comic);
        void onDestroy();
    }
}
