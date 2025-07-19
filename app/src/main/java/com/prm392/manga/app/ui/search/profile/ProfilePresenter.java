package com.prm392.manga.app.ui.profile;

import com.prm392.manga.app.data.model.User;
import com.prm392.manga.app.data.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;
    private final UserRepository userRepository;
    private final CompositeDisposable disposable;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        this.userRepository = new UserRepository(); // tạo mới hoặc inject
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void loadProfile() {
        view.showLoading();

        // Nếu chưa đăng nhập → hiển thị giao diện khách
        if (!userRepository.isLoggedIn()) {
            view.hideLoading();
            view.showGuestView();
            return;
        }

        // Đã đăng nhập → lấy thông tin user từ API
        disposable.add(userRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            view.hideLoading();
                            view.showUserInfo(user);
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showError("Không thể tải thông tin người dùng.");
                        }
                ));
    }

    @Override
    public void logout() {
        userRepository.logout();
        view.showGuestView();
    }

    @Override
    public void onDestroy() {
        disposable.clear();
    }
}
