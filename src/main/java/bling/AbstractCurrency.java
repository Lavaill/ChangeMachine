package bling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public abstract class AbstractCurrency {

    public abstract ArrayList<Long> getPossibleCoinValues();

    public abstract HashMap<String, Long> getCoinMap();

    public Long getValueOf(String tender){
      return getCoinMap().get(tender);
    };

    public ArrayList<Long> getAscendingCoinValues(){
        ArrayList<Long> possibleCoinValues = getPossibleCoinValues();
        possibleCoinValues.sort(Collections.reverseOrder());
        return possibleCoinValues;
    };
}
