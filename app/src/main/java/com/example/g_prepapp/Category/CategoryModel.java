package com.example.g_prepapp.Category;

public class CategoryModel {

   private int catId;
   private String catName;

    public CategoryModel(int catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                '}';
    }
}
