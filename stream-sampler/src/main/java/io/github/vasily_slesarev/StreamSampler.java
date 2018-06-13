package io.github.vasily_slesarev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSampler {

    public static final Set<Character> LATIN_LETTERS =
        Stream.concat(
            IntStream.range(0, 26).mapToObj(i -> (char)('a' + i)),
            IntStream.range(0, 26).mapToObj(i -> (char)('A' + i))
        ).collect(Collectors.toSet());
    public static final Map<Character,Long> DEFAULT_AMOUNTS = LATIN_LETTERS.stream().collect(Collectors.toMap(Function.identity(), c-> 1L));

    public String sample(InputStream inputStream, int sampleLength) {
        Map<Character, Long> amounts = countLatinLetters(inputStream);
        if (amounts.isEmpty()) {
            amounts = DEFAULT_AMOUNTS;
        }
        Sampler<Character> sampler = new Sampler<>(amounts);
        List<Character> sample = sampler.sample(sampleLength);
        String sampleString = sample.stream().map(String::valueOf).collect(Collectors.joining());
        return sampleString;
    }

    public Map<Character, Long> countLatinLetters(InputStream inputStream) {
        Map<Character, Long> result = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while (reader.ready()) {
                char ch = (char)reader.read();
                if (LATIN_LETTERS.contains(ch)) {
                    result.merge(ch, 1L, (a,b)->a + b);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Sample length not found");
            return;
        }
        if (!args[0].matches("\\d+")) {
            System.out.println("Sample length is invalid");
            return;
        }

        int sampleLength = Integer.valueOf(args[0]);

        String sampleString = new StreamSampler().sample(System.in, sampleLength);

        System.out.println(sampleString);
    }

}
