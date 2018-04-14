package com.rohantaneja.wheelstreetbot.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
