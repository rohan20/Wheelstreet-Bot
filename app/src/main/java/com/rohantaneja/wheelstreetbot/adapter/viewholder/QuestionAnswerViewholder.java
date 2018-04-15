package com.rohantaneja.wheelstreetbot.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rohantaneja.wheelstreetbot.databinding.ItemQuestionAnswerBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.squareup.picasso.Picasso;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class QuestionAnswerViewholder extends RecyclerView.ViewHolder {

    private ItemQuestionAnswerBinding mBinding;
    private String avatarUrl;

    public QuestionAnswerViewholder(View itemView, String avatarUrl) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
        this.avatarUrl = avatarUrl;
    }

    public void bindData(QuestionAnswer questionAnswer) {
        if (questionAnswer.getAnswer() == null) {
            mBinding.answerGroup.setVisibility(View.GONE);
        } else {
            mBinding.answerGroup.setVisibility(View.VISIBLE);
            mBinding.chatAnswerTextView.setText(questionAnswer.getAnswer().toString());
            Picasso.get().load(Uri.parse(avatarUrl)).into(mBinding.chatAnswerImageView);
        }

        mBinding.chatQuestionTextView.setText(questionAnswer.getQuestion());
    }
}
