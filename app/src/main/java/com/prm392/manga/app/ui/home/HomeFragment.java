package com.prm392.manga.app.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.model.Genre;
import com.prm392.manga.app.ui.auth.LoginActivity;
import com.prm392.manga.app.ui.comic.ComicDetailActivity;
import com.prm392.manga.app.ui.home.adapter.ComicAdapter;
import com.prm392.manga.app.ui.home.adapter.ComicTypeAdapter;
import com.prm392.manga.app.ui.home.adapter.GenreAdapter;
import com.prm392.manga.app.ui.profile.ProfileFragment;
import com.prm392.manga.app.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private ViewPager2 comicTypeViewPager;
    private LinearLayout comicTypeIndicator;
    private RecyclerView genresRecyclerView, featuredRecyclerView, latestRecyclerView;

    private ComicTypeAdapter comicTypeAdapter;
    private GenreAdapter genreAdapter;
    private ComicAdapter featuredAdapter, latestAdapter;

    private HomeContract.Presenter presenter;

    private boolean isLoggedIn = false;
    private ImageButton btnProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnProfile = view.findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(v -> {
            // Không kiểm tra gì cả
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });


        return view;
    }
    private void checkLoginStatus() {
        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString("auth_token", null);
        isLoggedIn = token != null && !token.isEmpty();
    }
    @Override
    public void onResume() {
        super.onResume();
        checkLoginStatus(); // mỗi lần quay lại đều check lại
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRecyclerViews();
        setupViewPager();

        presenter = new HomePresenter(this);
        presenter.loadHomeData();
    }

    private void initViews(View view) {
        comicTypeViewPager = view.findViewById(R.id.comic_type_viewpager);
        comicTypeIndicator = view.findViewById(R.id.comic_type_indicator);
        genresRecyclerView = view.findViewById(R.id.genres_recyclerview);
        featuredRecyclerView = view.findViewById(R.id.featured_recyclerview);
        latestRecyclerView = view.findViewById(R.id.latest_recyclerview);
    }

    private void setupRecyclerViews() {
        genresRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        genreAdapter = new GenreAdapter(new ArrayList<>(), this::onGenreClick);
        genresRecyclerView.setAdapter(genreAdapter);

        GridLayoutManager featuredLayoutManager = new GridLayoutManager(getContext(), 3);
        featuredRecyclerView.setLayoutManager(featuredLayoutManager);
        featuredRecyclerView.setNestedScrollingEnabled(false); // Disable nested scrolling
        featuredRecyclerView.setHasFixedSize(true); // Improve performance
        featuredAdapter = new ComicAdapter(new ArrayList<>(), this::onMangaClick);
        featuredRecyclerView.setAdapter(featuredAdapter);

        GridLayoutManager latestLayoutManager = new GridLayoutManager(getContext(), 3);
        latestRecyclerView.setLayoutManager(latestLayoutManager);
        latestRecyclerView.setNestedScrollingEnabled(false); // Disable nested scrolling
        latestRecyclerView.setHasFixedSize(true); // Improve performance
        latestAdapter = new ComicAdapter(new ArrayList<>(), this::onMangaClick);
        latestRecyclerView.setAdapter(latestAdapter);
    }

    private void setupViewPager() {
        List<String> comicTypes = Arrays.asList("Manga", "Manhwa", "Manhua", "DC Comics", "Marvel Comics");
        comicTypeAdapter = new ComicTypeAdapter(comicTypes, this::onComicTypeClick);
        comicTypeViewPager.setAdapter(comicTypeAdapter);

        setupIndicators(comicTypes.size());

        comicTypeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }
        });
    }

    private void setupIndicators(int count) {
        comicTypeIndicator.removeAllViews();

        for (int i = 0; i < count; i++) {
            View indicator = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.indicator_size),
                    getResources().getDimensionPixelSize(R.dimen.indicator_size)
            );
            params.setMargins(8, 0, 8, 0);
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

    private void onMangaClick(Comic comic) {
        // Navigate to comic detail using the ComicDetailActivity
        Intent intent = ComicDetailActivity.getStartIntent(getContext(), comic.getId());
        startActivity(intent);
    }

    @Override
    public void navigateToComicDetail(Comic comic) {
        Intent intent = ComicDetailActivity.getStartIntent(getContext(), comic.getId());
        startActivity(intent);
    }


    private void onComicTypeClick(String comicType) {
        presenter.onComicTypeSelected(comicType);
    }

    private void onGenreClick(Genre genre) {
        presenter.onGenreSelected(genre);
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
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
    }


    @Override
    public void navigateToComicList(String type) {
    }

    @Override
    public void navigateToGenreList(Genre genre) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

}