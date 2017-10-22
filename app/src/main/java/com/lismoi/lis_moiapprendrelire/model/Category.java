package com.lismoi.lis_moiapprendrelire.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phil on 12/10/2017.
 */

public class Category implements Serializable {

    private int imageResource;
    private String categoryName;
    private int wordsInCategoryNumber;
    private WordsList mWordsList;

    public Category() {

    }

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

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setWordsInCategoryNumber(int wordsInCategoryNumber) {
        this.wordsInCategoryNumber = wordsInCategoryNumber;
    }

    public int getImageRessource() {
        return imageResource;
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
