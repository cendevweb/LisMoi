package com.lismoi.lis_moiapprendrelire.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private TextView mEndLevel;
    private TextView mScoreTxt;
    private TextView mCategoryName;
    private RatingBar mRatingBar;
    private double ratingRatio;
    private RelativeLayout mResultLayout;
    private TinyDB mTinydb;
    private Category mCategory;
    private ArrayList<HashMap<String, Integer>> mHashmapCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TinyDB tinyDB = new TinyDB(this);
        int levelLocked = tinyDB.getInt("levelLocked") + 1;
        tinyDB.putInt("levelLocked", levelLocked);

        mEndLevel = (TextView) findViewById(R.id.result_end_level);
        mScoreTxt = (TextView) findViewById(R.id.result_score);
        mCategoryName = (TextView) findViewById(R.id.result_category_name);
        mRatingBar = (RatingBar) findViewById(R.id.result_rating_bar);
        mResultLayout = (RelativeLayout) findViewById(R.id.result_layout);

        Intent mIntent = getIntent();
        double nbItem = mIntent.getDoubleExtra("nbItem", 0.0);
        double nbSuccess = mIntent.getDoubleExtra("nbSuccess", 0.0);
        if (getIntent().getExtras() != null) {
            mCategory = (Category) mIntent.getExtras().get("category");
        }
        int mResultItem = (int) nbItem;
        int mResultSuccess = (int) nbSuccess;

        mCategoryName.setText(mCategory.getCategoryName());
        mRatingBar.setVisibility(View.INVISIBLE);

        mScoreTxt.setText(mResultSuccess + " / " + mResultItem);
        Typeface font = Typeface.createFromAsset(ResultActivity.this.getAssets(), "font/soft_marshmallow.ttf");
        mEndLevel.setTypeface(font);
        mScoreTxt.setTypeface(font);
        mCategoryName.setTypeface(font);

        ratingRatio = nbSuccess / nbItem;
        if (ratingRatio > 0.8) {
            mRatingBar.setNumStars(5);
            mRatingBar.setRating(5);
        } else if (ratingRatio > 0.6) {
            mRatingBar.setNumStars(4);
            mRatingBar.setRating(4);
        } else if (ratingRatio > 0.4) {
            mRatingBar.setNumStars(3);
            mRatingBar.setRating(3);
        } else if (ratingRatio > 0.2) {
            mRatingBar.setNumStars(2);
            mRatingBar.setRating(2);
        } else if (ratingRatio > 0) {
            mRatingBar.setNumStars(1);
            mRatingBar.setRating(1);
        } else {
            mRatingBar.setNumStars(0);
            mRatingBar.setRating(0);
        }


        String hashMapString = tinyDB.getString("categoryHashMap");
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ArrayList<HashMap<String, Integer>>>() {
        }.getType();
        ArrayList<HashMap<String, Integer>> hashMapObject = gson.fromJson(hashMapString, type);

        for (HashMap<String, Integer> hashMap : hashMapObject) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                if (key.equals(mCategory.getCategoryName())) {
                    value = mRatingBar.getNumStars();
                }

                entry.setValue(value);

                HashMap<String, Integer> hashMapFinal = new HashMap<>();
                hashMapFinal.put(key, value);
                mHashmapCategories.add(hashMapFinal);
            }
        }

        saveHashMap();

        mRatingBar.postDelayed(new Runnable() {
            public void run() {
                mRatingBar.setVisibility(View.VISIBLE);
            }
        }, 200);

        mTinydb = new TinyDB(ResultActivity.this);
        mResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
    }

    private void saveHashMap() {
        Gson gson = new Gson();
        String hashMapString = gson.toJson(mHashmapCategories);

        TinyDB tinyDB = new TinyDB(ResultActivity.this);
        tinyDB.putString("categoryHashMap", hashMapString);
    }
}
