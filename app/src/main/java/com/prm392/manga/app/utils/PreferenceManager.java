package com.prm392.manga.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static PreferenceManager instance;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "MangaHiHi_Prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AVATAR_URL = "avatar_url";
    private static final String KEY_IS_FIRST_LAUNCH = "is_first_launch";
    private static final String KEY_READING_MODE = "reading_mode";
    private static final String KEY_AUTO_SAVE_HISTORY = "auto_save_history";

    private PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void initialize(Context context) {
        if (instance == null) {
            synchronized (PreferenceManager.class) {
                if (instance == null) {
                    instance = new PreferenceManager(context);
                }
            }
        }
    }

    public static PreferenceManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("PreferenceManager must be initialized first");
        }
        return instance;
    }

    public void saveAuthToken(String token) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public void clearAuthToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply();
    }

    public void saveUserInfo(int userId, String username, String email, String avatarUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_AVATAR_URL, avatarUrl);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getAvatarUrl() {
        return sharedPreferences.getString(KEY_AVATAR_URL, "");
    }

    public void clearUserInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_AVATAR_URL);
        editor.apply();
    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        sharedPreferences.edit().putBoolean(KEY_IS_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_LAUNCH, true);
    }

    public void setReadingMode(String mode) {
        sharedPreferences.edit().putString(KEY_READING_MODE, mode).apply();
    }

    public String getReadingMode() {
        return sharedPreferences.getString(KEY_READING_MODE, "vertical");
    }

    public void setAutoSaveHistory(boolean autoSave) {
        sharedPreferences.edit().putBoolean(KEY_AUTO_SAVE_HISTORY, autoSave).apply();
    }

    public boolean isAutoSaveHistory() {
        return sharedPreferences.getBoolean(KEY_AUTO_SAVE_HISTORY, true);
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        String token = getAuthToken();
        return token != null && !token.isEmpty();
    }
}
