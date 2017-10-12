package com.lismoi.lis_moiapprendrelire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Phil on 12/10/2017.
 */

public class Word {

    @SerializedName("word")
    private String word;

    @SerializedName("word_tag")
    private String category;

    public Word(String word, String category) {
        this.word = word;
        this.category = category;
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }
}
