package machine;

import bling.AbstractCurrency;
import bling.ExerciseCurrency;
import bling.Monnaie;
import helper.Trampoline;
import workspace.Fibonacci;

import java.lang.reflect.Array;
import java.util.*;

public class main {
    public static void main(String[] args){
        System.out.println("Running main");

        fibonacciDepthTest();


//      getMinimalChangeTest();

        //Two possible approaches: Implement my own stack or
        //https://stackoverflow.com/questions/5496464/write-a-non-recursive-traversal-of-a-binary-search-tree-using-constant-space-and

    }

    StringBuilder builder = new StringBuilder();

    public static void fibonacciDepthTest(){
//        System.out.println("Fib 0 = " + new Fibonacci().getClassical(0).toString());
//        System.out.println("Fib 1 = " + new Fibonacci().getClassical(1).toString());
//        System.out.println("Fib 10 = " + new Fibonacci().getClassical(10).toString());
//        System.out.println("Fib 16 = " + new Fibonacci().getClassical(16).toString());
//        System.out.println("Fib 160 = " + new Fibonacci().getClassical(160).toString());
        //System.out.println("Fib 161 = " + new Fibonacci().getClassical(161).toString());

        //We filled standard longs here
        //System.out.println("Fib 1000 = " + new Fibonacci().getClassical(1000).toString());

        //Overflow begins here
        //System.out.println("Fib 10000 = " + new Fibonacci().get(10000).toString());

        System.out.println("FibBounce 0 = " + new Fibonacci().getBouncy(0).execute().toString());
        System.out.println("FibBounce 1 = " + new Fibonacci().getBouncy(1).execute().toString());
        System.out.println("FibBounce 10 = " + new Fibonacci().getBouncy(10).execute().toString());
        System.out.println("FibBounce 16 = " + new Fibonacci().getBouncy(16).execute().toString());
        System.out.println("FibBounce 160 = " + new Fibonacci().getBouncy(160).execute().toString());
        System.out.println("FibBounce 161 = " + new Fibonacci().getBouncy(161).execute().toString());

        //We filled standard longs here
        System.out.println("FibBounce 1000 = " + new Fibonacci().getBouncy(1000).execute().toString());

        //Overflow on classical methods starts here
        System.out.println("FibBounce 10000 = " + new Fibonacci().getBouncy(10000).execute().toString());
    }

    public static void monnaieObjectTest(){
        Monnaie firstMonnaie = new Monnaie(new ExerciseCurrency());
        Monnaie secondMonnaie = new Monnaie(new ExerciseCurrency());

        System.out.println("Monnaie initialised to : \n" + secondMonnaie);

        //Monnaie set method test
        firstMonnaie.setCoin("2", (long) 2);
        firstMonnaie.setCoin("10", (long) 1);
        secondMonnaie.setCoin("10", (long) 10);
        System.out.println("First monnaie:\n" + firstMonnaie);
        System.out.println("Second monnaie:\n" + secondMonnaie);

        //Monnaie merge method test
        Monnaie thirdMonnaie = firstMonnaie.merge(secondMonnaie);
        System.out.println("Third monnaie:\n" + thirdMonnaie);

        //Monnaie get number of coins test:
        System.out.println("First monnaie coin count = " + firstMonnaie.totalCoins());
        System.out.println("Second monnaie coin count = " + secondMonnaie.totalCoins());
        System.out.println("Third monnaie coin count = " + thirdMonnaie.totalCoins());
    }

