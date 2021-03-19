package com.example.myassistant;

@FunctionalInterface // Allows for lambdas
public interface HandleCheckFunction {
    boolean shouldHandleCommand(String command);
}
