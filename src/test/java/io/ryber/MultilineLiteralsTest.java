package io.ryber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MultilineLiteralsTest {
    @Test
    void multilineLiteralsAreCool() {
        var str = """
                OMG
                there are "things" on
                multiple
                lines without +
                by using triple quotes (\""")
                """;

        assertThat(str)
                .contains("OMG")
                .contains("by using triple quotes (\"\"\")")
                .hasLineCount(5);
    }

    @Test
    void formatting() {
        var str = """
                The rain in %s
                falls mainly on the %s
                """.formatted("Spain", "plain");

        assertThat(str)
                .contains("The rain in Spain")
                .contains("falls mainly on the plain")
                .hasLineCount(2);
    }

//    @Test
//    void stringTemplates() {
//        var country = "Spain";
//        var location = "plain";
//
//        String str = STR."""
//                    {
//                        "country": "{country}",
//                        "location": "{location}"
//                    }
//                """;
//
//        assertThat(str)
//                .contains("Spain");
//    }
}
