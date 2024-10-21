package io.ryber;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class SwitchStatementTest {
    @Test
    void simplified() {
        var species = switch ("Garfield") {
            case "Sylvester", "Tom", "Garfield" -> "cat";
            case "Goofy", "Pluto"               -> "dog";
            default                             -> "bird";
        };
        assertThat(species)
                .isEqualTo("cat");
    }

    @Test
    void yield() {
        var name = "Garfield";
        var species = switch (name) {
            case "Sylvester", "Tom", "Garfield" -> {
                System.out.println("name = " + name);
                yield "cat";
            }
            case "Goofy", "Pluto" -> "dog";
            default -> "Bird";
        };
        assertThat(species)
                .isEqualTo("cat");
    }

    Object something(){
        return new Object();
    }

    @Test
    void typeVariables() {
        switch (something()) {
            case String s when s.equals("World") -> System.out.println("world");
            case Integer i -> System.out.println("i = " + i);
            case null, default -> System.out.println("nothing");
        }
    }

    interface Animal {}
    record Fish(boolean isSaltWater) implements Animal {}
    record Crab(String color)        implements Animal {}

    Animal fromTheSea(){
        return new Fish(true);
    }

    @Test
    void records() {
        var animal = fromTheSea();
        switch (animal) {
            case Fish f:
                assertThat(f).returns(true, Fish::isSaltWater);
                break;
            case Crab c:
                assertThat(c).returns("red", Crab::color);
                break;
            default:
                throw new RuntimeException("I don't know you!");
        }
    }

    @Test
    void recordsWithPatterns() {
        var animal = fromTheSea();
        switch (animal) {
            case Fish(boolean isSaltWater):
                assertThat(isSaltWater).isTrue();
                break;
            case Crab(String color):
                assertThat(color).isEqualTo("red");
                break;
            default:
                throw new RuntimeException("I don't know you!");
        }
    }
}
