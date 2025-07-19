package com.prm392.manga.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prm392.manga.app.MainActivity;
import com.prm392.manga.app.R;
import com.prm392.manga.app.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupListeners();

        presenter = new LoginPresenter(this); // MVP binding
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showError("Vui lòng nhập đầy đủ thông tin");
            } else {
                presenter.login(email, password);
            }
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // MVP View Methods

    @Override
    public void showLoading() {
        // Có thể dùng ProgressDialog, hoặc loading layout sau
        Toast.makeText(this, "Đang đăng nhập...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        // Ẩn tiến trình nếu có
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
