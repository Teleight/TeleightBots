package org.teleight.teleightbots.utils.validate;

public class Check {

    public static void stateCondition(boolean condition, String reason){
        if(condition){
            throw new IllegalStateException(reason);
        }
    }

}
