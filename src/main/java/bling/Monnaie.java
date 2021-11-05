package main.java.bling;

import java.util.HashMap;

public class Monnaie {

    HashMap<String, Integer> changeDetails;
    public Monnaie(AbstractCurrency currency){
        currency.getPossibleCoinValues().stream().forEach(value -> this.changeDetails.put(value.toString(), 0));
    }

    @Override
    public String toString(){
        return changeDetails.entrySet().stream()
                .collect(StringBuilder::new,
                        (x,y) -> x.append(x + y.toString()),
                        (a,b) -> a.append("\n").append(b))
                .toString();
    }
}
