package com.prm392.manga.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.model.Genre;
import com.prm392.manga.app.ui.comic.ComicDetailActivity;
import com.prm392.manga.app.ui.comic.ComicListActivity;
import com.prm392.manga.app.ui.home.adapter.ComicTypeAdapter;
import com.prm392.manga.app.ui.home.adapter.GenreAdapter;
import com.prm392.manga.app.ui.home.adapter.ComicAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private ViewPager2 comicTypeViewPager;
    private LinearLayout comicTypeIndicator;
    private RecyclerView genresRecyclerView, featuredRecyclerView, latestRecyclerView;
    private View loadingLayout;

    private ComicTypeAdapter comicTypeAdapter;
    private GenreAdapter genreAdapter;
    private ComicAdapter featuredAdapter, latestAdapter;

    private HomeContract.Presenter presenter;

    private List<String> comicTypes;
    private int currentComicTypeIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupRecyclerViews();
        setupViewPager();
        setupPresenter();

        loadData();
    }

    private void initViews() {
        comicTypeViewPager = findViewById(R.id.comic_type_viewpager);
        comicTypeIndicator = findViewById(R.id.comic_type_indicator);
        genresRecyclerView = findViewById(R.id.genres_recyclerview);
        featuredRecyclerView = findViewById(R.id.featured_recyclerview);
        latestRecyclerView = findViewById(R.id.latest_recyclerview);

        loadingLayout = findViewById(R.id.loading_layout);
    }

    private void setupRecyclerViews() {
        LinearLayoutManager genresLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        genresRecyclerView.setLayoutManager(genresLayoutManager);
        genreAdapter = new GenreAdapter(new ArrayList<>(), this::onGenreClick);
        genresRecyclerView.setAdapter(genreAdapter);

        GridLayoutManager featuredLayoutManager = new GridLayoutManager(this, 3);
        featuredRecyclerView.setLayoutManager(featuredLayoutManager);
        featuredAdapter = new ComicAdapter(new ArrayList<>(), this::onMangaClick);
        featuredRecyclerView.setAdapter(featuredAdapter);

        GridLayoutManager latestLayoutManager = new GridLayoutManager(this, 3);
        latestRecyclerView.setLayoutManager(latestLayoutManager);
        latestAdapter = new ComicAdapter(new ArrayList<>(), this::onMangaClick);
        latestRecyclerView.setAdapter(latestAdapter);
    }

    private void setupViewPager() {
        comicTypes = Arrays.asList("Manga", "Manhwa", "Manhua", "DC Comics", "Marvel Comics");
        comicTypeAdapter = new ComicTypeAdapter(comicTypes, this::onComicTypeClick);
        comicTypeViewPager.setAdapter(comicTypeAdapter);

        setupIndicators(comicTypes.size());

        comicTypeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentComicTypeIndex = position;
                updateIndicators(position);
            }
        });
    }

    private void setupIndicators(int count) {
        comicTypeIndicator.removeAllViews();

        for (int i = 0; i < count; i++) {
            View indicator = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.indicator_size),
                    getResources().getDimensionPixelSize(R.dimen.indicator_size)
            );
            params.setMargins(
                    getResources().getDimensionPixelSize(R.dimen.indicator_margin), 0,
                    getResources().getDimensionPixelSize(R.dimen.indicator_margin), 0
            );
            indicator.setLayoutParams(params);
            indicator.setBackgroundResource(R.drawable.indicator_inactive);
            comicTypeIndicator.addView(indicator);
        }

        if (count > 0) {
            updateIndicators(0);
        }
    }

    private void updateIndicators(int selectedPosition) {
        for (int i = 0; i < comicTypeIndicator.getChildCount(); i++) {
            View indicator = comicTypeIndicator.getChildAt(i);
            if (i == selectedPosition) {
                indicator.setBackgroundResource(R.drawable.indicator_active);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator.getLayoutParams();
                params.width = getResources().getDimensionPixelSize(R.dimen.indicator_active_width);
                indicator.setLayoutParams(params);
            } else {
                indicator.setBackgroundResource(R.drawable.indicator_inactive);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator.getLayoutParams();
                params.width = getResources().getDimensionPixelSize(R.dimen.indicator_size);
                indicator.setLayoutParams(params);
            }
        }
    }

    private void setupPresenter() {
        presenter = new HomePresenter(this);
    }

    private void loadData() {
        if (presenter != null) {
            presenter.loadHomeData();
        }
    }

    private void onComicTypeClick(String comicType) {
        if (presenter != null) {
            presenter.onComicTypeSelected(comicType);
        }
    }

    private void onGenreClick(Genre genre) {
        if (presenter != null) {
            presenter.onGenreSelected(genre);
        }
    }

    private void onMangaClick(Comic comic) {
        if (presenter != null) {
            presenter.onMangaSelected(comic);
        }
    }

    @Override
    public void showGenres(List<Genre> genres) {
        if (genreAdapter != null) {
            genreAdapter.updateGenres(genres);
        }
    }

    @Override
    public void showFeaturedComics(List<Comic> comics) {
        if (featuredAdapter != null) {
            featuredAdapter.updateComics(comics);
        }
    }

    @Override
    public void showLatestComics(List<Comic> comics) {
        if (latestAdapter != null) {
            latestAdapter.updateComics(comics);
        }
    }

    @Override
    public void showLoading() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void navigateToComicDetail(Comic comic) {
        Intent intent = new Intent(this, ComicDetailActivity.class);
        intent.putExtra("comic_id", comic.getId());
        intent.putExtra("comic_name", comic.getName());
        startActivity(intent);
    }

    @Override
    public void navigateToComicList(String type) {
        Intent intent = new Intent(this, ComicListActivity.class);
        intent.putExtra("list_type", "type");
        intent.putExtra("type_name", type);
        intent.putExtra("title", type + " Comics");
        startActivity(intent);
    }

    @Override
    public void navigateToGenreList(Genre genre) {
        Intent intent = new Intent(this, ComicListActivity.class);
        intent.putExtra("list_type", "genre");
        intent.putExtra("genre_id", genre.getId());
        intent.putExtra("genre_name", genre.getName());
        intent.putExtra("title", genre.getName() + " Comics");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.loadHomeData();
        }
    }
}
