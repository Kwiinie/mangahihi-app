package com.prm392.manga.app.ui.comic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.ui.chapter.ChapterActivity;
import com.prm392.manga.app.ui.comic.adapter.ChapterAdapter;
import com.prm392.manga.app.ui.comic.adapter.GenreTagAdapter;

import java.util.ArrayList;
import java.text.DecimalFormat;


public class ComicDetailActivity extends AppCompatActivity implements ComicDetailContract.View {

    private static final String EXTRA_COMIC_ID = "extra_comic_id";
    private static final String EXTRA_COMIC = "extra_comic";

    private ImageView btnBack, btnFavorite, btnShare;
    private ImageView imgCover, bgBlur;
    private TextView txtTitle, txtAuthor, txtType, txtStatus, txtViews, txtChapterCount;
    private TextView txtDescription, btnToggleDescription;
    private RecyclerView recyclerGenres, recyclerChapters;
    private TextView btnStartReading, btnContinueReading;
    private View layoutLoading, layoutError;

    private GenreTagAdapter genreAdapter;
    private ChapterAdapter chapterAdapter;

    private ComicDetailContract.Presenter presenter;

    private Comic currentComic;
    private boolean isFavorite = false;
    private boolean isDescriptionExpanded = false;

    public static Intent getStartIntent(Context context, int comicId) {
        Intent intent = new Intent(context, ComicDetailActivity.class);
        intent.putExtra(EXTRA_COMIC_ID, comicId);
        return intent;
    }

    public static Intent getStartIntent(Context context, Comic comic) {
        Intent intent = new Intent(context, ComicDetailActivity.class);
        intent.putExtra(EXTRA_COMIC, comic);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        initViews();
        setupRecyclerViews();
        setupClickListeners();

        presenter = new ComicDetailPresenter(this);

        if (getIntent() != null) {
            if (getIntent().hasExtra(EXTRA_COMIC)) {
                Comic comic = (Comic) getIntent().getSerializableExtra(EXTRA_COMIC);
                if (comic != null) {
                    currentComic = comic;
                    displayBasicComicInfo(comic);
                    presenter.loadComicDetail(comic.getId());
                }
            } else if (getIntent().hasExtra(EXTRA_COMIC_ID)) {
                int comicId = getIntent().getIntExtra(EXTRA_COMIC_ID, -1);
                if (comicId != -1) {
                    presenter.loadComicDetail(comicId);
                }
            }
        }
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        btnFavorite = findViewById(R.id.btn_favorite);

        imgCover = findViewById(R.id.img_cover);
        bgBlur = findViewById(R.id.img_background_blur);
        txtTitle = findViewById(R.id.txt_title);
        txtAuthor = findViewById(R.id.txt_author);
        txtType = findViewById(R.id.txt_type);
        txtStatus = findViewById(R.id.txt_status);
        txtViews = findViewById(R.id.txt_views);
        txtChapterCount = findViewById(R.id.txt_chapter_count);

        txtDescription = findViewById(R.id.txt_description);
        btnToggleDescription = findViewById(R.id.btn_toggle_description);

        recyclerGenres = findViewById(R.id.recycler_genres);
        recyclerChapters = findViewById(R.id.recycler_chapters);

        btnStartReading = findViewById(R.id.btn_start_reading);
        btnContinueReading = findViewById(R.id.btn_continue_reading);

        layoutLoading = findViewById(R.id.layout_loading);
        layoutError = findViewById(R.id.layout_error);
    }

    private void setupRecyclerViews() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerGenres.setLayoutManager(flexboxLayoutManager);
        genreAdapter = new GenreTagAdapter(new ArrayList<>());
        recyclerGenres.setAdapter(genreAdapter);

        recyclerChapters.setLayoutManager(new LinearLayoutManager(this));
        chapterAdapter = new ChapterAdapter(new ArrayList<>(), this::onChapterClick);
        recyclerChapters.setAdapter(chapterAdapter);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnFavorite.setOnClickListener(v -> {

        });

