package com.streamliners.myecom.dialogs;

import android.content.Context;

import com.streamliners.myecom.models.Cart;
import com.streamliners.myecom.models.Product;

public class WeightPickerDialog {

    private Context context;

    public WeightPickerDialog(Context context, Cart cart){
        this.context = context;
    }

    public void show(Product product, WeightPickerCompleteListener listener){

    }

    public interface WeightPickerCompleteListener{
        void onCompleted();
    }

}
