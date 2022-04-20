package com.tea.teaproduction.Model;

public class CategoryModel {
    String GeadeCategoryId, GeadeCategoryName;

    public CategoryModel(String geadeCategoryId, String geadeCategoryName) {
        this.GeadeCategoryId = geadeCategoryId;
        this.GeadeCategoryName = geadeCategoryName;
    }

    public String getGeadeCategoryId() {
        return GeadeCategoryId;
    }

    public void setGeadeCategoryId(String geadeCategoryId) {
        GeadeCategoryId = geadeCategoryId;
    }

    public String getGeadeCategoryName() {
        return GeadeCategoryName;
    }

    public void setGeadeCategoryName(String geadeCategoryName) {
        GeadeCategoryName = geadeCategoryName;
    }
}
