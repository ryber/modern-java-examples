package io.ryber;

import org.junit.jupiter.api.Test;

public class FlexibleConstructorBodiesTest {
    @Test
    void buildIt() {
        var cat = new Cat("Bart");
    }

    class Animal {
        public Animal(String name){}
    }

    class Cat extends Animal {
        Cat(String name) {
            if ("Homer".equalsIgnoreCase(name)){
                throw new RuntimeException("No Homers");
            }
            super(name);
        }
    }
}
