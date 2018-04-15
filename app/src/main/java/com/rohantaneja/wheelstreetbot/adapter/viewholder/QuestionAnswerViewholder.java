package com.rohantaneja.wheelstreetbot.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.databinding.ItemQuestionAnswerBinding;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.util.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class QuestionAnswerViewholder extends RecyclerView.ViewHolder {

    private ItemQuestionAnswerBinding mBinding;
    private String mAvatarUrl;

    public QuestionAnswerViewholder(View itemView, String avatarUrl) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
        mAvatarUrl = avatarUrl;
    }

    public void bindData(final QuestionAnswer questionAnswer) {
        if (questionAnswer.getAnswer() == null) {
            mBinding.answerGroup.setVisibility(View.GONE);
        } else {
            mBinding.answerGroup.setVisibility(View.VISIBLE);
            mBinding.chatAnswerTextView.setText(questionAnswer.getAnswer().toString());
            Picasso.get().load(mAvatarUrl)
                    .placeholder(R.drawable.ic_account_circle_black_48dp)
                    .error(R.drawable.ic_account_circle_black_48dp)
                    .into(mBinding.chatAnswerImageView);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.chatQuestionImageView.setImageResource(R.drawable.wheelstreet_logo);
                mBinding.chatQuestionTextView.setText(questionAnswer.getQuestion());
            }
        }, Constants.NEXT_QUESTION_DELAY);
    }
}
