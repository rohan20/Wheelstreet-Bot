package com.rohantaneja.wheelstreetbot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.adapter.viewholder.QuestionAnswerViewholder;
import com.rohantaneja.wheelstreetbot.model.Question;

import java.util.List;

/**
 * Created by rohantaneja on 15/04/18.
 */
public class QuestionAnswerRecyclerViewAdapter extends RecyclerView.Adapter<QuestionAnswerViewholder> {
    private final Context context;
    private List<Question> questionsList;

    public QuestionAnswerRecyclerViewAdapter(List<Question> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @Override
    public QuestionAnswerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_q_and_a, parent, false);
        return new QuestionAnswerViewholder(v);
    }

    @Override
    public void onBindViewHolder(QuestionAnswerViewholder holder, int position) {
        Question question = questionsList.get(position);
        //TODO Fill in your logic for binding the view.
    }

    @Override
    public int getItemCount() {
        return questionsList == null ? 0 : questionsList.size();
    }
}