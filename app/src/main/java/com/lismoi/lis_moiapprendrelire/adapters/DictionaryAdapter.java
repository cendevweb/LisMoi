package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;

import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Cen on 21/10/2017.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>{

    private Context mContext;
    private List<String> mDictionaryList;
    private TextToSpeech mTextToSpeech;


    public DictionaryAdapter(List<String> wordList, Context context) {
        this.mContext = context;
        this.mDictionaryList = wordList;
    }

    @Override
    public DictionaryAdapter.DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_item_layout, parent, false);

        return new DictionaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DictionaryViewHolder holder, int position) {
        String word = mDictionaryList.get(position);
        holder.mDictionaryItemName.setText(word);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "font/soft_marshmallow.ttf");
        holder.mDictionaryItemName.setTypeface(font);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "hello friend");

                mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        mTextToSpeech.setLanguage(Locale.FRENCH);
                        mTextToSpeech.setSpeechRate(0.8f);
                        mTextToSpeech.speak(holder.mDictionaryItemName.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDictionaryList.size();
    }
    class DictionaryViewHolder extends RecyclerView.ViewHolder {

        public TextView mDictionaryItemName;

        public DictionaryViewHolder(View v) {
            super(v);

            mDictionaryItemName = v.findViewById(R.id.dictionary_item_name);
        }
    }
}
