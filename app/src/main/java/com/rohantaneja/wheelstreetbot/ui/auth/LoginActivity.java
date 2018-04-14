package com.rohantaneja.wheelstreetbot.ui.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ActivityLoginBinding;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.HomeActivity;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getName();
    private ActivityLoginBinding mBinding;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        hideProgressDialog();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        initFacebookLogin();
    }

    private void initFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();

        // Callback registration
        mBinding.facebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                showProgressDialog("Signing in...");

                setResult(RESULT_OK);
                Log.d(TAG, "onSuccess");

                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();

            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                showToast("Unable to login");
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                showToast("Unable to login");
                Log.d(TAG, "onError");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
