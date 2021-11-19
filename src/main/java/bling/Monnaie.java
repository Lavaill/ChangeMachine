package bling;

import bling.AbstractCurrency;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Monnaie {

    //Note: For optimisation purposes a few variables could be used to avoid having to calculate the value of
    //the total coins and total amount. This object is currently very utilitarian due to the nature of the exercise.

    private HashMap<String, Long> changeDetails = new HashMap<String, Long>();
    private AbstractCurrency currency;

    public Monnaie(AbstractCurrency currency){
        this.currency = currency;
        HashMap<String, Long> changeDetails = new HashMap<String, Long>();
        currency.getPossibleCoinValues().stream().forEach(value -> this.changeDetails.put(value.toString(), (long) 0));
    }

    //Warning: Working with longs is allowed as long as we do not use cents in our currencies.
    public Long totalCoins(){

        AtomicLong totalCoins = new AtomicLong(0);
        this.getChangeDetails().entrySet().forEach(entry -> totalCoins.addAndGet(entry.getValue()));

        return totalCoins.get();
    }

    /* FIXME Let's just avoid bigints for now.
    //This method's use of BigIntegers is highly inefficient but acts as a safeguard when calculating coin values around the maximum long.
    public BigInteger totalAmount(){

        BigInteger totalAmount =  new BigInteger("0");
        for (Map.Entry<String, Long> change : this.getChangeDetails().entrySet()){
            BigInteger bigIntTenderAmount = new BigInteger(change.getValue().toString());
            BigInteger bigIntTenderValue = new BigInteger(currency.getValueOf(change.getKey()).toString());
            totalAmount.add(bigIntTenderAmount.multiply(bigIntTenderValue));
        }

        return totalAmount;
    } */

    public Long totalAmount(){

        Long totalAmount =  (long) 0;
        for (Map.Entry<String, Long> change : this.getChangeDetails().entrySet()){
            totalAmount += change.getValue() * currency.getValueOf(change.getKey());
        }
        return totalAmount;
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
        //TODO investigate what happened here.

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
