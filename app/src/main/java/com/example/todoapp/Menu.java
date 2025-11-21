package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class Menu {
    public static void setupMenu(Activity activity) {
        Button btnNgay = activity.findViewById(R.id.ngay);
        Button btnThem = activity.findViewById(R.id.them);
        Button btnTaskCu = activity.findViewById(R.id.lich);
        Button btnCaiDat = activity.findViewById(R.id.caiDat);
        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(activity, TrangChu.class);
            activity.startActivity(intent);
                });

        // Mở CompletedTask khi bấm "Task cũ"
        btnTaskCu.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, CompletedTask.class)));

        // Mở SettingsActivity khi bấm "Cài đặt"
        btnCaiDat.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, SettingsActivity.class)));

        // Mở Ngay activity khi bấm "Ngày"
        btnNgay.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, TrangChu.class)));
    }
}
