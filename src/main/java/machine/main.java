package main.java.machine;

import main.java.bling.AbstractCurrency;
import main.java.bling.ExerciseCurrency;
import main.java.bling.Monnaie;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args){
        System.out.println("Running main");

        System.out.println(coinChange(new ExerciseCurrency(), 1));

    }

    StringBuilder builder = new StringBuilder();
    public static Monnaie coinChange(AbstractCurrency currency, Integer targetAmount){

        //Initialising Currency:
        Monnaie monnaie = new Monnaie(currency);

        System.out.println("Running coin change for target: " + targetAmount);

        List<Integer> possibleCoinValues = currency.getPossibleCoinValues();
        System.out.println("Possible coin values: " + possibleCoinValues.stream().collect(StringBuilder::new,
                (x,y)-> x.append(y).append(","),
                (a, b) -> a.append(",").append(b)).toString());


        return monnaie;
    }
}
