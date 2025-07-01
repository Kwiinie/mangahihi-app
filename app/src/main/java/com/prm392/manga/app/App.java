package com.prm392.manga.app;

import android.app.Application;

import com.prm392.manga.app.utils.PreferenceManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.initialize(this);

        initializeComponents();
    }

    private void initializeComponents() {
    }
}
