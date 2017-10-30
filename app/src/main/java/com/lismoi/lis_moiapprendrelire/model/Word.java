package com.lismoi.lis_moiapprendrelire.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phil on 12/10/2017.
 */

public class Word implements Serializable {

    @SerializedName("word")
    private String word;

    @SerializedName("word_tag")
    private String category;

    @SerializedName("word_image_url")
    private String imageUrl;

    public Word(String word, String category, String imageUrl) {
        this.word = word;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
