package workspace;

import helper.Trampoline;

import java.util.HashMap;
import java.util.Optional;

public class Fibonacci {

    private static HashMap<Integer, Long> problemSet = new HashMap();

    public static Long getClassical(Integer n){
        if(n == 0 || n == 1){
            return (long) n;
        } else {
            if(problemSet.containsKey(n)){
                return problemSet.get(n);
            }
            Long response = getClassical(n - 1) + getClassical(n - 2);
            problemSet.put(n, response);
            return response;
        }
    }

    public static Trampoline<Long> getBouncy(Integer n){
        if(n <= 1){
            return new Trampoline<Long>(){
                public Long get(){
                    return (long) n;
                }
            };
        } else {
            if(problemSet.containsKey(n)){
                return new Trampoline<Long>(){
                    public Long get(){
                        return problemSet.get(n);
                    }
                };
            }

            return new Trampoline<Long>(){
                public Trampoline<Long> run(){
                    Long response = getBouncy(n - 1).execute() + getBouncy(n - 2).execute();
                    problemSet.put(n, response);
                    return new Trampoline<Long>() {
                        public Long get() {
                            return response;
                        }
                    };
                }
            };
        }
    }
}
