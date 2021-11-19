package machine;

import bling.*;
import workspace.Fibonacci;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

public class main {
    public static void main(String[] args){
        //Please ignore the quality of the main code, it was written quickly to have something visual.
        //Uncomment the following line to go straight to the exercise:
        //getMinimalChangeStackTest(new ExerciseCurrency());

        System.out.println("The change will be calculated on three different currencies: \nThe exercise currency, the modern Korean Won and a fictional prime-number currency");

        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a long:");
            Long targetNumber;
            try{
                targetNumber = scanner.nextLong();
            } catch (InputMismatchException e){
                System.out.println("Invalid long.");
                continue;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Something went very wrong.");
                e.printStackTrace();
                continue;
            }
            System.out.println("\nTest currency:\n");
            System.out.println(getMinChangeStack(new ExerciseCurrency(), targetNumber));

            System.out.println("\nWon:\n");
            System.out.println(getMinChangeStack(new Won(), targetNumber));

            System.out.println("\nPrimo-coin:\n");
            System.out.println(getMinChangeStack(new PrimeCurrency(), targetNumber));

        }
    }

    StringBuilder builder = new StringBuilder();

    //Quick method to test out whether the monnaie object behaves properly.
    public static void monnaieObjectTest(){
        Monnaie firstMonnaie = new Monnaie(new ExerciseCurrency());
        Monnaie secondMonnaie = new Monnaie(new ExerciseCurrency());

        System.out.println("ExerciseCurrency LCM : " + new ExerciseCurrency().getLcm());
        System.out.println("Won LCM : " + new Won().getLcm());
        System.out.println("Monnaie initialised to : \n" + secondMonnaie);

        //Monnaie set method test
        firstMonnaie.setCoin((long) 2, (long) 2);
        firstMonnaie.setCoin((long) 10, (long) 1);
        secondMonnaie.setCoin((long) 10, (long) 10);
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


    private static void getMinimalChangeStackTest(AbstractCurrency currency){
        Long[] testValues = new Long[]{
                (long) 1,
                (long) 6,
                (long) 10,
                (long) 27,
                (long) 28,
                //Case to trip the Prime Currency
                (long) 1985,
                (long) 2898,
                (long) 22040,
                (long) 220410,
                (long) Integer.MAX_VALUE,
                Long.MAX_VALUE
        };

        Arrays.stream(testValues).forEach(testValue -> {
                    System.out.println("Get minimal change on value : " + testValue);
                    System.out.println(getMinChangeStack(currency, testValue));
                    System.out.println("\n");
                }
        );
    }
    private static Monnaie getMinChangeStack(AbstractCurrency currency, Long targetAmount){

        //Initialising the stack, the current Monnaie object, the current minimum coins and the memoization:
        Stack<Monnaie> changeStack = new Stack<>();
        HashMap<Long, Monnaie> answerMap = new HashMap<Long, Monnaie>();

        //We sort the coin values in order to guarantee optimal stack execution.
        ArrayList<Long> coinValues = currency.getPossibleCoinValues();
        Collections.sort(coinValues);

        Monnaie currentMonnaie = new Monnaie(currency);

        /*
        As a special optimisation for extremely large coin values of any currency we start the calculation at a set value calculated from the
        lowest common multiplier of all of the currency's coins. On all currently accepted legal currencies I know of the Lcm is equal to
        the highest tender available. On fictional currencies however the Lcm may be different from the highest legal tender which is why I took the liberty
        of calculating it. I have proven the stability of this approach up to max int for the three currencies available.

        Note: On custom currencies made out of prime numbers the LCM can be too high to get fast results on large numbers.
        */

        Long currencyLcm = currency.getLcm();
        Long highestCurrency = currency.getAscendingCoinValues().get(coinValues.size() -1);
        Long currencyBuffer = (Long) ((targetAmount - targetAmount % currencyLcm) / highestCurrency);
        Long safeStart = Math.max(currencyBuffer - 1, 0);
        currentMonnaie.setCoin(highestCurrency, safeStart);

        Monnaie minMonnaie = null;

        //Tracking values:
        Long nodesPopped = (long) 0;
        Long highCoinPrunes = (long) 0;
        Long highValuePrunes = (long) 0;
        Long memoizePrunes = (long) 0;
        Long hits = (long) 0;



        do {
            if(!changeStack.isEmpty()){
                //System.out.println("Popping : " + currentMonnaie.totalAmount());
                nodesPopped ++;

                currentMonnaie = changeStack.pop();
            }

            //We prune any branch that is known to be unable to produce a solution if one has been found.
            //Relevant for currencies with a large amount
            if(currentMonnaie != null && minMonnaie != null && currentMonnaie.totalCoins() + 1 >= minMonnaie.totalCoins()){
                highCoinPrunes ++;

                continue;
            }

            //For each coin in our currency we check if subtracting one would bring us under the target.
            for(Long coin : currency.getAscendingCoinValues()){
                Long s = targetAmount - (currentMonnaie.totalAmount() + coin);

                // Case prune due to high value:
                if (s < 0){
                    continue;
                }

                // Case correct possibility found:
                if (s == 0){
                    hits ++;
                    Monnaie monnaie = new Monnaie(currency);
                    monnaie.setCoin(coin, (long) 1);
                    minMonnaie = monnaie.merge(currentMonnaie);
                }

                // Case no possibility found yet.
                if (s > 0){
                    Monnaie monnaie = new Monnaie(currency);
                    monnaie.setCoin(coin, (long) 1);
                    //If our answer is the better than the current best answer for the value we're going to, we add it to the stack.
                    //Else, we continue our search.

                    //Check for memoized answer:
                    if (answerMap.get(s) != null){
                        if (currentMonnaie.totalCoins() + 1 < answerMap.get(s).totalCoins() ){
                            answerMap.put(s, currentMonnaie.merge(monnaie));
                            changeStack.add(currentMonnaie.merge(monnaie));
                        } else {
                            memoizePrunes++;
                            continue;
                        }
                    } else {
                        answerMap.put(s, currentMonnaie.merge(monnaie));
                        changeStack.add(currentMonnaie.merge(monnaie));
                    }
                }
            }
        } while (!changeStack.isEmpty());

        /*
        System.out.println("nodesPopped = " + nodesPopped);
        System.out.println("highCoinPrunes = " + highCoinPrunes);
        System.out.println("highValuePrunes = " + highValuePrunes);
        System.out.println("memoizePrunes = " + memoizePrunes);
        System.out.println("hits = " + hits);
         */
        return minMonnaie;
    }
}

