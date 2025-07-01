package com.prm392.manga.app.ui.comic;

import com.prm392.manga.app.data.repository.ComicRepository;
import com.prm392.manga.app.data.repository.FavoriteRepository;
import com.prm392.manga.app.data.repository.HistoryRepository;
import com.prm392.manga.app.utils.PreferenceManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ComicDetailPresenter implements ComicDetailContract.Presenter {

    private ComicDetailContract.View view;
    private ComicRepository comicRepository;
    private FavoriteRepository favoriteRepository;
    private HistoryRepository historyRepository;
    private PreferenceManager preferenceManager;
    private CompositeDisposable compositeDisposable;

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
                                comic -> {
                                    if (view != null) {
                                        view.showComicDetail(comic);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError("Lỗi khi tải truyện: " + throwable.getMessage());
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
