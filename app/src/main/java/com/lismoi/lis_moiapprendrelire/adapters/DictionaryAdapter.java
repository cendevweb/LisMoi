package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by Cen on 21/10/2017.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {

    private Context mContext;
    private List<String> mDictionaryList;
    private List<String> mDictionaryImageList;
    private TextToSpeech mTextToSpeech;

    public DictionaryAdapter(List<String> wordsList, List<String> imageList, Context context) {
        this.mContext = context;
        this.mDictionaryList = wordsList;
        this.mDictionaryImageList = imageList;
    }

    @Override
    public DictionaryAdapter.DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_item_layout, parent, false);

        return new DictionaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DictionaryViewHolder holder, int position) {
        final String word = mDictionaryList.get(position);
        String image = mDictionaryImageList.get(position);
        holder.mDictionaryItemName.setText(word);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "font/soft_marshmallow.ttf");
        holder.mDictionaryItemName.setTypeface(font);

        Picasso.with(mContext).load(image).into(holder.mDictionaryItemImage);

        mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mTextToSpeech.setLanguage(Locale.FRENCH);
                mTextToSpeech.setSpeechRate(0.8f);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDictionaryList.size();
    }

    class DictionaryViewHolder extends RecyclerView.ViewHolder {

        private TextView mDictionaryItemName;
        private ImageView mDictionaryItemImage;

        private DictionaryViewHolder(View v) {
            super(v);

            mDictionaryItemName = v.findViewById(R.id.dictionary_item_name);
            mDictionaryItemImage = v.findViewById(R.id.dictionary_item_image);
        }
    }
}
