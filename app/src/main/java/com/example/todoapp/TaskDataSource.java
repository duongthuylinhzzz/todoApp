package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;import com.example.todoapp.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Mảng các cột bạn muốn lấy từ bảng
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_DUE_DATE,
            DatabaseHelper.COLUMN_PRIORITY,
            DatabaseHelper.COLUMN_CATEGORY,
    };

    public TaskDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Mở kết nối đến database
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối đến database
    public void close() {
        dbHelper.close();
    }

    // CREATE: Thêm một Task mới
    public void addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, task.getTittleTask());
        values.put(DatabaseHelper.COLUMN_DUE_DATE, task.getDate());
        values.put(DatabaseHelper.COLUMN_PRIORITY, task.getPriority());
        values.put(DatabaseHelper.COLUMN_CATEGORY, task.getCategory());

        database.insert(DatabaseHelper.TABLE_TASKS, null, values);
    }

    // READ: Lấy tất cả các Task
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    // UPDATE: Cập nhật một Task
    public void updateTask(Task task) {
        long id = task.getId();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, task.getTittleTask());
        // Thêm các cột khác bạn muốn cập nhật...

        database.update(DatabaseHelper.TABLE_TASKS, values, DatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    // DELETE: Xóa một Task
    public void deleteTask(Task task) {
        long id = task.getId();
        database.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    // Helper method để chuyển đổi dữ liệu từ Cursor sang đối tượng Task
    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTittleTask(cursor.getString(1));
        task.setDate(cursor.getString(2));
        task.setPriority(cursor.getString(3));
        task.setCategory(cursor.getString(4));
        return task;
    }
}
