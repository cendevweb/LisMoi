package com.lismoi.lis_moiapprendrelire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.RequestService;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.adapters.CategoryAdapter;
import com.lismoi.lis_moiapprendrelire.model.Category;
import com.lismoi.lis_moiapprendrelire.model.Word;
import com.lismoi.lis_moiapprendrelire.model.WordsList;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryAdapterListener {

    private RecyclerView mActivityMainRecycler;
    private CategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TinyDB tinyDB = new TinyDB(this);
        if (tinyDB.getInt("levelLocked") == 0) {
            tinyDB.putInt("levelLocked", 1);
        }

        mActivityMainRecycler = (RecyclerView) findViewById(R.id.activity_main_recycler);
        getWords();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dictionary:
                Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getWords() {
        RequestService requestService = new RestAdapter.Builder()
                .setEndpoint(RequestService.ENDPOINT)
                .build()
                .create(RequestService.class);

        requestService.getAllWords(new Callback<WordsList>() {
            @Override
            public void success(WordsList words, Response response) {
                List<String> categoryStringList = new ArrayList<>();

                for (Word word : words.getWordList()) {
                    if (!categoryStringList.contains(word.getCategory())) {
                        categoryStringList.add(word.getCategory());
                    }
                }

                List<Category> categoryList = new ArrayList<>();

                for (int i = 0; i < categoryStringList.size(); i++) {
                    Category category = new Category();
                    WordsList wordsList = new WordsList();

                    for (Word word : words.getWordList()) {
                        if (word.getCategory().equals(categoryStringList.get(i))) {
                            category.updateWordsNumber();
                            wordsList.addWordToList(word);

                            category.setImageUrl(word.getImageUrl());
                        }
                    }

                    category.setWordsList(wordsList);
                    category.setCategoryName(categoryStringList.get(i));
                    categoryList.add(category);
                }

                mAdapter = new CategoryAdapter(categoryList, MainActivity.this, MainActivity.this);
                mActivityMainRecycler.setAdapter(mAdapter);
                mActivityMainRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                int resId = R.anim.layout_animation_slide_in;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(MainActivity.this, resId);
                mActivityMainRecycler.setLayoutAnimation(animation);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onCategoryClicked(Category category) {
        Intent intent = new Intent(this, WordsActivity.class);
        intent.putExtra("categoryObj", category);
        startActivity(intent);
    }
}
