package com.rohantaneja.wheelstreetbot.ui.profile;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.FragmentViewProfileBinding;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends BaseFragment {

    private FragmentViewProfileBinding mBinding;

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
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_view_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



}
