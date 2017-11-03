package com.lismoi.lis_moiapprendrelire.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.adapters.DictionaryAdapter;

import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private RecyclerView mActivityDictionaryRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        getSupportActionBar().setTitle(R.string.my_dictionnary);

        mActivityDictionaryRecycler = (RecyclerView) findViewById(R.id.activity_dictionary_recycler);
        getWords();
    }

    public void getWords() {
        TinyDB tinydb = new TinyDB(DictionaryActivity.this);
        List<String> wordsList = tinydb.getListString("wordsList");
        List<String> imageList = tinydb.getListString("imageList");

        DictionaryAdapter mAdapter = new DictionaryAdapter(wordsList, imageList, DictionaryActivity.this);
        mActivityDictionaryRecycler.setAdapter(mAdapter);
        mActivityDictionaryRecycler.setLayoutManager(new LinearLayoutManager(DictionaryActivity.this));
        int resId = R.anim.layout_animation_slide_in;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(DictionaryActivity.this, resId);
        mActivityDictionaryRecycler.setLayoutAnimation(animation);
    }
}
