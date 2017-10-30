package com.lismoi.lis_moiapprendrelire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lismoi.lis_moiapprendrelire.adapters.CategoryAdapter;
import com.lismoi.lis_moiapprendrelire.model.Category;
import com.lismoi.lis_moiapprendrelire.model.DictionaryActivity;
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
    private Button mActivityMainButton;
    List<String> wordsList = new ArrayList();
    List<String> imageList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainRecycler = (RecyclerView) findViewById(R.id.activity_main_recycler);
        mActivityMainButton = (Button) findViewById(R.id.activity_main_button);

        mActivityMainButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                wordsList.add("orange");
                imageList.add("https://image.flaticon.com/icons/png/512/135/135620.png");
                wordsList.add("fraise");
                imageList.add("https://image.flaticon.com/icons/png/512/135/135717.png");
                wordsList.add("pomme");
                imageList.add("https://image.flaticon.com/icons/png/512/135/135728.png");
                TinyDB tinydb = new TinyDB(MainActivity.this);
                tinydb.putListString("wordsList", (ArrayList<String>) wordsList);
                tinydb.putListString("imageList", (ArrayList<String>) imageList);

            }
        });
        getWords();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_dictionary:
                Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
