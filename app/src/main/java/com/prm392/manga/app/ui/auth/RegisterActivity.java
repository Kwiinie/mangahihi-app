package com.prm392.manga.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prm392.manga.app.R;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{
    private EditText edtUsername, edtEmail, edtPassword;
    private Button btnRegister;
    private RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupListeners();

        presenter = new RegisterPresenter(this);
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showError("Vui lòng điền đầy đủ thông tin.");
            } else {
                presenter.register(username, email, password);
            }
        });
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "Đang đăng ký...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        // Có thể xử lý UI nếu có loading custom
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "Đăng ký thành công! Hãy đăng nhập.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
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
