package io.ryber;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SequencedCollectionsTest {
    @Test
    void first_and_last() {
        var list = new ArrayList<Integer>();

        list.addFirst(1);
        list.addFirst(2);

        assertThat(list).containsExactly(2, 1);
        assertThat(list.getFirst()).isEqualTo(2);
        assertThat(list.getLast()).isEqualTo(1);

        list.addLast(0);

        assertThat(list).containsExactly(2, 1, 0);

        assertThat(list.reversed()).containsExactly(0, 1, 2);
    }

    @Test
    void maps() {
        var map = new LinkedHashMap<String, Integer>();

        map.put("foo", 1);
        map.putFirst("bar", 2);
        map.putLast("baz", 3);

        assertThat(map.pollFirstEntry())
                .returns("bar", Map.Entry::getKey)
                .returns(2,     Map.Entry::getValue);

        assertThat(map.pollLastEntry())
                .returns("baz", Map.Entry::getKey)
                .returns(3,     Map.Entry::getValue);
    }
}
