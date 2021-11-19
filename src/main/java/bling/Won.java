package bling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Won extends AbstractCurrency {

    //I'll use the Won here as an example of a classical currency system to study how realistic the model would be.

    private ArrayList<Long> currencyList;

    private HashMap<Long, String> coinMap;

    public Won(){
        this.coinMap = new HashMap<Long, String>();
        coinMap.put((long) 10 , "₩10");
        coinMap.put((long) 50 , "₩50");
        coinMap.put((long) 100 , "₩100");
        coinMap.put((long) 500 , "₩500");
        coinMap.put((long) 1000 , "₩1000");
        coinMap.put((long) 5000 , "₩5000");
        coinMap.put((long) 10000 , "₩10000");

        this.currencyList = new ArrayList<>(coinMap.keySet());
    }

    @Override
    public ArrayList<Long> getPossibleCoinValues() {
        return currencyList;
    }

    @Override
    public HashMap<Long, String> getCoinMap() { return coinMap; }
}


