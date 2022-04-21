package com.tea.teaproduction.Model;

public class CategoryListModel {
    String ItemCategoryId, CategoryName;

    public CategoryListModel(String ItemCategoryId, String CategoryName) {
        this.ItemCategoryId = ItemCategoryId;
        this.CategoryName = CategoryName;
    }

    public String getItemCategoryId() {
        return ItemCategoryId;
    }

    public void setItemCategoryId(String ItemCategoryId) {
        ItemCategoryId = ItemCategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        CategoryName = CategoryName;
    }
}
