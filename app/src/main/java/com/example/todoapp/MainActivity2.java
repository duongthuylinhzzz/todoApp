package com.example.todoapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.databinding.ActivityMain2Binding;
import com.example.todoapp.model.Task;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    private Boolean isOpenOption = false;
    private TaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataSource = new TaskDataSource(this);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        if (task != null) {
            updateUi(task);

            // Ẩn nút Finish nếu task đã hoàn thành
            if (task.isDone()) {
                binding.btnFinish.setVisibility(View.GONE);
            } else {
                binding.btnFinish.setVisibility(View.VISIBLE);
            }
        }
        binding.iconBack.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity2.this, MainActivity.class));
        });
        binding.iconOption.setOnClickListener(v -> {
            isOpenOption = !isOpenOption;
            Log.d("TAG", "onCreate: " + isOpenOption);
            if (isOpenOption) {
                binding.llOption.setVisibility(View.VISIBLE);
            } else {
                binding.llOption.setVisibility(View.GONE);
            }
        });
        binding.btnFinish.setOnClickListener(v -> {
            if (task != null) {
                showDialogFinish(task);
            }
        });
        binding.btnUpdate.setOnClickListener(v -> {
            if (task != null) {
                showDialogEdit(task);
            }
        });
        binding.btnDelete.setOnClickListener(v -> {
            if (task != null) {
                showDialogDelete(task);
            }
        });
    }

    private void showDialogEdit(Task task) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_task);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        EditText edtTittleTask = dialog.findViewById(R.id.edtEditTaskTittle);
        EditText edtDateTask = dialog.findViewById(R.id.edtEditTaskDate);
        EditText edtPriority = dialog.findViewById(R.id.edtEditTaskPriority);
        EditText edtCategoryTask = dialog.findViewById(R.id.edtEditTaskCategory);
        Button cancel = dialog.findViewById(R.id.btnCancel);
        Button save = dialog.findViewById(R.id.btnSave);

        if (task != null) {
            edtTittleTask.setText(task.tittleTask);
            edtCategoryTask.setText(task.category);
            edtPriority.setText(task.priority);
            edtDateTask.setText(task.date);
        }

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        save.setOnClickListener(v -> {
            if (task != null) {
                task.setTittleTask(edtTittleTask.getText().toString());
                task.setDate(edtDateTask.getText().toString());
                task.setPriority(edtPriority.getText().toString());
                task.setCategory(edtCategoryTask.getText().toString());
            }

//            Log.d("TAG", "showDialogEdit: "+edtTittleTask.getText().toString());
            dataSource.open();
            dataSource.updateTask(task);
            dataSource.close();
            updateUi(task);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void showDialogDelete(Task task) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_task);
        dialog.setCancelable(true);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnDeleteCancel);

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnConfirm.setOnClickListener(v -> {
            dataSource.open();
            dataSource.deleteTask(task);
            dataSource.close();
            finish();
        });
        dialog.show();
    }
    private void showDialogFinish(Task task) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_finish_task);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        Button btnConfirm = dialog.findViewById(R.id.btnConfirmFinish);
        Button btnCancel = dialog.findViewById(R.id.btnCancelFinish);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            // 1. Đánh dấu hoàn thành
            task.setDone(true);

            // 2. Cập nhật DB
            dataSource.open();
            dataSource.updateTask(task);
            dataSource.close();

            // 3. Chuyển sang activity khác
            Intent intent = new Intent(MainActivity2.this, CompletedTask.class);
            intent.putExtra("task", task);
            startActivity(intent);

            dialog.dismiss();
            finish(); // optional
        });

        dialog.show();
    }

    private void updateUi(Task task) {
        binding.tvTitleTask.setText(task.getTittleTask());
        binding.tvDate.setText(task.getDate());
        binding.tvCategory.setText(task.getCategory());
        binding.tvPriority.setText(task.getPriority());
    }

}