package com.prm392.manga.app.ui.favorite;

import com.prm392.manga.app.data.model.Favorite;
import com.prm392.manga.app.data.repository.FavoriteRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteContract.Presenter {

    private FavoriteContract.View view;
    private FavoriteRepository favoriteRepository;
    private CompositeDisposable compositeDisposable;

    public FavoritePresenter(FavoriteContract.View view) {
        this.view = view;
        this.favoriteRepository = new FavoriteRepository();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadFavorites() {
        if (view != null) {
            view.showLoading();
        }

        compositeDisposable.add(
                favoriteRepository.getFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Favorite>>() {
                                    @Override
                                    public void accept(List<Favorite> favorites) {
                                        if (view != null) {
                                            view.hideLoading();
                                            view.showFavorites(favorites);
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        if (view != null) {
                                            view.hideLoading();
                                            view.showError("Lỗi khi tải danh sách yêu thích");
                                        }
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
