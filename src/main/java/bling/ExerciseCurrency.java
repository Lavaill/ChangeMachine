package bling;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExerciseCurrency extends AbstractCurrency {

    //FIXME Deprecate the currencyList functionality it's not brilliant, let's only do the map.
    private ArrayList<Long> currencyList;

    private HashMap<Long, String> coinMap;

    public ExerciseCurrency(){
        this.coinMap = new HashMap<Long, String>();
        coinMap.put((long) 2 , "2€");
        coinMap.put((long) 5 , "5€");
        coinMap.put((long) 10 , "10€");

        this.currencyList = new ArrayList<>(coinMap.keySet());
    }

    @Override
    public ArrayList<Long> getPossibleCoinValues() {
        return currencyList;
    }

    @Override
    public HashMap<Long, String> getCoinMap() { return coinMap; }
}
