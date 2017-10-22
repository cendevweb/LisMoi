package com.lismoi.lis_moiapprendrelire.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phil on 12/10/2017.
 */

public class WordsList implements Serializable {
    @SerializedName("words")
    private List<Word> wordList = new ArrayList<>();

    public void addWordToList(Word word) {
        this.wordList.add(word);
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public List<Word> getWordList() {
        return wordList;
    }
}
