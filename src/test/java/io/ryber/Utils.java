package io.ryber;

import java.util.stream.IntStream;

class Utils {
    static boolean isPrime(int n) {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }

    static int sumOfPrimes(int start, int end){
        return IntStream.range(start, end)
                .filter(Utils::isPrime)
                .sum();
    }
}