    private static void getMinimalChangeTest(){
        System.out.println("Get minimal change on value : 1");
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 1));
        System.out.println("\n");

        System.out.println("Get minimal change on value : 6");
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 6));
        System.out.println("\n");

        System.out.println("Get minimal change on value : 10");
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 10));
        System.out.println("\n");

        System.out.println("Get minimal change on value : 27");
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 27));
        System.out.println("\n");

        System.out.println("Get minimal change on value : 28");
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 28));
        System.out.println("\n");

        System.out.println("Get minimal change on value : 2898");
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 2898));
        System.out.println("\n");


        //OK//
        System.out.println("Get minimal change on value : " + (21467));
        System.out.println(getMinChange(new ExerciseCurrency(), (long) 21467));
        System.out.println("\n");


        //OK//
        System.out.println("Get minimal change on value : " + Integer.MAX_VALUE);
        System.out.println(getMinChange(new ExerciseCurrency(), (long) Integer.MAX_VALUE));
        System.out.println("\n");

        /*
        //FINAL BOSS//
        System.out.println("Get minimal change on value : 9223372036854775807");
        System.out.println(getMinChange(new ExerciseCurrency(), Long.MAX_VALUE));
        System.out.println("\n");
        */

    }

    /**
     * This method is the core of the exercise, it will initialise the recursive operations and handle the memoization
     * and other optimisations
     * @param currency The currency system given to us. All coins are supposed infinite in this version.
     * @param targetAmount The amount we want to analyze the minimum possible change for given the currency.
     * @return A Monnaie object that will contain the minimal change information.
     */
    public static Monnaie getMinChange(AbstractCurrency currency, Long targetAmount){

        //Initialising Currency:
        Monnaie monnaie = new Monnaie(currency);

        System.out.println("Running coin change for target: " + targetAmount);

        //List<Integer> possibleCoinValues = currency.getPossibleCoinValues();

        HashMap<Long, Monnaie> answerMap = new HashMap<>();


        //TODO check for initial quickest path set.


        if (targetAmount == 0){
            return monnaie;
        } else {
            return minChangeRecurse(currency, targetAmount, answerMap);
        }
    }

    static Monnaie minChangeRecurse(AbstractCurrency currency, Long targetAmount, HashMap<Long, Monnaie> answerMap){

        //List<Integer> possibleCoinValues = currency.getPossibleCoinValues();

        Monnaie minMonnaie = null;

        //Branching occurs here
        for(Map.Entry<String, Long> coin : currency.getCoinMap().entrySet()){
            //System.out.println("Examining coin " + coin.getKey());

            Long s = targetAmount - coin.getValue();
            // Case prune due to high value:
            if (s < 0){
                continue;
            }

            // Case correct possibility found:
            if (s == 0){
                Monnaie monnaie = new Monnaie(currency);
                monnaie.setCoin(coin.getKey(), (long) 1);
                minMonnaie = monnaie;
            }

            // Case not possibility found yet.
            if (s > 0){
                Monnaie monnaie = new Monnaie(currency);
                monnaie.setCoin(coin.getKey(), (long) 1);
                //Check for memoized answer:
                if (answerMap.get(s) != null){
                    System.out.println("Found answer for: " + s);
                    // FIXME I have the feeling we should be firing that map earlier.
                    monnaie = monnaie.merge(answerMap.get(s));
                } else {
                    monnaie = monnaie.merge(minChangeRecurse(currency, s, answerMap));
                }

                if(monnaie != null && (minMonnaie == null || monnaie.totalCoins() < minMonnaie.totalCoins())){
                    minMonnaie = monnaie;
                }
            }
        }
        answerMap.put(targetAmount, minMonnaie);

        return minMonnaie;
    }

    static Trampoline<Monnaie> trampolineMinChangeRecurse(AbstractCurrency currency, Long targetAmount, HashMap<Long, Monnaie> answerMap){

        Monnaie minMonnaie = null;


        //Branching occurs here
        for(Map.Entry<String, Long> coin : currency.getCoinMap().entrySet()){

            Long s = targetAmount - coin.getValue();
            // Case prune due to high value:
            if (s < 0){
                continue;
            }

            // Case correct possibility found:
            if (s == 0){
                Monnaie monnaie = new Monnaie(currency);
                monnaie.setCoin(coin.getKey(), (long) 1);


                //Early Return
                final Monnaie earlyMinMonnaie = monnaie;
                return new Trampoline<Monnaie>() {
                    public Monnaie get() {
                        return earlyMinMonnaie;
                    }
                };

            }

            // Case no possibility found yet.
            if (s > 0){
                Monnaie monnaie = new Monnaie(currency);
                monnaie.setCoin(coin.getKey(), (long) 1);
                //Check for memoized answer:
                if (answerMap.get(s) != null){
                    System.out.println("Found answer for: " + s);
                    // FIXME I have the feeling we should be firing that map earlier.
                    monnaie = monnaie.merge(answerMap.get(s));
                } else {
                    // FIXME Issue with trampoline here
                    //monnaie = monnaie.merge(trampolineMinChangeRecurse(currency, s, answerMap));
                }

                if(monnaie != null && (minMonnaie == null || monnaie.totalCoins() < minMonnaie.totalCoins())){
                    minMonnaie = monnaie;
                }
            }
        }
        answerMap.put(targetAmount, minMonnaie);

        //FIXME Quick test
        Monnaie finalMinMonnaie = minMonnaie;
        return new Trampoline<Monnaie>(){ public Monnaie get() {return finalMinMonnaie;}};
    }
}
