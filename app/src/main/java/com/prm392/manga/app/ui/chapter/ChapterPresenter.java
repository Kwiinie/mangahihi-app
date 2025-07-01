package com.prm392.manga.app.ui.chapter;

import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.repository.ChapterRepository;
import com.prm392.manga.app.data.repository.ComicRepository;
import com.prm392.manga.app.data.repository.HistoryRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChapterPresenter implements ChapterContract.Presenter {

    private ChapterContract.View view;
    private ComicRepository comicRepository;
    private ChapterRepository chapterRepository;
    private HistoryRepository historyRepository;
    private CompositeDisposable compositeDisposable;

    private Chapter currentChapter;
    private Comic currentComic;
    private List<Chapter> allChapters;
    private int currentChapterIndex = -1;

    public ChapterPresenter(ChapterContract.View view) {
        this.view = view;
        this.comicRepository = new ComicRepository();
        this.chapterRepository = new ChapterRepository();
        this.historyRepository = new HistoryRepository();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadChapter(int chapterId) {
        if (view == null) return;

        view.showLoading();

        compositeDisposable.add(
                chapterRepository.getChapterById(chapterId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                chapter -> {
                                    if (view == null) return;

                                    currentChapter = chapter;
                                    view.hideLoading();
                                    view.showChapterContent(chapter);
                                    view.showPages(chapter.getPages());

                                    if (chapter.getPages() != null && !chapter.getPages().isEmpty()) {
                                        view.updatePageIndicator(1, chapter.getPages().size());
                                    }

                                    loadComicForNavigation(chapter.getComicId());
                                },
                                throwable -> {
                                    if (view == null) return;
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void loadChapterByComicAndNumber(int comicId, int chapterNumber) {
        if (view == null) return;

        view.showLoading();

        compositeDisposable.add(
                comicRepository.getComicById(comicId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                comic -> {
                                    if (view == null) return;

                                    currentComic = comic;
                                    allChapters = comic.getChapters();

                                    Chapter targetChapter = null;
                                    for (int i = 0; i < allChapters.size(); i++) {
                                        Chapter chapter = allChapters.get(i);
                                        if (chapter.getNumber() == chapterNumber) {
                                            targetChapter = chapter;
                                            currentChapterIndex = i;
                                            break;
                                        }
                                    }

                                    if (targetChapter != null) {
                                        loadChapter(targetChapter.getId());
                                    } else {
                                        view.hideLoading();
                                        view.showError("Chapter not found");
                                    }
                                },
                                throwable -> {
                                    if (view == null) return;
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    private void loadComicForNavigation(int comicId) {
        compositeDisposable.add(
                comicRepository.getComicById(comicId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                comic -> {
                                    if (view == null) return;

                                    currentComic = comic;
                                    allChapters = comic.getChapters();

                                    for (int i = 0; i < allChapters.size(); i++) {
                                        if (allChapters.get(i).getId() == currentChapter.getId()) {
                                            currentChapterIndex = i;
                                            break;
                                        }
                                    }

                                    updateNavigationButtons();
                                },
                                throwable -> {
                                }
                        )
        );
    }

    private void updateNavigationButtons() {
        if (view == null || allChapters == null || currentChapterIndex == -1) return;

        view.showPreviousChapterButton(currentChapterIndex > 0);

        view.showNextChapterButton(currentChapterIndex < allChapters.size() - 1);
    }

    @Override
    public void navigateToPreviousChapter() {
        if (allChapters == null || currentChapterIndex <= 0) return;

        Chapter previousChapter = allChapters.get(currentChapterIndex - 1);
        if (view != null) {
            view.navigateToPreviousChapter(previousChapter);
        }
    }

    @Override
    public void navigateToNextChapter() {
        if (allChapters == null || currentChapterIndex >= allChapters.size() - 1) return;

        Chapter nextChapter = allChapters.get(currentChapterIndex + 1);
        if (view != null) {
            view.navigateToNextChapter(nextChapter);
        }
    }

    @Override
    public void saveReadingHistory(int comicId, int chapterId) {
    }

    @Override
    public void getCurrentChapterInfo() {
        if (view != null && currentChapter != null && currentComic != null) {
            view.showChapterContent(currentChapter);
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
        this.currentChapter = null;
        this.currentComic = null;
        this.allChapters = null;

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}