package bling;

import bling.AbstractCurrency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Monnaie {

    private HashMap<String, Long> changeDetails = new HashMap<String, Long>();
    private AbstractCurrency currency;

    public Monnaie(AbstractCurrency currency){
        this.currency = currency;
        currency.getPossibleCoinValues().stream().forEach(value -> this.changeDetails.put(value.toString(), (long) 0));
    }

    public Long totalCoins(){

        /*
        Long totalCoins = (long) 0;
        for (Map.Entry<String,Integer> entry : this.getChangeDetails().entrySet()){
            totalCoins += entry.getValue();
        }
        return totalCoins;
        */


        AtomicLong totalCoins = new AtomicLong(0);
        this.getChangeDetails().entrySet().forEach(entry -> totalCoins.addAndGet(entry.getValue()));


        return totalCoins.get();
    }

    /**
     * This method is used to quickly merge Monnaie objects. It adds the coins of each type to the relevant coins
     * of the receiving Monnaie objects and creates a third object for ease of storage
     * @param monnaie The Monnaie object we should use as a base for the merge
     * @return A Monnaie object that is the combination of both entrant Monnaie objects
     */
    public Monnaie merge(Monnaie monnaie){
        if(monnaie == null){
            return null;
        } else {
            Monnaie resultingMonnaie = new Monnaie(this.currency);
            monnaie.getChangeDetails().entrySet().stream().forEach(entry ->
                    resultingMonnaie.setCoin(entry.getKey(),
                            changeDetails.get(entry.getKey()) + entry.getValue()));
            return resultingMonnaie;

        }
    }

    public void setCoin(String coinType, Long coinQuantity){
        changeDetails.put(coinType, coinQuantity);
    }

    public HashMap<String, Long> getChangeDetails(){
        return changeDetails;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        changeDetails.entrySet().forEach(entry -> sb
                .append(entry.getKey())
                .append(" = ")
                .append(entry.getValue().toString())
                .append("\n"));
        return sb.toString();
    }
}
