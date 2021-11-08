package bling;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractCurrency {

    public abstract ArrayList<Long> getPossibleCoinValues();

    public abstract HashMap<String, Long> getCoinMap();
}
