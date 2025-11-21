package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CategoryDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("sCategoryName", category.getSCategoryName());
        return database.insert("categories", null, values);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Cursor cursor = database.query("categories", null, null, null, null, null, "sCategoryName ASC");

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setICategoryID(cursor.getInt(cursor.getColumnIndexOrThrow("iCategoryID")));
                category.setSCategoryName(cursor.getString(cursor.getColumnIndexOrThrow("sCategoryName")));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public void deleteCategory(int id) {
        database.delete("categories", "iCategoryID = ?", new String[]{String.valueOf(id)});
    }
}
