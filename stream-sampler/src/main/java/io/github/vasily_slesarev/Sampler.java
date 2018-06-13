package io.github.vasily_slesarev;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Sampler<T> {
    private final ArrayList<T> keys = new ArrayList<>();
    private final ArrayList<Long> amountSums = new ArrayList<>();
    private final long totalAmount;

    public Sampler(Map<T, Long> amountsMap) {
        amountSums.add(0l);
        amountsMap.forEach((key, amount) -> {
            keys.add(key);
            long amountSum = amount + amountSums.get(amountSums.size() - 1);
            amountSums.add(amountSum);
        });
        totalAmount = amountSums.get(amountSums.size() - 1);
    }

    public T sampleOne(long random) {
        int index = Collections.binarySearch(amountSums, random);
        if (index >= 0) {
            return keys.get(index);
        } else {
            return keys.get(-index - 2);
        }
    }

    public List<T> sample(int sampleLength) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < sampleLength; ++i) {
            long random = ThreadLocalRandom.current().nextLong(totalAmount);
            T sample = sampleOne(random);
            result.add(sample);
        }
        return result;
    }

}
