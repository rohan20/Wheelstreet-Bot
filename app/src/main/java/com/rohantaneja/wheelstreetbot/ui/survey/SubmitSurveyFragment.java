package com.rohantaneja.wheelstreetbot.ui.survey;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.database.UserDatabaseHelper;
import com.rohantaneja.wheelstreetbot.databinding.FragmentSubmitSurveyBinding;
import com.rohantaneja.wheelstreetbot.databinding.FragmentViewProfileBinding;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitSurveyFragment extends BaseFragment {

    private FragmentSubmitSurveyBinding mBinding;
    private static final String TAG = SubmitSurveyFragment.class.getName();

    @Override
    public String getFragmentName() {
        return SubmitSurveyFragment.class.getName();
    }

    public SubmitSurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_submit_survey, container, false);
        return mBinding.getRoot();
    }

}
