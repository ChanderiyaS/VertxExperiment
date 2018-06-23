package com.csaura.dataGeneration;

import com.csaura.data.Whiskey;

import java.util.LinkedHashMap;
import java.util.Map;

public class Generator {

    // Store our product



    // Create some product
    public  Map<Integer,Whiskey> createSomeData() {
        Map<Integer, Whiskey> products = new LinkedHashMap<>();
        Whiskey bowmore = new Whiskey("Bowmore 15 Years Laimrig", "Scotland, Islay");
        products.put(bowmore.getId(), bowmore);
        Whiskey talisker = new Whiskey("Talisker 57° North", "Scotland, Island");
        products.put(talisker.getId(), talisker);

        return products;
    }

}

