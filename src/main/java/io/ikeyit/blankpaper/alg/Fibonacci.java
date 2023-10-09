package io.ikeyit.blankpaper.alg;

/**
 * Dynamic programming example: Fibonacci
 */
public class Fibonacci {

    public static int fibonacci(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n should be more than 0");
        if (n == 0 || n == 1)
            return 1;
        int fn2 = 1, fn1 = 1, fn = 0;
        for (int i = 2; i <= n; i++) {
            fn = fn2 + fn1;
            int temp = fn1;
            fn1 = fn;
            fn2 = temp;
        }
        return fn;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            System.out.println(fibonacci(i));
    }
}
