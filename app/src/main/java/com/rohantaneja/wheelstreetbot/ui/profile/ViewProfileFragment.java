package com.rohantaneja.wheelstreetbot.ui.profile;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.database.UserDatabaseHelper;
import com.rohantaneja.wheelstreetbot.databinding.FragmentViewProfileBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.StringUtil;
import com.rohantaneja.wheelstreetbot.util.Utils;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends BaseFragment {

    private FragmentViewProfileBinding mBinding;
    private static final String TAG = ViewProfileFragment.class.getName();

    @Override
    public String getFragmentName() {
        return ViewProfileFragment.class.getName();
    }

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_profile, container, false);
        displayUserData();
        return mBinding.getRoot();
    }

    private void displayUserData() {
        String userId = ((User) Hawk.get(Constants.HAWK_USER_DETAILS)).getId();
        UserDatabaseHelper userDatabaseHelper = UserDatabaseHelper.getUserDatabaseHelperInstance(getActivity());
        User user = userDatabaseHelper.getUserFromDb(userId);

        hideViewsThatHaveNoData(user);
    }

    private void hideViewsThatHaveNoData(User user) {
        if (StringUtil.isNullOrEmpty(user.getName()))
            mBinding.nameTextView.setVisibility(View.GONE);

        if (StringUtil.isNullOrEmpty(user.getEmail()))
            mBinding.emailTextView.setVisibility(View.GONE);

        if (StringUtil.isNullOrEmpty(user.getGender()))
            mBinding.genderTextView.setVisibility(View.GONE);

        if (StringUtil.isNullOrEmpty(user.getAge()))
            mBinding.ageTextView.setVisibility(View.GONE);

        if (StringUtil.isNullOrEmpty(user.getMobile()))
            mBinding.mobileTextView.setVisibility(View.GONE);

        setUserData(user);
    }

    private void setUserData(User user) {
        mBinding.nameTextView.setText(getString(R.string.display_name, user.getName()));
        mBinding.emailTextView.setText(getString(R.string.display_email, user.getEmail()));
        mBinding.genderTextView.setText(getString(R.string.display_gender, user.getGender()));
        mBinding.ageTextView.setText(getString(R.string.display_age, user.getAge()));
        mBinding.mobileTextView.setText(getString(R.string.display_mobile_number, user.getMobile()));

        Picasso.get().load(Utils.getPicassoPath(user))
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .error(R.drawable.ic_account_circle_black_48dp)
                .into(mBinding.avatarImageView);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_view_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update_profile:
                ((BaseActivity) getActivity()).pushFragment(Constants.FRAGMENTS.UPDATE_PROFILE, null, Constants.ANIMATION_TYPE.SLIDE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
