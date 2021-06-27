package com.streamliners.myecom.orders;

/**
 * Interface to allow OrdersHelper to communicate with OrdersActivity
 */
public interface OrdersCallbacksListener {

    void onError(String e);
    void onOrderAdded(int position);
    void onOrderChanged(int position);

}
