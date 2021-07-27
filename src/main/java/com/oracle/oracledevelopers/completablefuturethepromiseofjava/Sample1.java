package com.oracle.oracledevelopers.completablefuturethepromiseofjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * URL: https://www.youtube.com/watch?v=9ueIL0SwEWI
 */
public class Sample1 {

//    public static ForkJoinPool fjp = new ForkJoinPool(10);

    public static void main(String[] args) {
        System.out.println("main: " + Thread.currentThread());

        CompletableFuture<Integer> cf = create(); // runs on the ForkJoinPool thread
//        sleep(1);
        cf.thenAccept(data -> printIt(data));  // runs on the main thread, because of main thread sleep

        sleep(1);
        create().thenAccept(d -> printIt(d));  // runs on the ForkJoinPool thread
    }

    private static CompletableFuture<Integer> create() {
//        return CompletableFuture.supplyAsync(() -> compute(), fjp);
        return CompletableFuture.supplyAsync(() -> compute());
    }

    private static int compute() {
        System.out.println("Compute: "+ Thread.currentThread());
        return 2;
    }

    private static void printIt(int value) {
        System.out.println(value);
        System.out.println("printIt: " + Thread.currentThread());
    }

    private static void sleep(long t) {
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
