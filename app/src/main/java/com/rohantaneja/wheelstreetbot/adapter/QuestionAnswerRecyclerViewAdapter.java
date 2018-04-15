package com.rohantaneja.wheelstreetbot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.adapter.viewholder.QuestionAnswerViewholder;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;
import com.rohantaneja.wheelstreetbot.model.User;
import com.rohantaneja.wheelstreetbot.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohantaneja on 15/04/18.
 */
public class QuestionAnswerRecyclerViewAdapter extends RecyclerView.Adapter<QuestionAnswerViewholder> {

    private final Context context;
    private List<QuestionAnswer> questionsList;
    private String userAvatarUrl;

    public QuestionAnswerRecyclerViewAdapter(Context context) {
        questionsList = new ArrayList<>();
        this.context = context;

        userAvatarUrl = ((User) Hawk.get(Constants.HAWK_USER_DETAILS)).getAvatarUrl();
        // TODO: 15/04/18 Retrieve user image url here from SharedPrefs
    }

    public void updateQuestionAnswerList(List<QuestionAnswer> questionsList) {
        this.questionsList = questionsList;
        notifyDataSetChanged();
    }

    @Override
    public QuestionAnswerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_answer, parent, false);
        return new QuestionAnswerViewholder(v, userAvatarUrl);
    }

    @Override
    public void onBindViewHolder(QuestionAnswerViewholder holder, int position) {
        QuestionAnswer questionAnswer = questionsList.get(position);
        holder.bindData(questionAnswer);
    }

    @Override
    public int getItemCount() {
        return questionsList == null ? 0 : questionsList.size();
    }
}