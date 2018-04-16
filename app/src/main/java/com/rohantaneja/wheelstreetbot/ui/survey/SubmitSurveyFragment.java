package com.rohantaneja.wheelstreetbot.ui.survey;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.adapter.SurveyRecyclerViewAdapter;
import com.rohantaneja.wheelstreetbot.databinding.FragmentSubmitSurveyBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.model.pojo.request.SubmittedSurveyRequest;
import com.rohantaneja.wheelstreetbot.model.pojo.response.SubmittedSurveyResponse;
import com.rohantaneja.wheelstreetbot.network.RetrofitAdapter;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.ui.BaseFragment;
import com.rohantaneja.wheelstreetbot.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitSurveyFragment extends BaseFragment implements View.OnClickListener {

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
        initUI();
        return mBinding.getRoot();
    }

    private void initUI() {
        mBinding.confirmAndSubmitButton.setOnClickListener(this);
        ((BaseActivity) getActivity()).hideProgressDialog();

        displayFinishedSurvey();
    }

    private void displayFinishedSurvey() {
        mFinishedSurveyAdapter = new SurveyRecyclerViewAdapter(getActivity(), true);
        mBinding.finishedSurveyRecyclerView.setAdapter(mFinishedSurveyAdapter);
        mFinishedSurveyList = Hawk.contains(Constants.SURVEY_QUESTIONS_LIST) ? (List<QuestionAnswer>) Hawk.get(Constants.SURVEY_QUESTIONS_LIST) : new ArrayList<QuestionAnswer>();
        if (mFinishedSurveyList.size() == 0) {
            showToast("No survey found. Please try again.");
        }
        mFinishedSurveyAdapter.updateQuestionAnswerList(mFinishedSurveyList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_and_submit_button:
                submitSurvey();
                break;
        }
    }

    private void submitSurvey() {

        ((BaseActivity) getActivity()).showProgressDialog("Submitting survey, please wait...");

        RetrofitAdapter retrofitAdapter = new RetrofitAdapter(Constants.BASE_URL);
        SubmittedSurveyRequest surveyRequest = new SubmittedSurveyRequest();

        User user = Hawk.get(Constants.HAWK_USER_DETAILS);

        surveyRequest.setName(user.getName());
        surveyRequest.setFbUserName("");
        surveyRequest.setMobile(user.getMobile());
        surveyRequest.setGender(user.getGender());
        surveyRequest.setAge(user.getAge());
        surveyRequest.setEmail(user.getEmail());
        surveyRequest.setQuestions(mFinishedSurveyList);

        retrofitAdapter.getWheelstreetAPI().submitSurvey(surveyRequest).enqueue(new Callback<SubmittedSurveyResponse>() {
            @Override
            public void onResponse(Call<SubmittedSurveyResponse> call, Response<SubmittedSurveyResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getData());
                    if (response.body().getStatus() == Constants.SURVEY_SUBMITTED_SUCCESSFULL) {
                        showToast(getString(R.string.survey_submitted_successfully));
                        getActivity().finish();

                        ((BaseActivity) getActivity()).hideProgressDialog();
                    } else {
                        Log.d(TAG, response.body().getData());
                        showToast("An error has occurred. Please try later.");
                        ((BaseActivity) getActivity()).hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubmittedSurveyResponse> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
                ((BaseActivity) getActivity()).hideProgressDialog();
            }
        });

    }
}
