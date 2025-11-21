package com.example.todoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.model.Category;

public class CategoryAdd extends AppCompatActivity {

    private EditText edtCategoryName;
    private Button btnSaveCategory;

    private CategoryDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        edtCategoryName = findViewById(R.id.edtCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

        dataSource = new CategoryDataSource(this);
        dataSource.open();

        btnSaveCategory.setOnClickListener(v -> {
            String name = edtCategoryName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                return;
            }

            Category category = new Category(name);
            dataSource.addCategory(category);

            Toast.makeText(this, "Đã thêm danh mục!", Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
