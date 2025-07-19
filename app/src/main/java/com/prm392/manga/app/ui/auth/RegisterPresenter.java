package com.prm392.manga.app.ui.auth;

import com.prm392.manga.app.data.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private UserRepository userRepository;
    private CompositeDisposable disposables;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.userRepository = new UserRepository();
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void register(String username, String email, String password) {
        if (view != null) {
            view.showLoading();
        }

        disposables.add(userRepository
                .register(username, email, password, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            if (view != null) {
                                view.hideLoading();
                                view.onRegisterSuccess();
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showError("Đăng ký thất bại: " + throwable.getMessage());
                            }
                        }
                ));
    }

    @Override
    public void onDestroy() {
        view = null;
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
