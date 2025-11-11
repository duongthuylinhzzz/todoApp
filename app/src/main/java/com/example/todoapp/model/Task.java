package com.example.todoapp.model;


import java.io.Serializable;

public class Task implements Serializable {
    public int id;
    public String date;
    public String tittleTask;
    public String priority;
    public String category;

    public Task() {
    }

    public Task(String date, String tittleTask, String priority, String category) {
        this.date = date;
        this.tittleTask = tittleTask;
        this.priority = priority;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTittleTask() {
        return tittleTask;
    }

    public void setTittleTask(String tittleTask) {
        this.tittleTask = tittleTask;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
