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

        btnTaskCu.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, CompletedTask.class)));
    }
}
