package com.example.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class activity_add_task extends AppCompatActivity {

    EditText edtNgayGio, edtTenCongViec, edtMoTa, edtMucUuTien, edtDanhMuc;
    ImageButton btnChonNgay;
    Button btnLuu, btnHuy;

    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    private TaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        // mở database
        dataSource = new TaskDataSource(this);
        dataSource.open();

        // ánh xạ view
        edtNgayGio = findViewById(R.id.edtNgayGio);
        edtTenCongViec = findViewById(R.id.edtTenCongViec);
        edtMoTa = findViewById(R.id.edtMoTa);
        edtMucUuTien = findViewById(R.id.edtMucUuTien);
        edtDanhMuc = findViewById(R.id.edtDanhMuc);

        btnChonNgay = findViewById(R.id.btnChonNgay);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        calendar = Calendar.getInstance();


        // ---------------------- CHỌN NGÀY GIỜ ----------------------
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
                try {
                    sdf.setLenient(false);
                    sdf.parse(edtNgayGio.getText().toString().trim());
                } catch (Exception e) {
                    edtNgayGio.setError("Sai định dạng! Ví dụ: 28/10/2025 14:30");
                }
            }
        });


        // ---------------------- NÚT LƯU ----------------------
        btnLuu.setOnClickListener(v -> {

            String date = edtNgayGio.getText().toString().trim();
            String title = edtTenCongViec.getText().toString().trim();
            String desc = edtMoTa.getText().toString().trim();
            String priority = edtMucUuTien.getText().toString().trim();
            String category = edtDanhMuc.getText().toString().trim();

            // validate
            if (title.isEmpty()) {
                edtTenCongViec.setError("Không được để trống");
                return;
            }
            if (date.isEmpty()) {
                edtNgayGio.setError("Chọn hoặc nhập ngày giờ");
                return;
            }

            // tạo đối tượng Task
            Task task = new Task(date, title, priority, category);

            // lưu DB
            dataSource.addTask(task);

            Toast.makeText(this, "Đã lưu!", Toast.LENGTH_SHORT).show();

            // quay lại màn hình chính
            finish();
        });


        // ---------------------- NÚT HỦY ----------------------
        btnHuy.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}
