package com.lismoi.lis_moiapprendrelire.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.RippleBackground;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.adapters.SwipeCardAdapter;
import com.lismoi.lis_moiapprendrelire.cardsStack.SwingFlingAdapterView;
import com.lismoi.lis_moiapprendrelire.model.Category;
import com.lismoi.lis_moiapprendrelire.model.Word;
import com.lismoi.lis_moiapprendrelire.model.WordsList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordsActivity extends AppCompatActivity implements RecognitionListener {

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mRecognizerIntent;
    private Category mCategory;
    private SwingFlingAdapterView mSwipeFlingAdapterView;
    private Button mAddButton;
    private List<String> dicoWordList = new ArrayList();
    private List<String> dicoImageList = new ArrayList();
    private ImageView mMicrophoneButton;
    private SwipeCardAdapter mSwipeCardAdapter;
    private WordsList mWordsList;
    private TinyDB mTinydb;
    private int mTryNumber = 0;
    public double nbItem;
    public double nbSuccess = 0;
    private RippleBackground mRippleBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        if (getIntent().getExtras() != null) {
            mCategory = (Category) getIntent().getExtras().get("categoryObj");
        }

        mTinydb = new TinyDB(WordsActivity.this);
        mMicrophoneButton = (ImageView) findViewById(R.id.activity_word_microphone_button);
        mSwipeFlingAdapterView = (SwingFlingAdapterView) findViewById(R.id.activity_words_SwipeFlingAdapterView);
        mAddButton = (Button) findViewById(R.id.activity_word_add_button);
        mRippleBackground = (RippleBackground) findViewById(R.id.activity_words_ripple_background);

        mAddButton.setOnClickListener(mAddButtonListener);

        getSupportActionBar().setTitle(mCategory.getCategoryName());

        mWordsList = mCategory.getWordsList();

        mSwipeCardAdapter = new SwipeCardAdapter(this, getLayoutInflater(), mWordsList);

        mSwipeFlingAdapterView.setAdapter(mSwipeCardAdapter);

        mSwipeFlingAdapterView.setFlingListener(new SwingFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                mWordsList.getWordList().remove(0);
                mSwipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
            }

            @Override
            public void onRightCardExit(Object dataObject) {
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float v) {

            }
        });
        nbItem = mCategory.getWordsInCategoryNumber();
        mMicrophoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpeechRecognizer.startListening(mRecognizerIntent);
            }
        });

        // TODO : call on mic icon click
        checkAudioPermissions();

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(this);
        mRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "fr");
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

    private void checkAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    0);
        }
    }

    private View.OnClickListener mAddButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Word word = (Word) mSwipeCardAdapter.getItem(0);
            dicoWordList = mTinydb.getListString("wordsList");
            dicoImageList = mTinydb.getListString("imageList");

            if (word != null && !dicoWordList.contains(word.getWord())) {
                dicoWordList.add(word.getWord());
                dicoImageList.add(word.getImageUrl());
            }
            mTinydb.putListString("wordsList", (ArrayList<String>) dicoWordList);
            mTinydb.putListString("imageList", (ArrayList<String>) dicoImageList);

        }
    };

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        mRippleBackground.startRippleAnimation();
    }

    @Override
    public void onBeginningOfSpeech() {
        Toast.makeText(this, "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        mRippleBackground.stopRippleAnimation();
    }

    @Override
    public void onError(int errorCode) {
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_AUDIO));
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_CLIENT));
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS));
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_NETWORK));
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_NETWORK_TIMEOUT));
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_NO_MATCH));
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_RECOGNIZER_BUSY));
                break;
            case SpeechRecognizer.ERROR_SERVER:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_SERVER));
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_SPEECH_TIMEOUT));
                break;
            default:
                Log.d("DEBUG error", "Not understood");
                break;
        }
    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Log.d("DEBUG", String.valueOf(matches));

        if (matches != null) {
            int wordsNumber = matches.size();
            int wordsMissed = 0;

            for (int i = 0; i < matches.size(); i++) {
                String match = matches.get(i);
                Word word = (Word) mSwipeCardAdapter.getItem(0);
                if (match.toLowerCase().equals(word.getWord().toLowerCase())) {
                    nbSuccess++;

                    if (mSwipeCardAdapter.getCount() > 1) {
                        mSwipeFlingAdapterView.getTopCardListener().selectRight();
                    } else {
                        Intent mIntent = new Intent(WordsActivity.this, ResultActivity.class);
                        mIntent.putExtra("nbItem", nbItem);
                        mIntent.putExtra("nbSuccess", nbSuccess);
                        mIntent.putExtra("categoryName", mCategory.getCategoryName());
                        startActivity(mIntent);
                        finish();
                    }
                    return;
                } else {
                    wordsMissed++;
                }
            }

            if (wordsMissed == wordsNumber) {
                mSwipeFlingAdapterView.setWrong();
                mTryNumber++;
            }

            if (mTryNumber == 3) {
                mSwipeFlingAdapterView.getTopCardListener().selectRight();
                mTryNumber = 0;
            }
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSpeechRecognizer.destroy();
    }
}
