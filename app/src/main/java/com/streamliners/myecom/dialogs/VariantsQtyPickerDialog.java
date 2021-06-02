package com.streamliners.myecom.dialogs;

import android.content.Context;

import com.streamliners.myecom.models.Cart;
import com.streamliners.myecom.models.Product;

public class VariantsQtyPickerDialog {

    private Context context;

    public VariantsQtyPickerDialog(Context context, Cart cart){
        this.context = context;
    }

    public void show(Product product, VariantsQtyPickerCompleteListener listener){

    }

    public interface VariantsQtyPickerCompleteListener {
        void onCompleted();
    }

}
