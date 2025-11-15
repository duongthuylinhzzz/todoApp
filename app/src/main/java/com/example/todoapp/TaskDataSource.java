package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todoapp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_DUE_DATE,
            DatabaseHelper.COLUMN_PRIORITY,
            DatabaseHelper.COLUMN_CATEGORY,
            DatabaseHelper.COLUMN_IS_DONE
    };

    public TaskDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // CREATE
    public void addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, task.getTittleTask());
        values.put(DatabaseHelper.COLUMN_DUE_DATE, task.getDate());
        values.put(DatabaseHelper.COLUMN_PRIORITY, task.getPriority());
        values.put(DatabaseHelper.COLUMN_CATEGORY, task.getCategory());
        values.put(DatabaseHelper.COLUMN_IS_DONE, task.isDone() ? 1 : 0);

        database.insert(DatabaseHelper.TABLE_TASKS, null, values);
    }

    // READ
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_TASKS,
                allColumns,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_ID + " DESC"
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    // UPDATE
    public void updateTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, task.getTittleTask());
        values.put(DatabaseHelper.COLUMN_DUE_DATE, task.getDate());
        values.put(DatabaseHelper.COLUMN_PRIORITY, task.getPriority());
        values.put(DatabaseHelper.COLUMN_CATEGORY, task.getCategory());
        values.put(DatabaseHelper.COLUMN_IS_DONE, task.isDone() ? 1 : 0);

        database.update(
                DatabaseHelper.TABLE_TASKS,
                values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())}
        );
    }

    // DELETE
    public void deleteTask(Task task) {
        database.delete(
                DatabaseHelper.TABLE_TASKS,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())}
        );
    }
    public List<Task> getTasksByDoneStatus(int doneStatus) {
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_TASKS,
                allColumns,
                DatabaseHelper.COLUMN_IS_DONE + " = ?",
                new String[]{String.valueOf(doneStatus)},
                null, null, null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tasks.add(cursorToTask(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return tasks;
    }

    // Convert cursor → Task
    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTittleTask(cursor.getString(1));
        task.setDate(cursor.getString(2));
        task.setPriority(cursor.getString(3));
        task.setCategory(cursor.getString(4));
        task.setDone(cursor.getInt(5) == 1);

        return task;
    }
}
