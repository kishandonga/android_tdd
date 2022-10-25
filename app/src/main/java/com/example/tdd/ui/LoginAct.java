package com.example.tdd.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.tdd.BuildConfig;
import com.example.tdd.R;
import com.example.tdd.db.Repo;
import com.example.tdd.db.entities.User;
import com.example.tdd.utils.DataIntent;
import com.example.tdd.utils.RxDisposable;
import com.example.tdd.utils.Utils;
import com.example.tdd.utils.helper.Pair;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginAct extends AppCompatActivity {

    private TextInputEditText edEmail;
    private TextInputLayout edEmailLayout;
    private TextInputEditText edPwd;
    private TextInputLayout edPwdLayout;
    private AppCompatButton btnLogin;
    private AppCompatTextView btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();

        btnLogin.setOnClickListener(view -> {

            String emailStr = edEmail.getText() != null ? edEmail.getText().toString() : null;
            Pair<String, Boolean> email = Utils.validateEmail(emailStr);

            if (!email.second) {
                edEmailLayout.setError(email.first);
            }

            String pwdStr = edPwd.getText() != null ? edPwd.getText().toString() : null;
            Pair<String, Boolean> pwd = Utils.validatePassword(pwdStr);

            if (!pwd.second) {
                edPwdLayout.setError(pwd.first);
            }

            if (email.second && pwd.second) {

                User user = new User();
                user.setEmail(email.first);
                user.setPassword(pwd.first);
                Repo.isExists(this, user, (user1) -> {
                    DataIntent.getInstance().setUser(user1);
                    startActivity(new Intent(LoginAct.this, SelectFruitAct.class));
                    finish();
                }, (t) -> Utils.showSnack(this, t));
            }
        });

        btnSignUp.setOnClickListener(view -> startActivity(new Intent(LoginAct.this, SignUpAct.class)));
    }

    private void initView() {
        edEmail = findViewById(R.id.edEmail);
        edEmailLayout = findViewById(R.id.edEmailLayout);
        edPwd = findViewById(R.id.edPwd);
        edPwdLayout = findViewById(R.id.edPwdLayout);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnDoSignUp);

        if (BuildConfig.DEBUG) {
            edEmail.setText("k@d.com");
            edPwd.setText("Geeks@portal20");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.clear();
    }
}
