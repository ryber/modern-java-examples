package io.ryber;

import org.junit.jupiter.api.Test;

public class ScopedValuesTest {

    public final static ScopedValue<String> SCOPED = ScopedValue.newInstance();

    @Test
    void scopedValues() {
        ScopedValue.where(SCOPED, "Cheese")
                .run(() -> System.out.println("I like " + SCOPED.get()));
    }
    
}
