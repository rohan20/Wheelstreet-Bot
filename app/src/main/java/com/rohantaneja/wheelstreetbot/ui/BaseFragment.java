package com.rohantaneja.wheelstreetbot.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rohantaneja.wheelstreetbot.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    private Toast toast;

    public abstract String getFragmentName();

    protected void showToast(String message) {
        if (getActivity() != null && message != null) {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
