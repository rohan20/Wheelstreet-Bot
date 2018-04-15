package com.rohantaneja.wheelstreetbot.ui.profile;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.database.UserDatabaseHelper;
import com.rohantaneja.wheelstreetbot.databinding.FragmentUpdateProfileBinding;
import com.rohantaneja.wheelstreetbot.databinding.FragmentViewProfileBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends BaseFragment {

    private FragmentUpdateProfileBinding mBinding;

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
        String userId = ((User) Hawk.get(Constants.HAWK_USER_DETAILS)).getId();
        UserDatabaseHelper userDatabaseHelper = UserDatabaseHelper.getUserDatabaseHelperInstance(getActivity());
        User user = userDatabaseHelper.getUserFromDb(userId);

        setUserData(user);
    }

    private void setUserData(User user) {

        Picasso.get().load(Uri.parse(user.getAvatarUrl()))
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .error(R.drawable.ic_account_circle_black_48dp)
                .into(mBinding.avatarImageView);

        mBinding.nameEditText.setText(user.getName());
        mBinding.emailEditText.setText(user.getEmail());
        mBinding.mobileEditText.setText(user.getMobile());

        mBinding.ageNumberPicker.setMinValue(Constants.AGE_MIN_VALUE);
        mBinding.ageNumberPicker.setMaxValue(Constants.AGE_MAX_VALUE);
        mBinding.ageNumberPicker.setValue(Integer.parseInt(user.getAge()));

        if (user.getGender().equalsIgnoreCase(Constants.GENDER_FEMALE)) {
            mBinding.femaleRadioButton.setChecked(true);
        } else if (user.getGender().equalsIgnoreCase(Constants.GENDER_MALE)) {
            mBinding.maleRadioButton.setChecked(true);
        }

    }

}
