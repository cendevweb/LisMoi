package com.lismoi.lis_moiapprendrelire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;

public class ResultActivity extends AppCompatActivity {


    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        score = (TextView) findViewById(R.id.result_score);
        Intent mIntent = getIntent();
        int nbItem = mIntent.getIntExtra("nbItem", 0);
        int nbSucces = mIntent.getIntExtra("nbSucces", 0);
        score.setText(nbSucces+" / "+nbItem);

    }
}
