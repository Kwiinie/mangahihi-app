package com.prm392.manga.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prm392.manga.app.ui.auth.LoginActivity;
import com.prm392.manga.app.ui.favorite.FavoriteFragment;
import com.prm392.manga.app.ui.history.HistoryFragment;
import com.prm392.manga.app.ui.home.HomeFragment;
import com.prm392.manga.app.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navHome, navSearch, navFavorite, navHistory;
    private ImageView navHomeIcon, navSearchIcon, navFavoriteIcon, navHistoryIcon;
    private TextView navHomeText, navSearchText, navFavoriteText, navHistoryText;

    private Fragment currentFragment;
    private int currentTab = 0; // 0: Home, 1: Search, 2: Favorite, 3: History

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupClickListeners();

        // Load Home fragment by default
        loadFragment(new HomeFragment(), 0);


    }


    private void initViews() {
        navHome = findViewById(R.id.nav_home);
        navSearch = findViewById(R.id.nav_search);
        navFavorite = findViewById(R.id.nav_favorite);
        navHistory = findViewById(R.id.nav_history);

        navHomeIcon = findViewById(R.id.nav_home_icon);
        navSearchIcon = findViewById(R.id.nav_search_icon);
        navFavoriteIcon = findViewById(R.id.nav_favorite_icon);
        navHistoryIcon = findViewById(R.id.nav_history_icon);

        navHomeText = findViewById(R.id.nav_home_text);
        navSearchText = findViewById(R.id.nav_search_text);
        navFavoriteText = findViewById(R.id.nav_favorite_text);
        navHistoryText = findViewById(R.id.nav_history_text);
    }

    private void setupClickListeners() {
        navHome.setOnClickListener(v -> loadFragment(new HomeFragment(), 0));
//        navSearch.setOnClickListener(v -> loadFragment(new SearchFragment(), 1));
        navFavorite.setOnClickListener(v -> loadFragment(new FavoriteFragment(), 2));
//        navHistory.setOnClickListener(v -> loadFragment(new HistoryFragment(), 3));
    }

    private void loadFragment(Fragment fragment, int tabIndex) {
        if (currentTab == tabIndex && currentFragment != null) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        Fragment existingFragment = fragmentManager.findFragmentByTag("fragment_" + tabIndex);
        if (existingFragment != null) {
            transaction.show(existingFragment);
            currentFragment = existingFragment;
        } else {
            transaction.add(R.id.fragment_container, fragment, "fragment_" + tabIndex);
            currentFragment = fragment;
        }

        transaction.commit();
        currentTab = tabIndex;
        updateNavigation(tabIndex);
    }

    private void updateNavigation(int selectedTab) {
        resetNavItem(navHome, navHomeIcon, navHomeText);
        resetNavItem(navSearch, navSearchIcon, navSearchText);
        resetNavItem(navFavorite, navFavoriteIcon, navFavoriteText);
        resetNavItem(navHistory, navHistoryIcon, navHistoryText);

        switch (selectedTab) {
            case 0:
                setActiveNavItem(navHome, navHomeIcon, navHomeText);
                break;
            case 1:
                setActiveNavItem(navSearch, navSearchIcon, navSearchText);
                break;
            case 2:
                setActiveNavItem(navFavorite, navFavoriteIcon, navFavoriteText);
                break;
            case 3:
                setActiveNavItem(navHistory, navHistoryIcon, navHistoryText);
                break;
        }
    }

    private void resetNavItem(LinearLayout navItem, ImageView icon, TextView text) {
        navItem.setSelected(false);
        icon.setColorFilter(getResources().getColor(R.color.nav_item_color));
        text.setTextColor(getResources().getColor(R.color.nav_item_color));
    }

    private void setActiveNavItem(LinearLayout navItem, ImageView icon, TextView text) {
        navItem.setSelected(true);
        icon.setColorFilter(getResources().getColor(R.color.nav_item_active));
        text.setTextColor(getResources().getColor(R.color.nav_item_active));
    }
}