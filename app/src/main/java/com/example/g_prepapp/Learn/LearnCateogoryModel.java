package com.example.g_prepapp.Learn;

public class LearnCateogoryModel
{

    String category;

    public LearnCateogoryModel(String category) {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "LearnModel{" +
                "category='" + category + '\'' +
                '}';
    }
}
