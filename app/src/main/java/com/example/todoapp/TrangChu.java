package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity {

    private EditText edtSearchCategory;
    private GridView gridCategories;
    private CategoryAdapter adapter;
    private CategoryDataSource dataSource;
    private List<Category> fullList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        edtSearchCategory = findViewById(R.id.edtSearchCategory);
        gridCategories = findViewById(R.id.gridCategories);

        dataSource = new CategoryDataSource(this);
        dataSource.open();

        fullList = dataSource.getAllCategories();
        adapter = new CategoryAdapter(this, fullList);
        gridCategories.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAddCategory);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, CategoryAdd.class);
            startActivityForResult(intent, 100);
        });
        edtSearchCategory.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            fullList = dataSource.getAllCategories();
            adapter = new CategoryAdapter(this, fullList);
            gridCategories.setAdapter(adapter);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
