package com.rohantaneja.wheelstreetbot.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rohantaneja.wheelstreetbot.databinding.ItemChatQAndABinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class QuestionAnswerViewholder extends RecyclerView.ViewHolder {

    private ItemChatQAndABinding mBinding;

    public QuestionAnswerViewholder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }

    public void bindData(QuestionAnswer questionAnswer) {
        if (questionAnswer.getAnswer() == null) {
            mBinding.answerGroup.setVisibility(View.GONE);
        }

        mBinding.chatQuestionTextView.setText(questionAnswer.getQuestion());
    }
}
