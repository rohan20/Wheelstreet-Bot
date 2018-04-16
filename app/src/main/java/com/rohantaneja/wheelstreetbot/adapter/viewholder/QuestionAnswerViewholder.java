package com.rohantaneja.wheelstreetbot.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ItemQuestionAnswerBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.rohantaneja.wheelstreetbot.util.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class QuestionAnswerViewholder extends RecyclerView.ViewHolder {

    private ItemQuestionAnswerBinding mBinding;
    private User mUser;
    private boolean mIsFromCompletedSurvey;

    public QuestionAnswerViewholder(View itemView, User user, boolean isFromCompletedSurvey) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
        mUser = user;
        mIsFromCompletedSurvey = isFromCompletedSurvey;
    }

    public void bindData(QuestionAnswer questionAnswer) {
        if (mIsFromCompletedSurvey)
            bindCompletedSurveyData(questionAnswer);
        else
            bindOngoingSurveyData(questionAnswer);
    }

    private void bindOngoingSurveyData(QuestionAnswer questionAnswer) {
        if (questionAnswer.getAnswer() == null) {
            mBinding.answerGroup.setVisibility(View.GONE);
        } else {
            mBinding.answerGroup.setVisibility(View.VISIBLE);
            setAnswer(questionAnswer);
        }

        //check if current question requires delay or not
        if (Hawk.contains(Constants.ONGOING_SURVEY_QUESTIONS_LIST)) {
            setQuestion(questionAnswer);
        } else {
            addDelayToQuestions(questionAnswer);
        }

    }

    private void bindCompletedSurveyData(QuestionAnswer questionAnswer) {
        setQuestion(questionAnswer);
        setAnswer(questionAnswer);
    }

    private void setQuestion(QuestionAnswer questionAnswer) {
        mBinding.chatQuestionImageView.setImageResource(R.drawable.wheelstreet_logo);
        mBinding.chatQuestionTextView.setText(questionAnswer.getQuestion());
    }

    private void addDelayToQuestions(final QuestionAnswer questionAnswer) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setQuestion(questionAnswer);
            }
        }, Constants.NEXT_QUESTION_DELAY);
    }


    private void setAnswer(QuestionAnswer questionAnswer) {
        mBinding.chatAnswerTextView.setText(String.valueOf(questionAnswer.getAnswer()));

        Picasso.get().load(Utils.getPicassoPath(mUser))
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .error(R.drawable.ic_account_circle_black_48dp)
                .into(mBinding.chatAnswerImageView);
    }
}
