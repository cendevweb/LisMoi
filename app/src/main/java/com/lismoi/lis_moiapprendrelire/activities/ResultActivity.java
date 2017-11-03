package com.lismoi.lis_moiapprendrelire.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.model.Category;

public class ResultActivity extends AppCompatActivity {

    private TextView mEndLevel;
    private TextView mScoreTxt;
    private TextView mCategoryName;
    private RatingBar mRatingBar;
    private double ratingRatio;
    private RelativeLayout mResultLayout;
    private TinyDB mTinydb;
    private Category mCategory;

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
        if (getIntent().getExtras() != null){
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

        mCategory.setValidated(true);
        mCategory.setStars(mRatingBar.getNumStars());

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
}
