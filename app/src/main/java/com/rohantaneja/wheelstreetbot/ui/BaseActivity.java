package com.rohantaneja.wheelstreetbot.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.ui.profile.ProfileActivity;
import com.rohantaneja.wheelstreetbot.ui.profile.UpdateProfileFragment;
import com.rohantaneja.wheelstreetbot.ui.profile.ViewProfileFragment;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.Constants.ANIMATION_TYPE;
import com.rohantaneja.wheelstreetbot.util.Constants.FRAGMENTS;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();
    private ProgressDialog mProgressDialog;

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            if (!message.isEmpty())
                mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    //fetching user's profile data from Facebook
    public void fetchProfileDetailsFromFacebook(final Context context) {
        showProgressDialog("Fetching details...");

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if (context instanceof HomeActivity) {
                        ((HomeActivity) context).setProfileDetails(response.getJSONObject());
                    } else if (context instanceof ProfileActivity) {
//                        ((ProfileActivity) context).setProfileDetails(response.getJSONObject());
                    }
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


    //methods for adding/replacing fragment

    public static BaseFragment getFragment(FRAGMENTS fragmentId) {
        BaseFragment fragment = null;
        switch (fragmentId) {
            case VIEW_PROFILE:
                fragment = new ViewProfileFragment();
                break;
            case UPDATE_PROFILE:
                fragment = new UpdateProfileFragment();
                break;
        }
        return fragment;
    }

    public BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args, int containerViewId, boolean addToBackStack, boolean shouldAdd, ANIMATION_TYPE animationType) {
        try {
            BaseFragment fragment = getFragment(fragmentId);

            if (fragment == null) return null;
            if (args != null)
                fragment.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (animationType) {
                case DEFAULT:
                case SLIDE:
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    break;
                case FADE:
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    break;
                case NONE:
                    break;
            }
            if (shouldAdd)
                ft.add(containerViewId, fragment, fragment.getFragmentName());
            else
                ft.replace(containerViewId, fragment, fragment.getFragmentName());
            if (addToBackStack)
                ft.addToBackStack(fragment.getFragmentName());
            if (shouldAdd)
                ft.commit();
            else
                ft.commitAllowingStateLoss();

            return fragment;
        } catch (Exception x) {

        }
        return null;
    }

    public void pushFragment(FRAGMENTS fragmentId) {
        pushFragment(fragmentId, null, ANIMATION_TYPE.DEFAULT);
    }

    public void pushFragment(FRAGMENTS fragmentId, ANIMATION_TYPE animationType) {
        pushFragment(fragmentId, null, animationType);
    }

    public BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args) {
        return pushFragment(fragmentId, args, R.id.profile_container_frame_layout, true, ANIMATION_TYPE.DEFAULT);
    }

    public BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args, ANIMATION_TYPE animationType) {
        return pushFragment(fragmentId, args, R.id.profile_container_frame_layout, true, animationType);
    }

    public BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args, boolean addToBackStack) {
        return pushFragment(fragmentId, args, R.id.profile_container_frame_layout, addToBackStack, ANIMATION_TYPE.DEFAULT);
    }

    public BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args, boolean addToBackStack, ANIMATION_TYPE animationType) {
        return pushFragment(fragmentId, args, R.id.profile_container_frame_layout, addToBackStack, animationType);
    }

    public BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args, int containerViewId, boolean addToBackStack) {
        return pushFragment(fragmentId, args, containerViewId, addToBackStack, false, ANIMATION_TYPE.DEFAULT);
    }

    private BaseFragment pushFragment(FRAGMENTS fragmentId, Bundle args, int containerViewId, boolean addToBackStack, ANIMATION_TYPE animationType) {
        return pushFragment(fragmentId, args, containerViewId, addToBackStack, false, animationType);
    }

}
