package bling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PrimeCurrency extends AbstractCurrency {

    //I use this fictional prime-number only currency to test extreme edge cases. This currency is not convenient.

    private ArrayList<Long> currencyList;

    private HashMap<Long, String> coinMap;

    public PrimeCurrency(){
        this.coinMap = new HashMap<Long, String>();
        coinMap.put((long) 3 , "⊗3");
        coinMap.put((long) 11 , "⊗11");
        coinMap.put((long) 89 , "⊗89");
        coinMap.put((long) 97 , "⊗97");

        this.currencyList = new ArrayList<>(coinMap.keySet());
    }

    @Override
    public ArrayList<Long> getPossibleCoinValues() {
        return currencyList;
    }

    @Override
    public HashMap<Long, String> getCoinMap() { return coinMap; }
}




