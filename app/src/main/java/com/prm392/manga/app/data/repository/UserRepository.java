package com.prm392.manga.app.data.repository;

import com.prm392.manga.app.data.api.ApiService;
import com.prm392.manga.app.data.model.LoginRequest;
import com.prm392.manga.app.data.model.RegisterRequest;
import com.prm392.manga.app.data.model.TokenResponse;
import com.prm392.manga.app.data.model.User;
import com.prm392.manga.app.utils.NetworkManager;
import com.prm392.manga.app.utils.PreferenceManager;

import io.reactivex.Observable;

public class UserRepository {

    private ApiService apiService;
    private PreferenceManager preferenceManager;

    public UserRepository() {
        this.apiService = NetworkManager.getInstance().getApiService();
        this.preferenceManager = PreferenceManager.getInstance();
    }

    public Observable<TokenResponse> login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return apiService.login(request)
                .doOnNext(tokenResponse -> {
                    preferenceManager.saveAuthToken(tokenResponse.getToken());
                });
    }

    public Observable<User> register(String username, String email, String password, String avatarUrl) {
        RegisterRequest request = new RegisterRequest(username, email, password, avatarUrl);
        return apiService.register(request);
    }

    public Observable<User> getCurrentUser() {
        String authToken = NetworkManager.createAuthHeader(preferenceManager.getAuthToken());
        return apiService.getCurrentUser(authToken);
    }

    public boolean isLoggedIn() {
        return preferenceManager.getAuthToken() != null && !preferenceManager.getAuthToken().isEmpty();
    }

    public void logout() {
        preferenceManager.clearAuthToken();
    }
}

