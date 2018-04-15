package com.rohantaneja.wheelstreetbot.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ActivityHomeBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.auth.LoginActivity;
import com.rohantaneja.wheelstreetbot.ui.profile.ProfileActivity;
import com.rohantaneja.wheelstreetbot.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getName();
    ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        initUI();
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mBinding.profileInfoTextView.setOnClickListener(this);
        hideProgressDialog();

        fetchProfileDetails();
    }

    private void fetchProfileDetails() {
        showProgressDialog("Fetching details...");

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    setProfileDetails(response.getJSONObject());
                } catch (JSONException e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,birthday,gender,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setProfileDetails(JSONObject user) throws JSONException {
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

        Log.d(TAG, "id: " + id + "\nname: " + name + "\nemail: " + email + "\nbirthday: " + birthday + "\ngender: " + gender + "\nUri: " + picture.toString());
        mBinding.profileInfoTextView.setText("id: " + id + "\nname: " + name + "\nemail: " + email + "\nbirthday: " + birthday + "\ngender: " + gender + "\nUri: " + picture.toString());

        //save current user's data
        User currentUser = new User(Long.valueOf(id), name, email, birthday, gender, picture.toString());
        Hawk.put(Constants.HAWK_USER_DETAILS, currentUser);

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
            case R.id.profile_info_text_view:
                Intent i = new Intent(this, QuestionsActivity.class);
                startActivity(i);
                break;
        }
    }
}
