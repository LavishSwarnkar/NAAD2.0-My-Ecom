package com.streamliners.myecom.controllers.databinders;

import android.content.Context;
import android.widget.Adapter;

import com.streamliners.myecom.MainActivity;
import com.streamliners.myecom.controllers.AdapterCallbacksListener;
import com.streamliners.myecom.databinding.ItemWbProductBinding;
import com.streamliners.myecom.models.Cart;
import com.streamliners.myecom.models.Product;

public class WBProductBinder {

    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public WBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    public void bind(ItemWbProductBinding b, Product product){

    }

}
