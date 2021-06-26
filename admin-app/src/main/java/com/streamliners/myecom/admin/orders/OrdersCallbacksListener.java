package com.streamliners.myecom.admin.orders;

public interface OrdersCallbacksListener {

    void onError(String e);
    void onNewOrderReceived(int position);
    void onOrderChanged(int position);

}
