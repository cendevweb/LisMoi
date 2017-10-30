package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
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

import java.util.Locale;

/**
 * Created by Phil on 22/10/2017.
 */

public class SwipeCardAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WordsList mWordsList;
    private TextToSpeech mTextToSpeech;
    private ViewHolder mViewHolder;

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
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.word_card, parent, false);

            mViewHolder = new ViewHolder();
            mViewHolder.wordLayout = convertView.findViewById(R.id.word_card_layout);
            mViewHolder.wordText = convertView.findViewById(R.id.word_card_word);
            mViewHolder.wordImage = convertView.findViewById(R.id.word_card_image);
            mViewHolder.sayWordImage = convertView.findViewById(R.id.word_card_say_word);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        final Word word = mWordsList.getWordList().get(position);

        mViewHolder.wordText.setText(word.getWord());
        Picasso.with(mContext).load(word.getImageUrl()).into(mViewHolder.wordImage);

        mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mTextToSpeech.setLanguage(Locale.FRENCH);
                mTextToSpeech.setSpeechRate(0.8f);
            }
        });

        mViewHolder.sayWordImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextToSpeech.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private CardView wordLayout;
        private TextView wordText;
        private ImageView wordImage;
        private ImageView sayWordImage;
    }
}
