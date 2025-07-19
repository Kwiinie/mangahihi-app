package com.prm392.manga.app.ui.auth;

public interface RegisterContract {
    interface View {
        void showLoading();
        void hideLoading();
        void onRegisterSuccess();
        void showError(String message);
    }

    interface Presenter {
        void register(String username, String email, String password);
        void onDestroy();
    }
}
