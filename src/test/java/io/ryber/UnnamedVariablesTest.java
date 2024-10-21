package io.ryber;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

public class UnnamedVariablesTest {
    @Test
    void exceptions() {
        try {
            var number = Integer.parseInt("cheese");
        } catch (NumberFormatException _){
            // _ cannot be referenced
        }
    }

    @Test
    void loop() {
        for (int i = 0, _ = runOnce(); i < 10; i++) {
            System.out.print(i + " ");
        }
    }

    @Test
    void unnamedVariablesInLambdas() {
        doIt( (_,_) -> true);
    }

    void doIt(BiFunction<String, Integer, Boolean> func){}

    private int runOnce() {
        return 0;
    }
}
