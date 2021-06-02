package com.streamliners.myecom.models;

import java.util.HashMap;

public class Cart {

    public HashMap<String, CartItem> cartItems = new HashMap<>();
    public float total, noOfItems;

}
