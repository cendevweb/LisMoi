package com.lismoi.lis_moiapprendrelire.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.adapters.DictionaryAdapter;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private static final String TAG = "Dictionary";
    private RecyclerView mActivityDictionaryRecycler;
    private DictionaryAdapter mAdapter;
    List<String> wordsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        mActivityDictionaryRecycler = (RecyclerView) findViewById(R.id.activity_dictionary_recycler);

        getWords();

    }
    private enum Categories {
        FRUIT("Fruit"), ANIMAL("Animal");

        private String categoryName;

        Categories(String category) {
            this.categoryName = category;
        }
    }
    public void getWords() {
                TinyDB tinydb = new TinyDB(DictionaryActivity.this);
                wordsList = tinydb.getListString("wordsList");

                mAdapter = new DictionaryAdapter(wordsList, DictionaryActivity.this);
                mActivityDictionaryRecycler.setAdapter(mAdapter);
                mActivityDictionaryRecycler.setLayoutManager(new LinearLayoutManager(DictionaryActivity.this));
    }
}
