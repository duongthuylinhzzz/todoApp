package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.model.Task;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
        super(context ,0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }


        LinearLayout llItemTask = convertView.findViewById(R.id.llItemTask);
        TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);
        TextView tvTaskDueDate = convertView.findViewById(R.id.tvTaskDueDate);
        TextView tvTaskPriority = convertView.findViewById(R.id.tvTaskPriority);
        TextView tvTaskCategory = convertView.findViewById(R.id.tvTaskCategory);
        if (task != null) {
            tvTaskName.setText(task.getTittleTask());
            tvTaskDueDate.setText(task.getDate());
            tvTaskPriority.setText("Ưu tiên: " + task.getPriority());
            tvTaskCategory.setText("Loại: " + task.getCategory());

            llItemTask.setOnClickListener(v -> {
                itemClick.onItemClick(task);
            });
        }

        return convertView;
    }
}
interface ItemClick{
    void onItemClick(Task task);
}
