package com.rohantaneja.wheelstreetbot.ui.survey;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.FragmentSubmitSurveyBinding;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_submit_survey, container, false);
        ((BaseActivity) getActivity()).hideProgressDialog();
        return mBinding.getRoot();
    }

}
