package com.prm392.manga.app.ui.home;



import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.model.Genre;
import com.prm392.manga.app.data.repository.ComicRepository;
import com.prm392.manga.app.data.repository.GenreRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private ComicRepository comicRepository;
    private GenreRepository genreRepository;
    private CompositeDisposable compositeDisposable;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.comicRepository = new ComicRepository();
        this.genreRepository = new GenreRepository();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadHomeData() {
        if (view != null) {
            view.showLoading();
        }

        loadGenres();
        loadFeaturedComics();
        loadLatestComics();
    }

    private void loadGenres() {
        compositeDisposable.add(
                genreRepository.getAllGenres()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                genres -> {
                                    if (view != null) {
                                        view.showGenres(genres);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showError("Failed to load genres: " + throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    private void loadFeaturedComics() {
        compositeDisposable.add(
                comicRepository.getFeaturedComics()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                comics -> {
                                    if (view != null) {
                                        view.showFeaturedComics(comics);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showError("Failed to load featured comics: " + throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    private void loadLatestComics() {
        compositeDisposable.add(
                comicRepository.getLatestComics()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                comics -> {
                                    if (view != null) {
                                        view.showLatestComics(comics);
                                        view.hideLoading();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showError("Failed to load latest comics: " + throwable.getMessage());
                                        view.hideLoading();
                                    }
                                }
                        )
        );
    }

    @Override
    public void onComicTypeSelected(String comicType) {
        if (view != null) {
            view.navigateToComicList(comicType);
        }
    }

    @Override
    public void onGenreSelected(Genre genre) {
        if (view != null) {
            view.navigateToGenreList(genre);
        }
    }

    @Override
    public void onMangaSelected(Comic comic) {
        if (view != null) {
            view.navigateToComicDetail(comic);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