        btnToggleDescription.setOnClickListener(v -> toggleDescription());

        btnStartReading.setOnClickListener(v -> {
            if (currentComic != null && !currentComic.getChapters().isEmpty()) {
                startReading(currentComic.getChapters().get(0));
            }
        });

        btnContinueReading.setOnClickListener(v -> {

        });
    }

    private void displayBasicComicInfo(Comic comic) {
        Glide.with(this)
                .load(comic.getCoverUrl())
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(32))
                        .placeholder(R.drawable.comic_cover_placeholder)
                        .error(R.drawable.comic_cover_placeholder))
                .into(imgCover);

        Glide.with(this)
                .load(comic.getCoverUrl())
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(32))
                        .placeholder(R.drawable.comic_cover_placeholder)
                        .error(R.drawable.comic_cover_placeholder))
                .into(bgBlur);

        txtTitle.setText(comic.getName());
        txtAuthor.setText(comic.getAuthor());
        txtType.setText(comic.getType());
        txtStatus.setText(getStatusDisplayName(comic.getComicStatus()));
        txtViews.setText(formatViews(comic.getView()));

        if (comic.getChapters() != null) {
            txtChapterCount.setText(comic.getChapters().size() + " chapters");
        }

        if (comic.getDescription() != null && !comic.getDescription().isEmpty()) {
            txtDescription.setText(comic.getDescription());
            setupDescriptionToggle(comic.getDescription());
        }

        if (comic.getGenreNames() != null && !comic.getGenreNames().isEmpty()) {
            genreAdapter.updateGenres(comic.getGenreNames());
        }
    }

    private void setupDescriptionToggle(String description) {
        String[] words = description.split(" ");
        if (words.length > 30) {
            String truncated = String.join(" ", java.util.Arrays.copyOfRange(words, 0, 30)) + "...";
            txtDescription.setText(truncated);
            btnToggleDescription.setVisibility(View.VISIBLE);
            btnToggleDescription.setText("Xem thêm");
        } else {
            btnToggleDescription.setVisibility(View.GONE);
        }
    }

    private void toggleDescription() {
        if (currentComic == null || currentComic.getDescription() == null) return;

        if (isDescriptionExpanded) {
            setupDescriptionToggle(currentComic.getDescription());
            isDescriptionExpanded = false;
        } else {
            txtDescription.setText(currentComic.getDescription());
            btnToggleDescription.setText("Rút gọn");
            isDescriptionExpanded = true;
        }
    }

    private String getStatusDisplayName(String status) {
        return status == "Completed" ? "Hoàn thành" : "Đang tiến hành";
    }

    private String formatViews(int views) {
        if (views >= 1000000) {
            return new DecimalFormat("#.#M").format(views / 1000000.0);
        } else if (views >= 1000) {
            return new DecimalFormat("#.#K").format(views / 1000.0);
        }
        return String.valueOf(views);
    }

    private void shareComic(Comic comic) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Check out this amazing comic: " + comic.getName() + " by " + comic.getAuthor());
        startActivity(Intent.createChooser(shareIntent, "Share Comic"));
    }

    private void startReading(Chapter chapter) {
        Intent intent = ChapterActivity.getStartIntent(this, chapter.getId());
        startActivity(intent);
    }

        private void onChapterClick(Chapter chapter) {
        startReading(chapter);
    }

    @Override
    public void showLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        layoutError.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComicDetail(Comic comic) {
        currentComic = comic;
        displayBasicComicInfo(comic);

        // Update chapters
        if (comic.getChapters() != null && !comic.getChapters().isEmpty()) {
            chapterAdapter.updateChapters(comic.getChapters());
        }

        hideLoading();
    }

    @Override
    public void navigateToChapter(Chapter chapter) {
        startReading(chapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}