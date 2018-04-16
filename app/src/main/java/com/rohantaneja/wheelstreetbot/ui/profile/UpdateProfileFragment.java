package com.rohantaneja.wheelstreetbot.ui.profile;


import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.database.UserDatabaseHelper;
import com.rohantaneja.wheelstreetbot.databinding.FragmentUpdateProfileBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.AlertUtil;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends BaseFragment implements View.OnClickListener {

    private FragmentUpdateProfileBinding mBinding;
    private User mUser;

    @Override
    public String getFragmentName() {
        return UpdateProfileFragment.class.getName();
    }

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile, container, false);
        displayUserData();
        return mBinding.getRoot();
    }

    private void displayUserData() {

        mBinding.updateButton.setOnClickListener(this);
        mBinding.cancelButton.setOnClickListener(this);
        mBinding.avatarImageView.setOnClickListener(this);

        String userId = ((User) Hawk.get(Constants.HAWK_USER_DETAILS)).getId();
        UserDatabaseHelper userDatabaseHelper = UserDatabaseHelper.getUserDatabaseHelperInstance(getActivity());
        mUser = userDatabaseHelper.getUserFromDb(userId);

        setUserData();
    }

    private void setUserData() {
        Picasso.get().load(Uri.parse(mUser.getAvatarUrl()))
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .error(R.drawable.ic_account_circle_black_48dp)
                .into(mBinding.avatarImageView);

        mBinding.nameEditText.setText(mUser.getName());
        mBinding.emailEditText.setText(mUser.getEmail());
        mBinding.mobileEditText.setText(mUser.getMobile());

        mBinding.ageNumberPicker.setMinValue(Constants.AGE_MIN_VALUE);
        mBinding.ageNumberPicker.setMaxValue(Constants.AGE_MAX_VALUE);
        mBinding.ageNumberPicker.setValue(Integer.parseInt(mUser.getAge()));

        if (mUser.getGender().equalsIgnoreCase(Constants.GENDER_FEMALE)) {
            mBinding.femaleRadioButton.setChecked(true);
        } else if (mUser.getGender().equalsIgnoreCase(Constants.GENDER_MALE)) {
            mBinding.maleRadioButton.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_button:
                updateProfileDetails();
                break;

            case R.id.cancel_button:
                cancelProfileDetails();
                break;
        }
    }

    private void cancelProfileDetails() {
        AlertUtil.createYesNoAlert(getActivity(), "Warning!", "This action will result in losing the entered data. Are you sure you want to proceeed?", new AlertUtil.OnAlertClickListener() {
            @Override
            public void onPositive(DialogInterface dialog) {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            @Override
            public void onNegative(DialogInterface dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    private void updateProfileDetails() {

        if (isNewDataValid()) {

            mUser.setName(mBinding.nameEditText.getText().toString());
            mUser.setEmail(mBinding.emailEditText.getText().toString());
            mUser.setMobile(mBinding.mobileEditText.getText().toString());
            mUser.setAge(String.valueOf(mBinding.ageNumberPicker.getValue()));

            //check if age was changed by the User manually
            if (!String.valueOf(mBinding.ageNumberPicker.getValue()).equalsIgnoreCase(mUser.getAge()))
                mUser.setIsAgeOverridden(Constants.IS_AGE_OVERRIDDEN_TRUE);
            else
                mUser.setIsAgeOverridden(Constants.IS_AGE_OVERRIDDEN_FALSE);

            //set gender if selected by user
            if (!StringUtil.isNullOrEmpty(mUser.getGender())) {
                mUser.setGender(mBinding.femaleRadioButton.isChecked() ? Constants.GENDER_FEMALE : Constants.GENDER_MALE);
            }

            // TODO: 16/04/18 check if avatar is from storage or facebook url
//            mUser.setAvatarPath();
//            mUser.setIsAvatarFromPath();

            UserDatabaseHelper userDatabaseHelper = UserDatabaseHelper.getUserDatabaseHelperInstance(getActivity());
            userDatabaseHelper.updateUserInDb(mUser);

            showToast("Profile Updated");
            Hawk.put(Constants.FROM_UPDATE_PROFILE, true);
            Hawk.put(Constants.HAWK_USER_DETAILS, mUser);

            getActivity().finish();
        }
    }

    private boolean isNewDataValid() {

        //check email and name (mandatory fields)
        if (!(isValidName() & isValidEmail()))
            return false;

        //check if mobile number is a valid one
        if (StringUtil.isNullOrEmpty(mBinding.mobileEditText.getText().toString())) {
            mBinding.mobileTextInputLayout.setErrorEnabled(false);
            mBinding.mobileTextInputLayout.setError("");
            return true;
        }

        if (!StringUtil.isValidMobileNumber(mBinding.mobileEditText.getText().toString())) {
            mBinding.mobileTextInputLayout.setErrorEnabled(true);
            mBinding.mobileTextInputLayout.setError("Invalid Mobile Number");
            return false;
        }

        return true;
    }

    private boolean isValidName() {
        if (StringUtil.isNullOrEmpty(mBinding.nameEditText.getText().toString())) {
            mBinding.nameTextInputLayout.setErrorEnabled(true);
            mBinding.nameTextInputLayout.setError("Invalid Name");
            return false;
        } else {
            mBinding.nameTextInputLayout.setErrorEnabled(false);
            mBinding.nameTextInputLayout.setError("");
            return true;
        }
    }

    private boolean isValidEmail() {
        if (!StringUtil.isValidEmail(mBinding.emailEditText.getText().toString())) {
            mBinding.emailTextInputLayout.setErrorEnabled(true);
            mBinding.emailTextInputLayout.setError("Invalid Email");
            return false;
        } else {
            mBinding.emailTextInputLayout.setErrorEnabled(false);
            mBinding.emailTextInputLayout.setError("");
            return true;
        }
    }
}
