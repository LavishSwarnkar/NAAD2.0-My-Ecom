package com.streamliners.myecom.models;

import java.util.List;

public class Product {

    //Common
    public String name, imageURL;
    public int type;

    //WBP
    float minQty, pricePerKg;

    //VBP
    public List<Variant> variants;

}
