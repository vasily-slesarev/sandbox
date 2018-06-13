package io.github.vasily_slesarev;

import org.junit.Test;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SamplerTest {

    @Test
    public void sampleOneTest() {
        Map<Character, Long> map = new LinkedHashMap<>();
        map.put('a', 2L);
        map.put('c', 5L);
        map.put('B', 3L);
        Sampler<Character> sampler = new Sampler<>(map);

        for (int i = 0; i < 2; ++i) {
            assertEquals(Character.valueOf('a'), sampler.sampleOne(i));
        }
        for (int i = 2; i < 7; ++i) {
            assertEquals(Character.valueOf('c'), sampler.sampleOne(i));
        }
        for (int i = 7; i < 10; ++i) {
            assertEquals(Character.valueOf('B'), sampler.sampleOne(i));
        }
    }

    @Test
    public void sampleTest() {
        Map<Character, Long> map = new LinkedHashMap<>();
        map.put('a', 1L);
        Sampler<Character> sampler = new Sampler<>(map);

        List<Character> sample = sampler.sample(2);
        assertEquals(2, sample.size());
        assertEquals(Character.valueOf('a'), sample.get(0));
        assertEquals(Character.valueOf('a'), sample.get(1));
    }

}
