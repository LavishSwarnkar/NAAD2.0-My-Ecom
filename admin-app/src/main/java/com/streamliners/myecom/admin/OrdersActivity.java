package com.streamliners.myecom.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.streamliners.myecom.admin.orders.OrdersAdapter;
import com.streamliners.myecom.admin.orders.OrdersCallbacksListener;
import com.streamliners.myecom.admin.orders.OrdersHelper;

public class OrdersActivity extends AppCompatActivity {

    private OrdersHelper ordersHelper;
    private OrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        setup();
    }

    private void setup() {
        setupOrdersHelper();
        setupOrdersAdapter();
    }

    private void setupOrdersHelper() {
        ordersHelper = new OrdersHelper();
        ordersHelper.init(new OrdersCallbacksListener() {

            @Override
            public void onNewOrderReceived(int position) {
                adapter.notifyItemInserted(position);
            }

            @Override
            public void onOrderChanged(int position) {
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onError(String e) {

            }

        });
    }

    private void setupOrdersAdapter() {
        //TODO 2.4 : Setup the OrdersAdapter
    }

}