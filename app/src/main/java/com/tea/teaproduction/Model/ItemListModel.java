package com.tea.teaproduction.Model;

public class ItemListModel {
    String ItemId, ItemName;

    public ItemListModel(String ItemId, String ItemName) {
        this.ItemId = ItemId;
        this.ItemName = ItemName;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String ItemId) {
        ItemId = ItemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        ItemName = ItemName;
    }
}
