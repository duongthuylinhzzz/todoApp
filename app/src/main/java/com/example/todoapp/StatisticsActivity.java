package com.example.todoapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private TaskDataSource dataSource;
    private TextView tvTotalTasks, tvCompletedTasks;
    private ListView lvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvTotalTasks = findViewById(R.id.tvTotalTasks);
        tvCompletedTasks = findViewById(R.id.tvCompletedTasks);
        lvTasks = findViewById(R.id.lvTasks);

        dataSource = new TaskDataSource(this);
        dataSource.open();

        loadStatistics();
    }

    private void loadStatistics() {
        List<Task> allTasks = dataSource.getAllTasks();
        List<Task> completedTasks = dataSource.getTasksByDoneStatus(1);

        tvTotalTasks.setText("Tổng số công việc: " + allTasks.size());
        tvCompletedTasks.setText("Công việc đã hoàn thành: " + completedTasks.size());

        // Hiển thị tên task
        List<String> taskNames = new ArrayList<>();
        for (Task t : allTasks) {
            taskNames.add(t.getTittleTask() + (t.isDone() ? " (Đã hoàn thành)" : ""));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                taskNames
        );
        lvTasks.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}
