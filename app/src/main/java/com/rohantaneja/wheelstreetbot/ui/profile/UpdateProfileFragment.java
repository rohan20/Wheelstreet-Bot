package com.rohantaneja.wheelstreetbot.ui.profile;


import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.database.UserDatabaseHelper;
import com.rohantaneja.wheelstreetbot.databinding.BottomsheetUpdateAvatarBinding;
import com.rohantaneja.wheelstreetbot.databinding.FragmentUpdateProfileBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.AlertUtil;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.ImagePickerUtils;
import com.rohantaneja.wheelstreetbot.util.PermissionUtils;
import com.rohantaneja.wheelstreetbot.util.StringUtil;
import com.rohantaneja.wheelstreetbot.util.Utils;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends BaseFragment implements View.OnClickListener, ImagePickerUtils.OnImagePickerListener {

    private BottomsheetUpdateAvatarBinding mBottomSheetBinding;
    private FragmentUpdateProfileBinding mBinding;
    private User mUser;
    private static final String TAG = UpdateProfileFragment.class.getName();

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

        mBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.bottomsheet_update_avatar, mBinding.bottomSheetMembers, false);

        mBinding.updateButton.setOnClickListener(this);
        mBinding.cancelButton.setOnClickListener(this);
        mBinding.avatarImageView.setOnClickListener(this);

        mBottomSheetBinding.tvCancel.setOnClickListener(this);
        mBottomSheetBinding.tvTakePhoto.setOnClickListener(this);
        mBottomSheetBinding.tvFromGallery.setOnClickListener(this);
        mBottomSheetBinding.tvUseFacebookImage.setOnClickListener(this);

        String userId = ((User) Hawk.get(Constants.HAWK_USER_DETAILS)).getId();
        UserDatabaseHelper userDatabaseHelper = UserDatabaseHelper.getUserDatabaseHelperInstance(getActivity());
        mUser = userDatabaseHelper.getUserFromDb(userId);

        setUserData();
    }

    private void setUserData() {

        Picasso.get().load(Utils.getPicassoPath(mUser))
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

            case R.id.avatar_image_view:
                showImageSelector();
                break;

            case R.id.tv_cancel:
                mBinding.bottomSheetMembers.dismissSheet();
                break;

            case R.id.tv_use_facebook_image:
                mBinding.bottomSheetMembers.dismissSheet();

                mUser.setIsAvatarFromPath(Constants.IS_AVATAR_FROM_PATH_FALSE);

                Picasso.get().load(Utils.getPicassoPath(mUser))
                        .placeholder(R.drawable.ic_account_circle_black_48dp)
                        .error(R.drawable.ic_account_circle_black_48dp)
                        .into(mBinding.avatarImageView);

                break;

            case R.id.tv_take_photo:
                mBinding.bottomSheetMembers.dismissSheet();
                ImagePickerUtils.add(true, getActivity().getSupportFragmentManager(), this);
                break;

            case R.id.tv_from_gallery:
                mBinding.bottomSheetMembers.dismissSheet();
                ImagePickerUtils.add(false, getActivity().getSupportFragmentManager(), this);
                break;
        }
    }

    public void showImageSelector() {
        if (new PermissionUtils((BaseActivity) getActivity()).checkForImagePermission())
            mBinding.bottomSheetMembers.showWithSheetView(mBottomSheetBinding.getRoot());
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

            //check if age was changed by the User manually
            if (String.valueOf(mBinding.ageNumberPicker.getValue()).equalsIgnoreCase(mUser.getAge()))
                mUser.setIsAgeOverridden(Constants.IS_AGE_OVERRIDDEN_FALSE);
            else
                mUser.setIsAgeOverridden(Constants.IS_AGE_OVERRIDDEN_TRUE);
            
            mUser.setAge(String.valueOf(mBinding.ageNumberPicker.getValue()));

            //set gender if selected by user
            if (mBinding.femaleRadioButton.isChecked())
                mUser.setGender(Constants.GENDER_FEMALE);
            else if (mBinding.maleRadioButton.isChecked())
                mUser.setGender(Constants.GENDER_MALE);
            else
                mUser.setGender(null);

            //save updated user to database
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

    @Override
    public void success(String name, String path) {

        mUser.setAvatarPath(path);
        mUser.setIsAvatarFromPath(Constants.IS_AVATAR_FROM_PATH_TRUE);

        Picasso.get().load(Utils.getPicassoPath(mUser))
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .error(R.drawable.ic_account_circle_black_48dp)
                .into(mBinding.avatarImageView);

    }

    @Override
    public void fail(String message) {
        Log.d(TAG, "Failed to update avatar");
        showToast("Failed to update avatar");
    }
}
