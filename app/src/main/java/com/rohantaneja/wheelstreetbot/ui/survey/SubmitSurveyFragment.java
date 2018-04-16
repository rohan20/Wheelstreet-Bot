package com.rohantaneja.wheelstreetbot.ui.survey;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.adapter.SurveyRecyclerViewAdapter;
import com.rohantaneja.wheelstreetbot.databinding.FragmentSubmitSurveyBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitSurveyFragment extends BaseFragment {

    private FragmentSubmitSurveyBinding mBinding;
    private static final String TAG = SubmitSurveyFragment.class.getName();
    private SurveyRecyclerViewAdapter mFinishedSurveyAdapter;
    private List<QuestionAnswer> mFinishedSurveyList;

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
        displaySurveyAnswers();
        return mBinding.getRoot();
    }

    private void displaySurveyAnswers() {
        ((BaseActivity) getActivity()).hideProgressDialog();

        initFinishedSurveyRecyclerView();
    }

    private void initFinishedSurveyRecyclerView() {
        mFinishedSurveyAdapter = new SurveyRecyclerViewAdapter(getActivity(), true);
        mBinding.finishedSurveyRecyclerView.setAdapter(mFinishedSurveyAdapter);
        mFinishedSurveyList = Hawk.contains(Constants.SURVEY_QUESTIONS_LIST) ? (List<QuestionAnswer>) Hawk.get(Constants.SURVEY_QUESTIONS_LIST) : new ArrayList<QuestionAnswer>();
        if (mFinishedSurveyList.size() == 0) {
            showToast("No survey found. Please try again.");
        }
        mFinishedSurveyAdapter.updateQuestionAnswerList(mFinishedSurveyList);
    }

}
