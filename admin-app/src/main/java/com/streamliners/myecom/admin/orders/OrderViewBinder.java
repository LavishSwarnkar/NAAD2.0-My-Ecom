package com.streamliners.myecom.admin.orders;

import com.streamliners.myecom.admin.databinding.ItemOrderBinding;
import com.streamliners.myecom.models.Order;

/**
 * Binder class to bind the order data with its view
 */
public class OrderViewBinder {

    public void bind(ItemOrderBinding b, Order order){
        //TODO 2.2 : Bind the order data with its views

        /* Note :
         * When order status is updated, update the order on Firestore & notify the User
         *      - Use the FCMSender used in User app
         *      - Make sure to implement FCMReceiverService in User app
         */
    }

}
