package io.ryber;

import org.junit.jupiter.api.Test;

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
                .gather(Gatherers.fold( () -> "",
                                        (string, number) -> string + number
                ))
                .findFirst();

        assertThat(result.get()).isEqualTo("12345");
    }
}
