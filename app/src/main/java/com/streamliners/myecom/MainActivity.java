package com.streamliners.myecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.streamliners.myecom.controllers.AdapterCallbacksListener;
import com.streamliners.myecom.controllers.ProductsAdapter;
import com.streamliners.myecom.databinding.ActivityMainBinding;
import com.streamliners.myecom.models.Cart;
import com.streamliners.myecom.tmp.ProductsHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ProductsAdapter adapter;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        showUserDetails();

        //setupAdapter();
    }

    private void showUserDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Login successful!")
                    .setMessage(new Gson().toJson(user))
                    .show();
        }
    }

    private void setupAdapter() {
        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int itemPosition) {
                updateCartSummary();
                adapter.notifyItemChanged(itemPosition);
            }
        };

        adapter = new ProductsAdapter(this
                , ProductsHelper.getProducts()
                , cart
                , listener);

        b.list.setLayoutManager(new LinearLayoutManager(this));

        b.list.setAdapter(adapter);
    }

    private void updateCartSummary() {

    }
}