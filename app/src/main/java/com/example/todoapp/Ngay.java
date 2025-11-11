package com.example.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ngay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ngay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnAddTaskCircle = findViewById(R.id.btnAddTask);
        Button btnThemTask = findViewById(R.id.btnThemTask);
        Menu.setupMenu(this);

        btnThemTask.setOnClickListener(v -> {
            Intent intent = new Intent(Ngay.this, activity_add_task.class);
            startActivityForResult(intent, 100);
        });
        btnAddTaskCircle.setOnClickListener(v -> {
            Intent intent = new Intent(Ngay.this, activity_add_task.class);
            startActivityForResult(intent, 100);
        });
        Button btnSapXep = findViewById(R.id.btnSapXep);
        LinearLayout layoutSort = findViewById(R.id.layoutSort);
        View viewDim = findViewById(R.id.viewDim);
        Button btnCancelSort = findViewById(R.id.btnCancelSort);
        Button btnApplySort = findViewById(R.id.btnApplySort);

        btnSapXep.setOnClickListener(v -> {
            viewDim.setVisibility(View.VISIBLE);
            layoutSort.setVisibility(View.VISIBLE);
            layoutSort.animate().translationY(0).setDuration(250);
        });

        btnCancelSort.setOnClickListener(v -> {
            layoutSort.animate().translationY(layoutSort.getHeight()).setDuration(250)
                    .withEndAction(() -> {
                        layoutSort.setVisibility(View.GONE);
                        viewDim.setVisibility(View.GONE);
                    });
        });

        btnApplySort.setOnClickListener(v -> {
            layoutSort.animate().translationY(layoutSort.getHeight()).setDuration(250)
                    .withEndAction(() -> {
                        layoutSort.setVisibility(View.GONE);
                        viewDim.setVisibility(View.GONE);
                    });
        });

        viewDim.setOnClickListener(v -> {
            btnCancelSort.performClick(); // Bấm ra ngoài cũng đóng
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String ten = data.getStringExtra("ten");
            String mota = data.getStringExtra("mota");
            String ngaygio = data.getStringExtra("ngaygio");
            String uutien = data.getStringExtra("uutien");
            String danhmuc = data.getStringExtra("danhmuc");

            addTaskToLayout(ten, mota, ngaygio, uutien, danhmuc);
        }
    }
    private void addTaskToLayout(String ten, String mota, String ngaygio, String uutien, String danhmuc) {
        LinearLayout layoutTaskList = findViewById(R.id.layoutTaskList);
        LinearLayout layoutEmpty = findViewById(R.id.layoutEmpty);
        Button btnThemTask = findViewById(R.id.btnThemTask);
        Button btnAddTaskCircle = findViewById(R.id.btnAddTask);
        TextView tvTodayTitle = findViewById(R.id.tvTodayTitle);
        TextView tvCompletedTitle = findViewById(R.id.tvCompletedTitle);
        View dividerCompleted = findViewById(R.id.dividerCompleted);
        LinearLayout layoutCompleted = findViewById(R.id.layoutCompleted);

        tvTodayTitle.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        btnThemTask.setVisibility(View.GONE);
        btnAddTaskCircle.setVisibility(View.VISIBLE);
        layoutTaskList.setVisibility(View.VISIBLE);

        LinearLayout taskRow = new LinearLayout(this);
        taskRow.setOrientation(LinearLayout.HORIZONTAL);
        taskRow.setPadding(8, 8, 8, 8);
        taskRow.setGravity(android.view.Gravity.CENTER_VERTICAL);

        android.widget.RadioButton rb = new android.widget.RadioButton(this);
        taskRow.addView(rb);

        LinearLayout infoLayout = new LinearLayout(this);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        infoLayout.setPadding(8, 0, 0, 0);

        TextView tvTen = new TextView(this);
        tvTen.setText(ten);
        tvTen.setTextSize(15);
        tvTen.setTypeface(null, Typeface.BOLD);

        TextView tvInfo = new TextView(this);
        tvInfo.setText(ngaygio + " | " + danhmuc + " | Ưu tiên: " + uutien);
        tvInfo.setTextColor(Color.parseColor("#777777"));
        tvInfo.setTextSize(13);

        infoLayout.addView(tvTen);
        infoLayout.addView(tvInfo);
        taskRow.addView(infoLayout);

        TextView tvIndex = new TextView(this);
        int count = layoutTaskList.getChildCount() + 1;
        tvIndex.setText(String.valueOf(count));
        tvIndex.setTextColor(Color.parseColor("#999999"));
        tvIndex.setTextSize(14);
        tvIndex.setPadding(8, 0, 0, 0);
        taskRow.addView(tvIndex);

        layoutTaskList.addView(taskRow);

        rb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layoutTaskList.removeView(taskRow);


                tvTen.setPaintFlags(tvTen.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
                tvTen.setTextColor(Color.GRAY);
                tvInfo.setTextColor(Color.LTGRAY);


                rb.setVisibility(View.GONE);


                layoutCompleted.addView(taskRow);
                layoutCompleted.setVisibility(View.VISIBLE);
                tvCompletedTitle.setVisibility(View.VISIBLE);
                dividerCompleted.setVisibility(View.VISIBLE);
            }
        });
    }



}