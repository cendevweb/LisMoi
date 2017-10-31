package com.lismoi.lis_moiapprendrelire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;

public class ResultActivity extends AppCompatActivity {

    private TextView mScoreTxt;
    private RatingBar mRatingBar;
    private double ratingRatio;
    private LinearLayout mResultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TinyDB tinyDB = new TinyDB(this);
        int levelLocked = tinyDB.getInt("levelLocked") + 1;
        tinyDB.putInt("levelLocked", levelLocked);

        mScoreTxt = (TextView) findViewById(R.id.result_score);
        mRatingBar = (RatingBar) findViewById(R.id.result_rating_bar);
        mResultLayout = (LinearLayout) findViewById(R.id.result_layout);

        Intent mIntent = getIntent();
        double nbItem = mIntent.getDoubleExtra("nbItem", 0.0);
        double nbSuccess = mIntent.getDoubleExtra("nbSuccess", 0.0);
        mScoreTxt.setText(nbSuccess + " / " + nbItem);
        ratingRatio = nbSuccess/nbItem;
        if (ratingRatio > 0.8){
            mRatingBar.setNumStars(5);
            mRatingBar.setRating(5);
        }else if(ratingRatio > 0.6){
            mRatingBar.setNumStars(4);
            mRatingBar.setRating(4);
        }else if(ratingRatio > 0.4){
            mRatingBar.setNumStars(3);
            mRatingBar.setRating(3);
        }else if(ratingRatio > 0.2){
            mRatingBar.setNumStars(2);
            mRatingBar.setRating(2);
        }else if(ratingRatio > 0){
            mRatingBar.setNumStars(1);
            mRatingBar.setRating(1);
        }else{
            mRatingBar.setNumStars(0);
            mRatingBar.setRating(0);
        }

        mResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });

    }
}
