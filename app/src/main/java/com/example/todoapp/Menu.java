package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class Menu {
    public static void setupMenu(Activity activity) {
        Button btnNgay = activity.findViewById(R.id.btnNgay);
        Button btnThem = activity.findViewById(R.id.btnThem);
        Button btnLich = activity.findViewById(R.id.btnLich);
        Button btnCaiDat = activity.findViewById(R.id.btnCaiDat);

        btnNgay.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, Ngay.class)));

    }
}
