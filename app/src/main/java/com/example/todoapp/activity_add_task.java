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

            DatePickerDialog dateDialog = new DatePickerDialog(
                    this,
                    (view, y, m, d) -> {
                        calendar.set(y, m, d);


                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timeDialog = new TimePickerDialog(
                                this,
                                (timeView, h, min) -> {
                                    calendar.set(Calendar.HOUR_OF_DAY, h);
                                    calendar.set(Calendar.MINUTE, min);


                                    edtNgayGio.setText(sdf.format(calendar.getTime()));
                                },
                                hour, minute, true
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
        EditText edtTenCongViec = findViewById(R.id.edtTenCongViec);
        EditText edtMoTa = findViewById(R.id.edtMoTa);
        EditText edtNgayGio = findViewById(R.id.edtNgayGio);
        EditText edtMucUuTien = findViewById(R.id.edtMucUuTien);
        EditText edtDanhMuc = findViewById(R.id.edtDanhMuc);

        Button btnLuu = findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(v -> {

            Intent resultIntent = new Intent();
            resultIntent.putExtra("ten", edtTenCongViec.getText().toString());
            resultIntent.putExtra("mota", edtMoTa.getText().toString());
            resultIntent.putExtra("ngaygio", edtNgayGio.getText().toString());
            resultIntent.putExtra("uutien", edtMucUuTien.getText().toString());
            resultIntent.putExtra("danhmuc", edtDanhMuc.getText().toString());

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Button btnHuy = findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(v -> finish());
    }
}
