package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Phil on 12/10/2017.
 */

public class WordsResultAdapter extends RecyclerView.Adapter<WordsResultAdapter.ResultViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String, Boolean>> mWordsResult;

    public WordsResultAdapter(ArrayList<HashMap<String, Boolean>> wordsList, Context mContext) {
        this.mContext = mContext;
        this.mWordsResult = wordsList;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_result, parent, false);

        return new ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        final HashMap<String, Boolean> hasmap = mWordsResult.get(position);

        for (String key : hasmap.keySet()) {
            holder.mItemWordResultText.setText(key);

            Boolean isValidated = hasmap.get(key);

            if (isValidated == Boolean.TRUE) {
                holder.mItemWordResultStatus.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_check));
            } else if (isValidated == Boolean.FALSE) {
                holder.mItemWordResultStatus.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_close));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mWordsResult.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemWordResultText;
        private ImageView mItemWordResultStatus;

        private ResultViewHolder(View v) {
            super(v);

            mItemWordResultText = v.findViewById(R.id.item_word_result_text);
            mItemWordResultStatus = v.findViewById(R.id.item_word_result_status);
        }
    }
}
