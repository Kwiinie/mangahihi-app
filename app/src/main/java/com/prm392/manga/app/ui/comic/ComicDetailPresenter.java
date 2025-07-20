package com.prm392.manga.app.ui.comic;

import com.prm392.manga.app.data.model.Favorite;
import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.repository.ComicRepository;
import com.prm392.manga.app.data.repository.FavoriteRepository;
import com.prm392.manga.app.data.repository.HistoryRepository;
import com.prm392.manga.app.utils.PreferenceManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ComicDetailPresenter implements ComicDetailContract.Presenter {

    private ComicDetailContract.View view;
    private ComicRepository comicRepository;
    private FavoriteRepository favoriteRepository;
    private HistoryRepository historyRepository;
    private PreferenceManager preferenceManager;
    private CompositeDisposable compositeDisposable;
    private boolean isFavorite = false;

    public ComicDetailPresenter(ComicDetailContract.View view) {
        this.view = view;
        this.comicRepository = new ComicRepository();
        this.favoriteRepository = new FavoriteRepository();
        this.historyRepository = new HistoryRepository();
        this.preferenceManager = PreferenceManager.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadComicDetail(int comicId) {
        if (view != null) {
            view.showLoading();
        }

        compositeDisposable.add(
                comicRepository.getComicById(comicId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Comic>() {
                                    @Override
                                    public void accept(Comic comic) {
                                        if (view != null) {
                                            view.showComicDetail(comic);
                                            checkIsFavorite(comic.getId());
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        if (view != null) {
                                            view.hideLoading();
                                            view.showError("Lỗi khi tải truyện: " + throwable.getMessage());
                                        }
                                    }
                                }
                        )
        );
    }

    @Override
    public void checkIsFavorite(final int comicId) {
        compositeDisposable.add(
                favoriteRepository.getFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Favorite>>() {
                                    @Override
                                    public void accept(List<Favorite> favorites) {
                                        boolean isFavorite = false;
                                        for (Favorite fav : favorites) {
                                            if (fav.getComicId() == comicId) {
                                                isFavorite = true;
                                                break;
                                            }
                                        }
                                        if (view != null) {
                                            view.setIsFavorite(isFavorite);
                                            view.setFavoriteIcon(isFavorite);
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        if (view != null) {
                                            view.setIsFavorite(false);
                                            view.setFavoriteIcon(false);
                                        }
                                    }
                                }
                        )
        );
    }

    @Override
    public void toggleFavorite(final Comic comic, boolean isCurrentlyFavorite) {
        if (isCurrentlyFavorite) {
            // 👉 XÓA YÊU THÍCH
            compositeDisposable.add(
                    favoriteRepository.removeFromFavorites(comic.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<Void>() {
                                        @Override
                                        public void accept(Void unused) {
                                            if (view != null) {
                                                view.setIsFavorite(false);
                                                view.setFavoriteIcon(false);
                                            }
                                        }
                                    },
                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) {
                                            if (view != null) {
                                                view.showError("Xoá yêu thích thất bại");
                                            }
                                        }
                                    }
                            )
            );
        } else {
            // 👉 THÊM YÊU THÍCH
            compositeDisposable.add(
                    favoriteRepository.addToFavorites(comic.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<Void>() {
                                        @Override
                                        public void accept(Void unused) {
                                            if (view != null) {
                                                view.setIsFavorite(true);
                                                view.setFavoriteIcon(true);
                                            }
                                        }
                                    },
                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) {
                                            if (view != null) {
                                                view.showError("Thêm yêu thích thất bại");
                                            }
                                        }
                                    }
                            )
            );
        }
    }
    public void addToFavorites(int comicId) {
        compositeDisposable.add(
                favoriteRepository.addToFavorites(comicId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                unused -> { // Đây chính là Consumer<Void>
                                    isFavorite = true;
                                    if (view != null) {
                                        view.updateFavoriteStatus(true);
                                        view.showMessage("Đã thêm vào yêu thích");
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showMessage("Thêm vào yêu thích thất bại: " + throwable.getMessage());
                                    }
                                }
                        )
        );
    }
    public void removeFromFavorites(int comicId) {
        compositeDisposable.add(
                favoriteRepository.removeFromFavorites(comicId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                unused -> {
                                    isFavorite = false;
                                    if (view != null) {
                                        view.updateFavoriteStatus(false);
                                        view.showMessage("Đã xoá khỏi yêu thích");
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showMessage("Xoá yêu thích thất bại: " + throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        view = null;
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
