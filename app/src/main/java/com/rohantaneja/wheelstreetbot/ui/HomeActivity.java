package com.rohantaneja.wheelstreetbot.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.database.UserDatabaseHelper;
import com.rohantaneja.wheelstreetbot.databinding.ActivityHomeBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.auth.LoginActivity;
import com.rohantaneja.wheelstreetbot.ui.profile.ProfileActivity;
import com.rohantaneja.wheelstreetbot.ui.survey.SurveyActivity;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getName();
    private ActivityHomeBinding mBinding;
    private int mCurrentUserId;
    private UserDatabaseHelper mUserDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Hawk.contains(Constants.FROM_UPDATE_PROFILE) && (Boolean) Hawk.get(Constants.FROM_UPDATE_PROFILE)) {
            setUserDetails((User) Hawk.get(Constants.HAWK_USER_DETAILS));
            Hawk.put(Constants.FROM_UPDATE_PROFILE, false);
        }

        if (Hawk.contains(Constants.IS_SURVEY_COMPLETE)) {
            mBinding.surveyDescTextView.setText(R.string.continue_survey_desc);
            mBinding.surveyButton.setText(R.string.continue_survey);
        } else {
            mBinding.surveyDescTextView.setText(getString(R.string.take_survey_desc));
            mBinding.surveyButton.setText(getString(R.string.take_survey));
        }
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mBinding.surveyButton.setOnClickListener(this);
        hideProgressDialog();

        mUserDatabaseHelper = UserDatabaseHelper.getUserDatabaseHelperInstance(this);
        fetchUserIdFromFacebook(this);
    }

    public void setProfileDetails(JSONObject user) throws JSONException {
        String id = user.getString("id");
        String name = user.getString("name");

        String email = null;
        if (user.has("email")) {
            email = user.getString("email");
        }

        String birthday = null;
        if (user.has("birthday")) {
            birthday = user.getString("birthday");
        }

        String gender = null;
        if (user.has("gender")) {
            gender = user.getString("gender");
        }

        Uri picture = Uri.parse(user.getJSONObject("picture").getJSONObject("data").getString("url"));

        //save current user's data
        User currentUser = new User(id, name, email, birthday, gender, "", "", Constants.IS_AGE_OVERRIDDEN_FALSE, picture.toString(), null, Constants.IS_AVATAR_FROM_PATH_FALSE);
        currentUser.setAge(currentUser.getAge());
        mUserDatabaseHelper.updateUserInDb(currentUser);
        setUserDetails(currentUser);
    }

    public void setUserDetails(User user) {
        Hawk.put(Constants.HAWK_USER_DETAILS, user);

        //set name and avatar for home screen
        mBinding.hiTextView.setText(getString(R.string.hi_name, user.getName()));
        Picasso.get().load(Uri.parse(user.getAvatarUrl()))
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .error(R.drawable.ic_account_circle_black_48dp)
                .into(mBinding.avatarImageView);

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

            case R.id.action_profile:
                viewProfile();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewProfile() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    private void signOut() {
        showProgressDialog("Signing out...");

        LoginManager.getInstance().logOut();

        //go back to login screen
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.survey_button:
                Intent i = new Intent(this, SurveyActivity.class);
                startActivity(i);
                break;
        }
    }
}
