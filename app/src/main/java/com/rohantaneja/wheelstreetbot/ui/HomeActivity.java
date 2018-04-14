package com.rohantaneja.wheelstreetbot.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ActivityHomeBinding;
import com.rohantaneja.wheelstreetbot.ui.auth.LoginActivity;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        hideProgressDialog();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        showProgressDialog("Signing out...");

        LoginManager.getInstance().logOut();

        //go back to login screen
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
