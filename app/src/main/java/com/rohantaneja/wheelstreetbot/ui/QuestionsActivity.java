package com.rohantaneja.wheelstreetbot.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.adapter.QuestionAnswerRecyclerViewAdapter;
import com.rohantaneja.wheelstreetbot.databinding.ActivityQuestionsBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.model.QuestionsResponse;
import com.rohantaneja.wheelstreetbot.network.RetrofitAdapter;
import com.rohantaneja.wheelstreetbot.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends BaseActivity {

    private static final String TAG = QuestionsActivity.class.getName();
    private ActivityQuestionsBinding mBinding;
    private QuestionAnswerRecyclerViewAdapter mQuestionAnswerRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_questions);

        initQuestionAnswerChatView();
        fetchQuestions();
    }

    private void initQuestionAnswerChatView() {
        mQuestionAnswerRecyclerViewAdapter = new QuestionAnswerRecyclerViewAdapter(this);
        mBinding.chatRecyclerView.setAdapter(mQuestionAnswerRecyclerViewAdapter);
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
                    List<QuestionAnswer> questionAnswerList = response.body().getData();
                    mQuestionAnswerRecyclerViewAdapter.updateQuestionAnswerList(response.body().getData());
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
}
