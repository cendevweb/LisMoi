package com.lismoi.lis_moiapprendrelire.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Phil on 12/10/2017.
 */

public class WordsList {
    @SerializedName("words")
    public List<Word> words = null;

    public List<Word> getWords() {
        return words;
    }
}
