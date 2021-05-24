package com.example.myassistant;

@FunctionalInterface // Allows for lambdas
/**
 * This interface will be the function pointer to the check is command functions.
 * @author Noa Fatael
 */
public interface HandleCheckFunction {
    boolean shouldHandleCommand(String command);
}
