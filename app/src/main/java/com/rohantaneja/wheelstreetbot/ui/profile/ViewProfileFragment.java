package com.rohantaneja.wheelstreetbot.ui.profile;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.FragmentViewProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends Fragment {

    private FragmentViewProfileBinding mBinding;

    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_profile, container, false);
        return mBinding.getRoot();
    }

}
