package com.streamliners.myecom.orders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamliners.myecom.models.Order;

import java.util.List;

/**
 * Adapter class for RecyclerView to show List of Orders
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private OrderViewBinder binder;
    private List<Order> orders;

    public OrdersAdapter(List<Order> orders){
        this.orders = orders;
        binder = new OrderViewBinder();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        binder.bind(holder.b, order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
