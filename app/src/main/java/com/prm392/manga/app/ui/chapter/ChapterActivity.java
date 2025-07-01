package com.prm392.manga.app.ui.chapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.data.model.Page;
import com.prm392.manga.app.ui.chapter.adapter.PageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity implements ChapterContract.View {

    private static final String EXTRA_CHAPTER_ID = "extra_chapter_id";
    private static final String EXTRA_COMIC_ID = "extra_comic_id";
    private static final String EXTRA_CHAPTER_NUMBER = "extra_chapter_number";

    private AppBarLayout appBar;
    private ImageView btnBack;
    private TextView txtChapterTitle;
    private RecyclerView recyclerPages;
    private LinearLayout btnPreviousChapter, btnNextChapter;
    private View bottomNavigation;
    private View layoutLoading, layoutError;
    private TextView btnRetry;
    private PageAdapter pageAdapter;

    private ChapterContract.Presenter presenter;
    private Chapter currentChapter;
    private int currentComicId;
    private int currentPage = 1;
    private int totalPages = 0;

    private boolean isUiVisible = true;
    private Handler hideHandler = new Handler();
    private Runnable hideRunnable = this::hideSystemUI;

    public static Intent getStartIntent(Context context, int chapterId) {
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra(EXTRA_CHAPTER_ID, chapterId);
        return intent;
    }

    public static Intent getStartIntent(Context context, int comicId, int chapterNumber) {
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra(EXTRA_COMIC_ID, comicId);
        intent.putExtra(EXTRA_CHAPTER_NUMBER, chapterNumber);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        setupSystemUI();

        presenter = new ChapterPresenter(this);

        handleIntent();

        delayedHide(3000);
    }

    private void initViews() {
        appBar = findViewById(R.id.app_bar);
        btnBack = findViewById(R.id.btn_back);
        txtChapterTitle = findViewById(R.id.txt_chapter_title);
        recyclerPages = findViewById(R.id.recycler_pages);
        btnPreviousChapter = findViewById(R.id.btn_previous_chapter);
        btnNextChapter = findViewById(R.id.btn_next_chapter);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        layoutLoading = findViewById(R.id.layout_loading);
        layoutError = findViewById(R.id.layout_error);
        btnRetry = findViewById(R.id.btn_retry);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerPages.setLayoutManager(layoutManager);

        pageAdapter = new PageAdapter(this, new ArrayList<>());
        pageAdapter.setOnPageClickListener(new PageAdapter.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                toggleSystemUI();
            }

        });

        recyclerPages.setAdapter(pageAdapter);

        recyclerPages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateCurrentPageIndicator();
            }
        });
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnPreviousChapter.setOnClickListener(v -> {
            if (presenter != null) {
                presenter.navigateToPreviousChapter();
            }
        });

        btnNextChapter.setOnClickListener(v -> {
            if (presenter != null) {
                presenter.navigateToNextChapter();
            }
        });

        btnRetry.setOnClickListener(v -> handleIntent());
    }

    private void setupSystemUI() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_CHAPTER_ID)) {
                int chapterId = intent.getIntExtra(EXTRA_CHAPTER_ID, -1);
                if (chapterId != -1 && presenter != null) {
                    presenter.loadChapter(chapterId);
                }
            } else if (intent.hasExtra(EXTRA_COMIC_ID) && intent.hasExtra(EXTRA_CHAPTER_NUMBER)) {
                currentComicId = intent.getIntExtra(EXTRA_COMIC_ID, -1);
                int chapterNumber = intent.getIntExtra(EXTRA_CHAPTER_NUMBER, -1);
                if (currentComicId != -1 && chapterNumber != -1 && presenter != null) {
                    presenter.loadChapterByComicAndNumber(currentComicId, chapterNumber);
                }
            }
        }
    }

    private void updateCurrentPageIndicator() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerPages.getLayoutManager();
        if (layoutManager != null) {
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            if (firstVisiblePosition >= 0) {
                currentPage = firstVisiblePosition + 1;
                updatePageIndicator(currentPage, totalPages);
            }
        }
    }

    private void toggleSystemUI() {
        if (isUiVisible) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        isUiVisible = false;

        appBar.setVisibility(View.GONE);
        bottomNavigation.setVisibility(View.GONE);

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        }
    }

    private void showSystemUI() {
        isUiVisible = true;
        appBar.setVisibility(View.VISIBLE);
        bottomNavigation.setVisibility(View.VISIBLE);

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars());
        }
        delayedHide(3000);
    }

    private void delayedHide(int delayMillis) {
        hideHandler.removeCallbacks(hideRunnable);
        hideHandler.postDelayed(hideRunnable, delayMillis);
    }

    @Override
    public void showLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.GONE);
        recyclerPages.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        layoutLoading.setVisibility(View.GONE);
        recyclerPages.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        layoutError.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        recyclerPages.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChapterContent(Chapter chapter) {
        currentChapter = chapter;
        txtChapterTitle.setText(chapter.getTitle());

        if (presenter != null && currentComicId > 0) {
            presenter.saveReadingHistory(currentComicId, chapter.getId());
        }
    }

    @Override
    public void showPages(List<Page> pages) {
        if (pages != null && !pages.isEmpty()) {
            totalPages = pages.size();
            pageAdapter.updatePages(pages);
            updatePageIndicator(1, totalPages);
        }
    }

    @Override
    public void updatePageIndicator(int currentPage, int totalPages) {
    }

    @Override
    public void showPreviousChapterButton(boolean show) {
        btnPreviousChapter.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showNextChapterButton(boolean show) {
        btnNextChapter.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void navigateToPreviousChapter(Chapter chapter) {
        Intent intent = getStartIntent(this, chapter.getId());
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToNextChapter(Chapter chapter) {
        Intent intent = getStartIntent(this, chapter.getId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onReadingHistorySaved() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideHandler.removeCallbacks(hideRunnable);
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
