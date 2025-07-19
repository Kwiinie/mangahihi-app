package com.prm392.manga.app.ui.auth;

public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void onLoginSuccess();
        void showError(String message);
    }

    interface Presenter {
        void login(String email, String password);
        void onDestroy();
    }
}
