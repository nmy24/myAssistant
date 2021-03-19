package com.example.myassistant;

@FunctionalInterface // Allows for lambdas
public interface CommandHandler {
    void handleCommand(String command);

}
