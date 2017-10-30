package com.lismoi.lis_moiapprendrelire.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Phil on 12/10/2017.
 */

public class Category implements Serializable {

    private String imageUrl;
    private String categoryName;
    private int wordsInCategoryNumber;
    private WordsList mWordsList;

    public ArrayList<String> getCategoryWords() {
        ArrayList<String> wordsList = new ArrayList<>();

        for (Word word : mWordsList.getWordList()) {
            wordsList.add(word.getWord());
        }

        return wordsList;
    }

    public WordsList getWordsList() {
        return mWordsList;
    }

    public void setWordsList(WordsList wordsList) {
        this.mWordsList = wordsList;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setWordsInCategoryNumber(int wordsInCategoryNumber) {
        this.wordsInCategoryNumber = wordsInCategoryNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getWordsInCategoryNumber() {
        return wordsInCategoryNumber;
    }

    public void updateWordsNumber() {
        wordsInCategoryNumber++;
    }
}
