package bling;

import java.util.*;

public abstract class AbstractCurrency {

    public abstract ArrayList<Long> getPossibleCoinValues();

    public abstract HashMap<Long, String> getCoinMap();

    ArrayList<Long> sortedCoinValues = null;

    public Long getValueOf(String tender){
        return getCoinMap()
              .entrySet()
              .stream()
              .filter(entry -> tender == entry.getValue())
              .findFirst()
              .get()
              .getKey();
    };

    public String getNameOf(Long tender){
        return getCoinMap().get(tender);
    }

    public ArrayList<Long> getAscendingCoinValues(){
        if(sortedCoinValues == null){
            sortedCoinValues = getPossibleCoinValues();
            Collections.sort(sortedCoinValues);
        }
        return sortedCoinValues;
    };

    //We will be using the least common multiple of the currency in order to optimise the algorithm for very large numbers.
    public Long getLcm(){
        return lcm(getPossibleCoinValues());
    }

    //These three concise methods for finding the lcm of a vararg are based on the Euclid algorithm and extracted
    //from Stack Overflow, credits due to Qw3ry. I only made it compatible with longs.
    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    private static long gcd(ArrayList<Long> numbers) {
        return numbers.stream().reduce((long) 0, (x, y) -> gcd(x, y));
    }

    private static long lcm(ArrayList<Long> numbers) {
        return numbers.stream().reduce((long) 1, (x, y) -> x * (y / gcd(x, y)));
    }
}
