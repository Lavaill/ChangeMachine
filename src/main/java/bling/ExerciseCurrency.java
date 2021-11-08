package bling;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExerciseCurrency extends AbstractCurrency {

    //FIXME Deprecate the currencyList functionality it's not brilliant, let's only do the map.
    private ArrayList<Long> currencyList;

    private HashMap<String, Long> coinMap;

    public ExerciseCurrency(){
        this.currencyList = new ArrayList<Long>(Arrays.asList((long) 2,(long) 5, (long) 10));
        this.coinMap = new HashMap<String, Long>();
        coinMap.put("2", (long) 2);
        coinMap.put("5", (long) 5);
        coinMap.put("10", (long) 10);
    }

    @Override
    public ArrayList<Long> getPossibleCoinValues() {
        return currencyList;
    }

    @Override
    public HashMap<String, Long> getCoinMap() { return coinMap; }
}
