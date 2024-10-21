package io.ryber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.IntStream;

import static java.util.concurrent.StructuredTaskScope.Subtask.State.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

// Preview Feature
public class StructuredConcurrencyTest {

    @BeforeEach
    void setUp() {
        Car.race.clear();
    }

    @Test
    void virtualThreads_simple() {
        var start = LocalDateTime.now();
        int number = 10_000;
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, number).mapToObj(i ->
                    executor.submit(() -> {
                        Thread.sleep(Duration.ofSeconds(1));
                        return i;
                    })
            );
        }

        System.out.println("DONE: %s in %s seconds".formatted(
                number,
                Duration.between(start, LocalDateTime.now()).getSeconds()
        ));
    }

    record Car(String name, int sleep, boolean finished) implements Callable<String> {
        static List<String> race = new ArrayList<>();
        Car(Object name, int sleep){
            this(String.valueOf(name), sleep, false);
        }

        @Override
        public String call() throws Exception {
            if(sleep < 0){
                throw new RuntimeException(name() + " Exploded");
            }
            //Thread.sleep(Duration.ofMillis(sleep));
            int i = Utils.sumOfPrimes(2, 10000);
            //System.out.println("%s : %s ".formatted(sleep, i));
            race.add(name());
            return name;
        }

        public void run() {
            try { call(); } catch (Exception _){ }
        }
    }

    @Test
    void waitTilAllTheTasksAreDone() throws Exception {
        try(var scope = new StructuredTaskScope<String>()){

            var slow = scope.fork(new Car("Slow", 19));
            var fast = scope.fork(new Car("Fast", 1));
            var pokey = scope.fork(new Car("Pokey", 7));

            scope.join();

            assertThat(Car.race)
                    .containsExactly("Fast", "Pokey", "Slow");

            List.of(slow, fast, pokey)
                    .forEach(c -> {
                        assertThat(c.state()).isEqualTo(SUCCESS);
                        assertThatCode(() -> c.get())
                                .doesNotThrowAnyException();
                    });
        }
    }

    @Test
    void raceAsyncTasks() throws Exception {
        try(var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()){

            var slow = scope.fork(new Car("Slow", 190000));
            var fast = scope.fork(new Car("Fast", 1));
            var pokey = scope.fork(new Car("Pokey", -7));

            scope.join();

            assertThat(scope.result())
                    .isEqualTo("Fast");

            assertThat(slow.state()).isEqualTo(UNAVAILABLE);
            assertThat(fast.state()).isEqualTo(SUCCESS);
            assertThat(pokey.state()).isEqualTo(FAILED);
        }
    }

    @Test
    void policiesOnJoining() throws Exception {
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){

            scope.fork(new Car("Slow", 190000));
            scope.fork(new Car("Fast", 1));
            scope.fork(new Car("Pokey", -7));

            scope.join();

            assertThatCode(() -> scope.throwIfFailed(ex -> new RuntimeException("Something went wrong! " + ex.getMessage())))
                    .hasMessage("Something went wrong! Pokey Exploded");
        }
    }


    @Test
    void virtualThreads_structured() throws Exception {
        var start = LocalDateTime.now();

        try(var scope = new StructuredTaskScope<String>()){
            var tasks = IntStream.range(0, 30_000).mapToObj(i ->
                    scope.fork(new Car(String.valueOf(i), i))
            ).toList();

            scope.join();

            assertThat(tasks)
                    .hasSize(30_000)
                    .allMatch(t -> t.state() == SUCCESS);

            System.out.println("DONE: in %s seconds".formatted(
                    Duration.between(start, LocalDateTime.now()).getSeconds()));
        }
    }
}
