package nl.thijsnederlof.common.util;

import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiKeyMapTest {

    private MultiKeyMap<String, Long, String> sut;

    @Test
    public void given_givenSinglePut_thenExpect_multiKeyMapSize_One() {
        sut = new MultiKeyMap<>();
        sut.put("key", 1L, "value");

        assertThat(sut.size()).isEqualTo(1);
    }

    @Test
    public void given_thenExpect() {
        sut = new MultiKeyMap<>();
        sut.put("key", 1L, "value");
        final Set<MultiKeyMap.Entry<String, Long, String>> entries = sut.entrySet();
        assertThat(entries.size()).isEqualTo(1);
    }
}