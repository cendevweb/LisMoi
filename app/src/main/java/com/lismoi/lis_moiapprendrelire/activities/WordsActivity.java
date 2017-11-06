package com.lismoi.lis_moiapprendrelire.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.RippleBackground;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.adapters.SwipeCardAdapter;
import com.lismoi.lis_moiapprendrelire.cardsStack.SwingFlingAdapterView;
import com.lismoi.lis_moiapprendrelire.model.Category;
import com.lismoi.lis_moiapprendrelire.model.Word;
import com.lismoi.lis_moiapprendrelire.model.WordsList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class WordsActivity extends AppCompatActivity implements RecognitionListener {

    private LinearLayout mAddButton;
    private List<String> mDicoWordList = new ArrayList();
    private List<String> mDicoImageList = new ArrayList();
    private ImageView mMicrophoneButton;
    private ImageView mDictionaryIcon;
    private TextView mDictionaryText;
    private RippleBackground mRippleBackground;

    private SwipeCardAdapter mSwipeCardAdapter;
    private SwingFlingAdapterView mSwipeFlingAdapterView;
    private WordsList mWordsList;
    private TinyDB mTinydb;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mRecognizerIntent;
    private Category mCategory;
    private int mTryNumber = 0;
    private double nbItem;
    private double nbSuccess = 0;
    private ArrayList<HashMap<String, Boolean>> mWordsResult = new ArrayList<>();

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
        mAddButton = (LinearLayout) findViewById(R.id.activity_word_add_button);
        mRippleBackground = (RippleBackground) findViewById(R.id.activity_words_ripple_background);
        mDictionaryText = (TextView) findViewById(R.id.activity_word_dictionary_text);
        mDictionaryIcon = (ImageView) findViewById(R.id.activity_word_dictionary_icon);

        mAddButton.setOnClickListener(mAddButtonListener);

        getSupportActionBar().setTitle(mCategory.getCategoryName());

        mWordsList = mCategory.getWordsList();

        mSwipeCardAdapter = new SwipeCardAdapter(this, getLayoutInflater(), mWordsList);
        mSwipeFlingAdapterView.setAdapter(mSwipeCardAdapter);

        checkFirstWordInDictionary();

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
            mDicoWordList = mTinydb.getListString("wordsList");
            mDicoImageList = mTinydb.getListString("imageList");

            if (word != null && !mDicoWordList.contains(word.getWord())) {
                mDicoWordList.add(word.getWord());
                mDicoImageList.add(word.getImageUrl());
                mDictionaryText.setText(getString(R.string.added_to_dictionnary));
                mDictionaryIcon.setImageDrawable(ContextCompat.getDrawable(WordsActivity.this, R.drawable.ic_check));
            } else {
                mDictionaryText.setText(getString(R.string.add_to_dictionnary));
                mDictionaryIcon.setImageDrawable(ContextCompat.getDrawable(WordsActivity.this, R.drawable.ic_dictionary));
            }

            mTinydb.putListString("wordsList", (ArrayList<String>) mDicoWordList);
            mTinydb.putListString("imageList", (ArrayList<String>) mDicoImageList);

        }
    };

    private void checkFirstWordInDictionary() {
        Word word = (Word) mSwipeCardAdapter.getItem(0);
        mDicoWordList = mTinydb.getListString("wordsList");
        mDicoImageList = mTinydb.getListString("imageList");

        mDictionaryText.setText(mDicoWordList.contains(word.getWord()) ?
                getString(R.string.added_to_dictionnary) : getString(R.string.add_to_dictionnary));

        mDictionaryIcon.setImageDrawable(ContextCompat.getDrawable(this, mDicoWordList.contains(word.getWord()) ?
                R.drawable.ic_check : R.drawable.ic_dictionary));
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
    }

    @Override
    public void onBeginningOfSpeech() {
        mRippleBackground.startRippleAnimation();
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
        mSwipeFlingAdapterView.setWrong();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);

        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_AUDIO));
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                Log.d("DEBUG error", String.valueOf(SpeechRecognizer.ERROR_CLIENT));
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                checkAudioPermissions();
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

        Word word = null;

        if (matches != null) {
            int wordsNumber = matches.size();
            int wordsMissedNumber = 0;

            for (int i = 0; i < matches.size(); i++) {
                String match = matches.get(i);
                word = (Word) mSwipeCardAdapter.getItem(0);
                if (match.toLowerCase().equals(word.getWord().toLowerCase())) {
                    nbSuccess++;

                    if (mSwipeCardAdapter.getCount() > 1) {
                        mSwipeFlingAdapterView.getTopCardListener().selectRight();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkFirstWordInDictionary();
                            }
                        }, 500);
                        HashMap<String, Boolean> wordMissed = new HashMap<>();
                        wordMissed.put(word.getWord(), Boolean.TRUE);
                        mWordsResult.add(wordMissed);
                    } else {
                        startResultActivity();
                    }
                    return;
                } else {
                    wordsMissedNumber++;
                }
            }

            if (wordsMissedNumber == wordsNumber) {
                mSwipeFlingAdapterView.setWrong();
                mTryNumber++;
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }

            if (mTryNumber == 3) {
                mSwipeFlingAdapterView.getTopCardListener().selectRight();
                mTryNumber = 0;
                HashMap<String, Boolean> wordMissed = new HashMap<>();
                wordMissed.put(word.getWord(), Boolean.FALSE);
                mWordsResult.add(wordMissed);

                if (mSwipeCardAdapter.getCount() <= 1) {
                    startResultActivity();
                }
            }
        }
    }

    private void startResultActivity() {
        Intent mIntent = new Intent(WordsActivity.this, ResultActivity.class);
        mIntent.putExtra("nbItem", nbItem);
        mIntent.putExtra("nbSuccess", nbSuccess);
        mIntent.putExtra("category", mCategory);
        mIntent.putExtra("wordsResult", mWordsResult);
        startActivity(mIntent);
        finish();
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
