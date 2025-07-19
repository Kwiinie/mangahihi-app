package com.prm392.manga.app.ui.auth;

import com.prm392.manga.app.data.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter{

    private LoginContract.View view;
    private UserRepository userRepository;
    private CompositeDisposable disposables;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.userRepository = new UserRepository();
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void login(String email, String password) {
        if (view != null) {
            view.showLoading();
        }

        disposables.add(userRepository.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tokenResponse -> {
                            if (view != null) {
                                view.hideLoading();
                                view.onLoginSuccess();
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showError("Đăng nhập thất bại: " + throwable.getMessage());
                            }
                        }
                )
        );
    }
    @Override
    public void onDestroy() {
        view = null;
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
