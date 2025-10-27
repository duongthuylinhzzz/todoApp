package com.example.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class activity_add_task extends AppCompatActivity {

    EditText edtNgayGio;
    ImageButton btnChonNgay;
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        edtNgayGio = findViewById(R.id.edtNgayGio);
        btnChonNgay = findViewById(R.id.btnChonNgay);

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
    }
}
