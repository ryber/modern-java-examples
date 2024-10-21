package io.ryber;

class Utils {
    static boolean isPrime(int number) {
        // Handle special cases
        if (number <= 1) {
            return false; // 0 and 1 are not prime numbers
        }
        if (number <= 3) {
            return true; // 2 and 3 are prime numbers
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false; // Exclude multiples of 2 and 3
        }

        // Check for factors from 5 to the square root of the number
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false; // Found a factor, not prime
            }
        }
        return true; // No factors found, it's prime
    }
}
