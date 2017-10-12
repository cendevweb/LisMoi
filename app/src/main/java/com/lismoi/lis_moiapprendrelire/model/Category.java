package com.lismoi.lis_moiapprendrelire.model;

/**
 * Created by Phil on 12/10/2017.
 */

public class Category {
    private int imageResource;
    private String categoryName;
    private int wordsInCategoryNumber;

    public Category(int imageResource, String categoryName, int wordsInCategoryNumber) {
        this.imageResource = imageResource;
        this.categoryName = categoryName;
        this.wordsInCategoryNumber = wordsInCategoryNumber;
    }

    public Category(String categoryName, int wordsInCategoryNumber) {
        this.categoryName = categoryName;
        this.wordsInCategoryNumber = wordsInCategoryNumber;
    }

    public Category() {

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
