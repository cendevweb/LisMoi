package com.lismoi.lis_moiapprendrelire;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mActivityMainRecycler;
    private CategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainRecycler = (RecyclerView) findViewById(R.id.activity_main_recycler);

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
        RequestService githubService = new RestAdapter.Builder()
                .setEndpoint(RequestService.ENDPOINT)
                .build()
                .create(RequestService.class);

        githubService.getAllWords(new Callback<WordsList>() {
            @Override
            public void success(WordsList words, Response response) {
                Log.d("DEBUG", String.valueOf(words));

                List<String> categoryStringList = new ArrayList<>();

                for (Word word : words.getWords()) {
                    if (!categoryStringList.contains(word.getCategory())) {
                        categoryStringList.add(word.getCategory());
                    }
                }


                List<Category> categoryList = new ArrayList<>();

                for (int i = 0; i < categoryStringList.size(); i++) {
                    Category category = new Category();
                    for (Word word : words.getWords()) {
                        if (word.getCategory().equals(categoryStringList.get(i))) {
                            category.updateWordsNumber();
                        }
                    }
                    category.setCategoryName(categoryStringList.get(i));
                    categoryList.add(category);
                }

                mAdapter = new CategoryAdapter(categoryList, MainActivity.this);
                mActivityMainRecycler.setAdapter(mAdapter);
                mActivityMainRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
