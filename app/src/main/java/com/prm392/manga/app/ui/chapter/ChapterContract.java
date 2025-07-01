package com.prm392.manga.app.ui.chapter;

import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.data.model.Page;

import java.util.List;

public interface ChapterContract {

    interface View{
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showChapterContent(Chapter chapter);
        void showPages(List<Page> pages);
        void updatePageIndicator(int currentPage, int totalPages);
        void showPreviousChapterButton(boolean show);
        void showNextChapterButton(boolean show);
        void navigateToPreviousChapter(Chapter chapter);
        void navigateToNextChapter(Chapter chapter);
        void onReadingHistorySaved();
    }

    interface Presenter {
        void loadChapter(int chapterId);
        void loadChapterByComicAndNumber(int comicId, int chapterNumber);
        void saveReadingHistory(int comicId, int chapterId);
        void navigateToPreviousChapter();
        void navigateToNextChapter();
        void getCurrentChapterInfo();
        void onDestroy();
    }
}