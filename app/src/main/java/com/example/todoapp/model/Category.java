
package com.example.todoapp.model;

public class Category {
    private int iCategoryID;
    private String sCategoryName;

    public Category() {}

    public Category(String sCategoryName) {
        this.sCategoryName = sCategoryName;
    }

    public int getICategoryID() {
        return iCategoryID;
    }

    public void setICategoryID(int iCategoryID) {
        this.iCategoryID = iCategoryID;
    }

    public String getSCategoryName() {
        return sCategoryName;
    }

    public void setSCategoryName(String sCategoryName) {
        this.sCategoryName = sCategoryName;
    }
}
