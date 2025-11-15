package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.model.Task;

import java.util.List;

public class CompletedTask extends AppCompatActivity {

    private ListView lvCompletedTasks;
    private TextView tvNoCompleted;
    private TaskDataSource dataSource;
    private TaskAdapter taskAdapter;
    private List<Task> completedTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);

        lvCompletedTasks = findViewById(R.id.listViewCompleted);
        tvNoCompleted = findViewById(R.id.tvKhongCoTaskCompleted);

        dataSource = new TaskDataSource(this);
        dataSource.open();

        loadCompletedTasks();
    }

    private void loadCompletedTasks() {
        completedTaskList = dataSource.getTasksByDoneStatus(1); // only done tasks

        if (completedTaskList.isEmpty()) {
            tvNoCompleted.setText("Chưa có task hoàn thành nào");
            tvNoCompleted.setVisibility(android.view.View.VISIBLE);
        } else {
            tvNoCompleted.setVisibility(android.view.View.GONE);
        }

        taskAdapter = new TaskAdapter(this, completedTaskList);
        taskAdapter.setItemClick(task -> {
            if (task != null) {
                Intent intent = new Intent(CompletedTask.this, MainActivity2.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }
        });

        lvCompletedTasks.setAdapter(taskAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        loadCompletedTasks(); // reload nếu có task mới được đánh dấu done
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}
