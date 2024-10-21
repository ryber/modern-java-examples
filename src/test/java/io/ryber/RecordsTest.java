package io.ryber;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecordsTest {

    record Cat(String name, int age) { }

    @Test
    void basicRecord() {
        assertThat(new Cat("Barry", 4))
                .returns("Barry", Cat::name)
                .returns(4, Cat::age)
                .returns("Cat[name=Barry, age=4]", Cat::toString)
                .isEqualTo(new Cat("Barry", 4))
                .isNotEqualTo(new Cat("Barry", 12))
                .isNotEqualTo(new Cat("Ned", 4));
    }

    record Bird(String name, int age, int wingspan){
        static List<Bird> flock = new ArrayList<>();

        Bird {
            flock.add(this);
        }

        Bird(String name) {
            this(name, 0 , 100);
        }

        boolean canFly() {
            return wingspan > 10;
        }

        public List<Bird> getFlock(){
            return flock;
        }
    }

    @Test
    void constructors(){
        assertThat(new Bird("Tweety Bird"))
                .returns(true, Bird::canFly)
                .returns(1, b -> b.getFlock().size());

        assertThat(new Bird("Foghorn Leghorn", 53, 1))
                .returns(false, Bird::canFly)
                .returns(2, b -> b.getFlock().size());;

    }
}