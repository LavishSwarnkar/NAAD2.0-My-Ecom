package com.streamliners.myecom.controllers.databinders;

import android.content.Context;

import com.streamliners.myecom.controllers.AdapterCallbacksListener;
import com.streamliners.myecom.databinding.ItemVbProductBinding;
import com.streamliners.myecom.databinding.ItemWbProductBinding;
import com.streamliners.myecom.models.Cart;
import com.streamliners.myecom.models.Product;

public class VBProductBinder {

    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public VBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    public void bind(ItemVbProductBinding b, Product product){

    }

}
