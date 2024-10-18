package io.ryber;

import org.junit.jupiter.api.Test;

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

    private int runOnce() {
        return 0;
    }
}
