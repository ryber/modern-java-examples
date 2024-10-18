package io.ryber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NullPointersTest {

    record Fruit(String name, String color){}

    @Test
    void nullPointersTellYouTheNullThing() {
        assertThatThrownBy(() -> new Fruit("Apple", null).color().length())
                .hasMessage("""
                        Cannot invoke "String.length()" because the return value of "io.ryber.NullPointersTest$Fruit.color()" is null""");

    }
}
