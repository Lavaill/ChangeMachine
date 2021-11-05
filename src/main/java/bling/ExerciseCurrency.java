package main.java.bling;
import java.util.ArrayList;
import java.util.Arrays;

public class ExerciseCurrency extends AbstractCurrency {


    ArrayList<Integer> currencyList;

    public ExerciseCurrency(){
        this.currencyList = new ArrayList<Integer>(Arrays.asList(2,5,10));
        System.out.println();

    }

    @Override
    public ArrayList<Integer> getPossibleCoinValues() {
        return currencyList;
    }
}
