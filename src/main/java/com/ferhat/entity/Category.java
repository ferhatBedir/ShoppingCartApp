package com.ferhat.entity;

public class Category {

    private String categoryName;
    private Category rootCategory;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String categoryName, Category rootCategory) {
        this.categoryName = categoryName;
        this.rootCategory = rootCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(Category rootCategory) {
        this.rootCategory = rootCategory;
    }
}
