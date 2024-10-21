package io.ryber;

import io.ryber.StructuredConcurrencyTest.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.IntStream;

import static java.util.concurrent.Future.State.RUNNING;

public class StructuredConcurrencyPerfTest {
    private LocalDateTime start;

    @BeforeEach
    void setUp() {
        start = LocalDateTime.now();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Done in " + Duration.between(start, LocalDateTime.now()).getSeconds());
    }

    @Test //121 seconds
    void none() {
        IntStream.range(0, 10000).forEach(i -> {
            new Car(i, 10).run();
            System.out.println(i);
        });
    }

    @Test // 12 seconds
    void threads() {
        try (var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            var futures = IntStream.range(0, 10000).mapToObj(i -> {
                var car = new Car(i, 10);
                var f = executor.submit(car);
                System.out.println(i);
                return f;
            }).toList();

            while(futures.stream().anyMatch(f -> f.state() == RUNNING)){
              // we wait
            }
        }
    }

    @Test // < 1 seconds
    void virtualThreads() throws InterruptedException {
        try (var executor =  new StructuredTaskScope<String>()) {
            var futures = IntStream.range(0, 10000).mapToObj(i -> {
                var car = new Car(i, 10);
                var f = executor.fork(car);
                return f;
            }).toList();

           executor.join();
        }
    }
}
