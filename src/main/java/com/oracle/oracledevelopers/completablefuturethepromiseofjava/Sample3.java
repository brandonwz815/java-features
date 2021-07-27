package com.oracle.oracledevelopers.completablefuturethepromiseofjava;

import java.util.concurrent.CompletableFuture;

/**
 * URL: https://www.youtube.com/watch?v=9ueIL0SwEWI
 * URL: https://stackoverflow.com/questions/43019126/completablefuture-thenapply-vs-thencompose#43025751
 */
public class Sample3 {

    public static void main(String[] args) {
        create(2)
                .thenCombine(create(3), (r1, r2) -> r1 + r2)
                .thenAccept(System.out::println);

        create(2)
                .thenCompose(data -> create(data))
                .thenAccept(System.out::println);
    }

    public static CompletableFuture<Integer> create(int n) {
        return CompletableFuture.supplyAsync(() -> n);
    }
}
