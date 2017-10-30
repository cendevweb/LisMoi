package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.model.Word;
import com.lismoi.lis_moiapprendrelire.model.WordsList;
import com.squareup.picasso.Picasso;

/**
 * Created by Phil on 22/10/2017.
 */

public class SwipeCardAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WordsList mWordsList;

    public SwipeCardAdapter(Context context, LayoutInflater layoutInflater, WordsList wordsList) {
        this.mContext = context;
        this.mLayoutInflater = layoutInflater;
        this.mWordsList = wordsList;
    }

    @Override
    public int getCount() {
        return mWordsList.getWordList().size();
    }

    @Override
    public Object getItem(int position) {
        return mWordsList.getWordList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.word_card, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.wordText = convertView.findViewById(R.id.word_card_word);
            viewHolder.wordImage = convertView.findViewById(R.id.word_card_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Word word = mWordsList.getWordList().get(position);

        viewHolder.wordText.setText(word.getWord());
        Picasso.with(mContext).load(word.getImageUrl()).into(viewHolder.wordImage);

        return convertView;
    }

    private static class ViewHolder {
        public TextView wordText;
        public ImageView wordImage;
    }
}
