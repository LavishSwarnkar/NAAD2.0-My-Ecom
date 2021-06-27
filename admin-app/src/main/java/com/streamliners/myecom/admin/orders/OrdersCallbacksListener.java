package com.streamliners.myecom.admin.orders;

/**
 * Interface to allow OrdersHelper to communicate with OrdersActivity
 */
public interface OrdersCallbacksListener {

    void onError(String e);
    void onNewOrderReceived(int position);
    void onOrderChanged(int position);

}
