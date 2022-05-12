package com.example.shoppingapp.data;

import android.view.View;

public interface ClickListener {
    void onDeleteClicked(View v,int position);
    void onEditClicked(View v,int position);
}
