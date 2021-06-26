package com.streamliners.myecom.admin.orders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamliners.myecom.admin.databinding.ItemOrderBinding;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public ItemOrderBinding b;

    public OrderViewHolder(@NonNull ItemOrderBinding b) {
        super(b.getRoot());
        this.b = b;
    }
}
