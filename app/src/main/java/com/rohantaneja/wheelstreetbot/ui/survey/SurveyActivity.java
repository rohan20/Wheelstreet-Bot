package com.rohantaneja.wheelstreetbot.ui.survey;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.adapter.SurveyRecyclerViewAdapter;
import com.rohantaneja.wheelstreetbot.databinding.ActivitySurveyBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.model.QuestionsResponse;
import com.rohantaneja.wheelstreetbot.network.RetrofitAdapter;
import com.rohantaneja.wheelstreetbot.ui.BaseActivity;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.Constants.FRAGMENTS;
import com.rohantaneja.wheelstreetbot.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SurveyActivity.class.getName();
    private ActivitySurveyBinding mBinding;
    private SurveyRecyclerViewAdapter mQuestionAnswerRecyclerViewAdapter;

    private List<QuestionAnswer> questionsList;
    private List<QuestionAnswer> questionAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_survey);
        mBinding.sendImageView.setOnClickListener(this);

        initQuestionAnswerChatView();
        fetchQuestions();
    }

    private void initQuestionAnswerChatView() {
        mQuestionAnswerRecyclerViewAdapter = new SurveyRecyclerViewAdapter(this);
        mBinding.chatRecyclerView.setAdapter(mQuestionAnswerRecyclerViewAdapter);
        questionAnswerList = new ArrayList<>();
        mQuestionAnswerRecyclerViewAdapter.updateQuestionAnswerList(questionAnswerList);
        // TODO: 15/04/18 Add item decoration (margin)
    }

    private void fetchQuestions() {
        showProgressDialog("Fetching data, please wait...");

        RetrofitAdapter retrofitAdapter = new RetrofitAdapter(Constants.BASE_URL);
        retrofitAdapter.getWheelstreetAPI().getQuestions().enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(Call<QuestionsResponse> call, Response<QuestionsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Successful: " + response.body().toString());

                    //store all questions in a list
                    questionsList = response.body().getData();
                    //but, display only the first question to the user
                    questionAnswerList.add(questionsList.get(0));

                    mQuestionAnswerRecyclerViewAdapter.updateQuestionAnswerList(questionAnswerList);
                    hideProgressDialog();
                } else {
                    Log.d(TAG, "Not successful: " + response.message());
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                hideProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_image_view:
                submitAnswerForCurrentQuestion();
                break;
        }
    }

    private void submitAnswerForCurrentQuestion() {
        Object answer = mBinding.answerEditText.getText().toString();

        int currentQuestionIndex = questionAnswerList.size() - 1;
        QuestionAnswer currentQuestion = questionAnswerList.get(currentQuestionIndex);

        //check if answer submitted is of the same format as required
        switch (currentQuestion.getAnswerType()) {
            case Constants.TYPE_BOOLEAN:
                //check if valid boolean
                if (!StringUtil.isValidBooleanTypeAnswer(String.valueOf(answer))) {
                    setValidationError(getString(R.string.valid_answer_boolean));
                    return;
                } else
                    removeValidationError();

                break;

            case Constants.TYPE_FLOAT:
                //check if valid float
                if (!StringUtil.isValidFloatValue(String.valueOf(answer))) {
                    setValidationError(getString(R.string.valid_answer_float));
                    return;
                } else
                    removeValidationError();

                break;

            case Constants.TYPE_INTEGER:
                //check if valid integer
                if (!StringUtil.isValidIntegerValue(String.valueOf(answer))) {
                    setValidationError(getString(R.string.valid_answer_integer));
                    return;
                } else
                    removeValidationError();

                break;

            default:
                //check if valid string
                if (StringUtil.isNullOrEmpty(String.valueOf(answer))) {
                    setValidationError(getString(R.string.valid_answer_string));
                    return;
                } else
                    removeValidationError();
        }

        questionAnswerList.get(currentQuestionIndex).setAnswer(answer);
        mQuestionAnswerRecyclerViewAdapter.updateQuestionAnswerList(questionAnswerList);

        //clear edit text
        mBinding.answerEditText.setText("");

        //if all questions haven't been answered, display the next question
        if (questionAnswerList.size() < questionsList.size())
            displayNextQuestion();
        else {
            mBinding.chatRecyclerView.getLayoutManager().smoothScrollToPosition(mBinding.chatRecyclerView, null, questionAnswerList.size() - 1);
            showProgressDialog("Please wait...");
            hideKeyboard();
            Hawk.put(Constants.IS_SURVEY_COMPLETE, true);
            Hawk.put(Constants.SURVEY_QUESTIONS_LIST, questionsList);
            pushFragment(FRAGMENTS.SUBMIT_SURVEY, R.id.survey_container_frame_layout, Constants.ANIMATION_TYPE.SLIDE);
        }
    }

    private void displayNextQuestion() {

        //add the next available question to the list of displayed questions
        questionAnswerList.add(questionsList.get(questionAnswerList.size()));

        mQuestionAnswerRecyclerViewAdapter.updateQuestionAnswerList(questionAnswerList);
        mBinding.chatRecyclerView.getLayoutManager().smoothScrollToPosition(mBinding.chatRecyclerView, null, questionAnswerList.size() - 1);

    }

    private void setValidationError(String validationMessage) {
        mBinding.answerTextInputLayout.setErrorEnabled(true);
        mBinding.answerTextInputLayout.setError(validationMessage);
    }

    private void removeValidationError() {
        mBinding.answerTextInputLayout.setErrorEnabled(false);
        mBinding.answerTextInputLayout.setError("");
    }
}
