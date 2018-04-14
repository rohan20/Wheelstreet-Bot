package com.rohantaneja.wheelstreetbot.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rohantaneja.wheelstreetbot.databinding.ItemChatQAndABinding;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class QuestionAnswerViewholder extends RecyclerView.ViewHolder {

    ItemChatQAndABinding mBinding;

    public QuestionAnswerViewholder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }
}
