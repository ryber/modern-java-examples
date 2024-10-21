package io.ryber;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamGatheringTest {
    @Test
    void fixedWindow() {
        var result = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.windowFixed(2))
                .toList();

        assertThat(result).isEqualTo(
                of(
                        of(1, 2),
                        of(3, 4),
                        of(5)
                )
        );
    }

    @Test
    void slidingWindows() {
        var result = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.windowSliding(2))
                .toList();

        assertThat(result).isEqualTo(
                of(
                        of(1, 2),
                        of(2, 3),
                        of(3, 4),
                        of(4, 5)
                )
        );
    }

    @Test
    void folding() {
        var ints = Stream.of(1, 2, 3, 4, 5);

        var result = ints
                .gather(Gatherers.fold( () -> "seed-",
                                        (string, number) -> string + number
                ))
                .findFirst();

        assertThat(result.get()).isEqualTo("seed-12345");
    }

    @Test
    void scan() {
        var ints = Stream.of(1, 2, 3, 4, 5);

        var result = ints
                .gather(Gatherers.scan(
                        () -> "seed-",
                        (string, num) -> string + num
                ))
                .toList();

        assertThat(result).isEqualTo(List.of(
                 "seed-1"
                ,"seed-12"
                ,"seed-123"
                ,"seed-1234"
                ,"seed-12345"
        ));
    }
}
