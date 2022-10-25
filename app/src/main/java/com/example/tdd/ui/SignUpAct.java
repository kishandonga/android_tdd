package com.example.tdd.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdd.databinding.ActivitySignUpBinding;
import com.example.tdd.db.Repo;
import com.example.tdd.db.entities.User;
import com.example.tdd.utils.RxDisposable;
import com.example.tdd.utils.Utils;
import com.example.tdd.utils.helper.Pair;

import timber.log.Timber;

public class SignUpAct extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignUp.setOnClickListener(view -> {
            String emailStr = binding.edEmail.getText() != null ? binding.edEmail.getText().toString() : null;
            Pair<String, Boolean> email = Utils.validateEmail(emailStr);

            if (!email.second) {
                binding.edEmailLayout.setError(email.first);
            }

            String pwdStr = binding.edPwd.getText() != null ? binding.edPwd.getText().toString() : null;
            Pair<String, Boolean> pwd = Utils.validatePassword(pwdStr);

            if (!pwd.second) {
                binding.edPwdLayout.setError(pwd.first);
            }

            String confirmPwdStr = binding.edConfirmPwd.getText() != null ? binding.edConfirmPwd.getText().toString() : null;
            Pair<String, Boolean> confirmPwd = Utils.validatePassword(confirmPwdStr);

            if (!confirmPwd.second) {
                binding.edConfirmPwdLayout.setError(confirmPwd.first);
            }

            if(pwd.second && confirmPwd.second){
                Pair<String, Boolean> comparePwd = Utils.comparePassword(pwd, confirmPwd);

                if (email.second && comparePwd.second) {
                    User user = new User();
                    user.setEmail(email.first);
                    user.setPassword(pwd.first);
                    Repo.addUser(this, user, (Void) -> Utils.showToast(this, "User Registered."), (t) -> {
                        Utils.showToast(this, "User already registered.");
                        Timber.e(t);
                    });
                } else {
                    if (!comparePwd.second) {
                        Utils.showSnack(this, comparePwd.first);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.clear();
    }
}
