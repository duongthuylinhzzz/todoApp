package com.example.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên và phiên bản của cơ sở dữ liệu
    public static final String DATABASE_NAME = "tasks_app.db";
    public static final int DATABASE_VERSION = 1;

    // Định nghĩa tên bảng và các cột
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DUE_DATE = "dueDate";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_IS_DONE = "is_done";
    // Câu lệnh SQL để tạo bảng
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DUE_DATE + " TEXT, " +
                    COLUMN_PRIORITY + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_IS_DONE + " INTEGER DEFAULT 0);";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực thi câu lệnh tạo bảng khi cơ sở dữ liệu được tạo lần đầu
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu nó tồn tại và tạo lại
        // Trong ứng dụng thực tế, bạn cần một chiến lược di chuyển dữ liệu tốt hơn
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}
