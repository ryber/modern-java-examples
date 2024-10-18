package io.ryber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternMatchingTest {
    record Mouse(String name, int height){}

    Object getAnimal(String name) {
        return new Mouse(name, 2);
    }

    @Test
    void freeCastingWIthInstanceOf() {
        Object o = getAnimal("Jerry");
        if(o instanceof Mouse mouse){
           assertThat(mouse)
                   .returns("Jerry", Mouse::name);
        }
    }

    @Test
    void castExtendsOutSideTheBlock() {
        Object o = getAnimal("Micky");
        if(!(o instanceof Mouse mouse)) {
            throw new RuntimeException("We only like mice!");
        }
        assertThat(mouse)
                .returns("Micky", Mouse::name);
    }


    @Test
    void canReferenceRecordPropertiesAsVariables() {
        if(getAnimal("Mighty") instanceof Mouse(String name, _)){
            assertThat(name).isEqualTo("Mighty");
        }
    }
}
