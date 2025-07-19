package com.prm392.manga.app.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.User;
import com.prm392.manga.app.ui.auth.LoginActivity;
import com.prm392.manga.app.ui.auth.RegisterActivity;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter presenter;

    private TextView tvName, tvEmail, tvPassword, tvGuestMessage;
    private Button btnLogin, btnRegister, btnLogout, btnChangePassword;
    private LinearLayout layoutAuth, layoutGuest;
    private ProgressBar progressBar;
    private ImageView btnBack;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Ánh xạ view
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPassword = view.findViewById(R.id.tvPassword);
        tvGuestMessage = view.findViewById(R.id.tvGuestMessage);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        layoutAuth = view.findViewById(R.id.layoutAuth);
        layoutGuest = view.findViewById(R.id.layoutGuest);
        progressBar = view.findViewById(R.id.progressBar);
        btnBack = view.findViewById(R.id.btn_back);

        // Gắn presenter
        presenter = new ProfilePresenter(this);

        // Sự kiện các nút
        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), LoginActivity.class)));

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), RegisterActivity.class)));

        btnLogout.setOnClickListener(v -> presenter.logout());

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        // (Chưa làm) Đổi mật khẩu
        btnChangePassword.setOnClickListener(v ->
                Toast.makeText(getContext(), "Tính năng đang phát triển", Toast.LENGTH_SHORT).show());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadProfile(); // Tải lại mỗi lần quay lại Fragment
    }

    // Khi đã đăng nhập
    @Override
    public void showUserInfo(User user) {
        layoutAuth.setVisibility(View.VISIBLE);
        layoutGuest.setVisibility(View.GONE);
        tvName.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
        tvPassword.setText("*************");
    }

    // Khi chưa đăng nhập
    @Override
    public void showGuestView() {
        layoutAuth.setVisibility(View.GONE);
        layoutGuest.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}
