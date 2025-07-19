package com.prm392.manga.app.ui.profile;

import com.prm392.manga.app.data.model.User;

public interface ProfileContract {
    interface View {
        void showUserInfo(User user);
        void showGuestView();
        void showLoading();
        void hideLoading();
        void showError(String message);
    }

    interface Presenter {
        void loadProfile();
        void logout();
        void onDestroy();
    }
}
