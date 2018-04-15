package com.rohantaneja.wheelstreetbot.ui.profile;

import android.databinding.DataBindingUtil;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ActivityProfileBinding;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.util.Constants;

public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        pushFragment(Constants.FRAGMENTS.VIEW_PROFILE, null, false, Constants.ANIMATION_TYPE.SLIDE);
    }

}
