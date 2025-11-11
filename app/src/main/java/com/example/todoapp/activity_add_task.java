package com.example.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class activity_add_task extends AppCompatActivity {

    EditText edtNgayGio, edtTenCongViec, edtMoTa, edtMucUuTien, edtDanhMuc;
    ImageButton btnChonNgay;
    Calendar calendar;
    Button btnThem, btnHuy;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    private TaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        dataSource = new TaskDataSource(this);
        dataSource.open();

        edtNgayGio = findViewById(R.id.edtNgayGio);
        btnChonNgay = findViewById(R.id.btnChonNgay);
        btnThem = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
        edtTenCongViec = findViewById(R.id.edtTenCongViec);
        edtDanhMuc = findViewById(R.id.edtDanhMuc);
        edtMucUuTien = findViewById(R.id.edtMucUuTien);

        calendar = Calendar.getInstance();

        btnChonNgay.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // --- Chọn NGÀY ---
            DatePickerDialog dateDialog = new DatePickerDialog(
                    this,
                    (view, y, m, d) -> {
                        calendar.set(y, m, d);

                        // --- Chọn GIỜ ---
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timeDialog = new TimePickerDialog(
                                this,
                                (timeView, h, min) -> {
                                    calendar.set(Calendar.HOUR_OF_DAY, h);
                                    calendar.set(Calendar.MINUTE, min);

                                    // Hiển thị kết quả dd/MM/yyyy HH:mm
                                    edtNgayGio.setText(sdf.format(calendar.getTime()));
                                },
                                hour, minute, true // true = định dạng 24h
                        );

                        timeDialog.show();
                    },
                    year, month, day
            );

            dateDialog.show();
        });
        edtNgayGio.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String input = edtNgayGio.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                sdf.setLenient(false);

                try {
                    sdf.parse(input);
                } catch (Exception e) {
                    edtNgayGio.setError("Sai định dạng! Ví dụ: 28/10/2025 14:30");
                }
            }
        });
        btnThem.setOnClickListener(v -> {
            String date = edtNgayGio.getText().toString();
            String tittleTask = edtTenCongViec.getText().toString();
            String priority = edtMucUuTien.getText().toString();
            String category = edtDanhMuc.getText().toString();

            Task task = new Task(date, tittleTask, priority, category);
            addTask(task);
            startActivity(new Intent(activity_add_task.this, MainActivity.class));
        });

        btnHuy.setOnClickListener(v -> {
            startActivity(new Intent(activity_add_task.this, MainActivity.class));
        });
    }
    void addTask(Task task) {
        dataSource.addTask(task);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}
