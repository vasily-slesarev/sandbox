package io.github.vasily_slesarev;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static io.github.vasily_slesarev.StreamSampler.LATIN_LETTERS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamSamplerTest {

    @Test
    public void latinLettersTest() {
        assertEquals(52, LATIN_LETTERS.size());
        assertTrue(LATIN_LETTERS.contains('a'));
        assertTrue(LATIN_LETTERS.contains('z'));
        assertTrue(LATIN_LETTERS.contains('A'));
        assertTrue(LATIN_LETTERS.contains('Z'));
    }

    @Test
    public void countLatinLettersTest() {
        String input = "aЫЫЫЫЫЫЫЫЫ111aBB BB*?c";
        Map<Character, Long> expected = new HashMap<>();
        expected.put('a', 2L);
        expected.put('c', 1L);
        expected.put('B', 4L);

        Map<Character, Long> result = new StreamSampler().countLatinLetters(new ByteArrayInputStream(input.getBytes()));
        assertEquals(expected, result);
    }

    @Test
    public void sampleTest() {
        int length = 1000000;
        String sample = new StreamSampler().sample(new ByteArrayInputStream("".getBytes()), length);
        assertEquals(length, sample.length());
        for (Character c : StreamSampler.LATIN_LETTERS) {
            assertTrue(sample.indexOf(c) >= 0);
        }
    }

}