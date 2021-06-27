package com.streamliners.myecom.orders;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.streamliners.myecom.models.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fetches orders & listens for changes in "orders" collection
 */
public class OrdersHelper {

    public List<Order> orders;
    private Map<String, Integer> orderIdIndexMap;

    private OrdersCallbacksListener listener;
    private CollectionReference ordersCollRef;

    /**
     * Initialize variables
     * @param listener needed to communicate with OrdersActivity
     */
    public OrdersHelper(OrdersCallbacksListener listener){
        this.listener = listener;

        //Data
        orders = new ArrayList<>();
        orderIdIndexMap = new HashMap<>();

        //Data location
        ordersCollRef = FirebaseFirestore.getInstance()
                .collection("orders");
    }

    /**
     * Fetches orders & sets a snapshotListener for listening to changes
     */
    public void fetchOrders(){
        //TODO 5 : Set snapshotListener on "orders" collection

        //Note : orderBy(timestamp, DESC) && userPhoneNo ==user.phoneNo

        /* Set listener on orders collection,
                if order ADDED, call onOrderAdded(orderId, order);
           (You'll get this callback once for each existing order so no need to read separately)
                if existing order modified, call onOrderChanged(orderId, order);
         */
    }

    /**
     * Updates list when new order is received
     * @param orderId id of the new order
     * @param order the new order object
     */
    public synchronized void onOrderAdded(String orderId, Order order){
        //Add new order at the end of list
        orders.add(order);
        orderIdIndexMap.put(orderId, orders.size() - 1);

        //Notify adapter for order added at last index
        listener.onOrderAdded(orders.size() - 1);
    }

    /**
     * Updates list when an existing order is changed
     * @param orderId id of the order changed
     * @param order new order object
     */
    public synchronized void onOrderChanged(String orderId, Order order){
        //Impossible error
        if(!orderIdIndexMap.containsKey(orderId))
            return;

        //Get index of existing order
        int index = orderIdIndexMap.get(orderId);

        //Update the order in the list
        orders.remove(index);
        orders.add(index, order);

        //Notify adapter for item changed
        listener.onOrderChanged(index);
    }

}
