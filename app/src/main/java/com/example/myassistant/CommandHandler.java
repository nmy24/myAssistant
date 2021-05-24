package com.example.myassistant;

@FunctionalInterface // Allows for lambdas
/**
 * This interface will be the function pointer to the handle command functions.
 * @author Noa Fatael
 */
public interface CommandHandler {
    void handleCommand(String command);

}
