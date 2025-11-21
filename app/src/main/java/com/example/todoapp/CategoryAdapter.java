package com.example.todoapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.todoapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private List<Category> categoryList;
    private List<Category> fullList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = new ArrayList<>(categoryList);
        this.fullList = new ArrayList<>(categoryList);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryList.get(position).getICategoryID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category, null);
        }

        TextView tv = convertView.findViewById(R.id.tvName);
        tv.setText(categoryList.get(position).getSCategoryName());

        return convertView;
    }


    public void filter(String keyword) {
        categoryList.clear();
        if (keyword.isEmpty()) {
            categoryList.addAll(fullList);
        } else {
            keyword = keyword.toLowerCase();
            for (Category c : fullList) {
                if (c.getSCategoryName().toLowerCase().contains(keyword)) {
                    categoryList.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}
