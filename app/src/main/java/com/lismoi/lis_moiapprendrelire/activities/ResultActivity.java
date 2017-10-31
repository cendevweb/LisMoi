package com.lismoi.lis_moiapprendrelire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;

public class ResultActivity extends AppCompatActivity {

    private TextView mScoreTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TinyDB tinyDB = new TinyDB(this);
        int levelLocked = tinyDB.getInt("levelLocked") + 1;
        tinyDB.putInt("levelLocked", levelLocked);

        mScoreTxt = (TextView) findViewById(R.id.result_score);
        Intent intent = getIntent();
        int nbItem = intent.getIntExtra("nbItem", 0);
        int nbSucces = intent.getIntExtra("nbSuccess", 0);

        mScoreTxt.setText(nbSucces + " / " + nbItem);
    }
}
