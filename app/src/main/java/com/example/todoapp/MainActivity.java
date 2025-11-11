package com.example.todoapp;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapp.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tvKhongCoTask;
    ListView lvTaskCV;
    Button btnNgay,btnThem, btnLich, btnCaidat;
    private TaskDataSource dataSource;
    List<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;

    private ItemClick itemClick ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvKhongCoTask = findViewById(R.id.tvKhongCoTask);
        lvTaskCV = findViewById(R.id.listView);
        btnNgay = findViewById(R.id.ngay);
        btnThem = findViewById(R.id.them);
        btnLich = findViewById(R.id.lich);
        btnCaidat = findViewById(R.id.caiDat);

        dataSource = new TaskDataSource(this);
        dataSource.open();

        loadTasksFromDatabase();

        if (taskList.isEmpty()){
            tvKhongCoTask.setVisibility(VISIBLE);
        }else{
            tvKhongCoTask.setVisibility(View.GONE);
        }
        itemClick = task -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);

            intent.putExtra("task", task);
            startActivity(intent);
        };
    }
    public void moActivityNgay(View view) {
        Intent intent = new Intent(MainActivity.this, Ngay.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        loadTasksFromDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    private void loadTasksFromDatabase() {
        taskList = dataSource.getAllTasks();
        taskAdapter = new TaskAdapter(this, taskList);
        taskAdapter.setItemClick(itemClick);
        lvTaskCV.setAdapter(taskAdapter);
    }
}