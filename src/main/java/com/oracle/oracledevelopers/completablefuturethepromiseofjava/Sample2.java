package com.oracle.oracledevelopers.completablefuturethepromiseofjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * URL: https://www.youtube.com/watch?v=9ueIL0SwEWI
 */
public class Sample2 {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
//        future.completeOnTimeout(500, 3, TimeUnit.SECONDS);  // java 1.9+ feature
//        future.orTimeout(3, TimeUnit.SECONDS);    // java 1.9+ feature
        future.orTimeout(4, TimeUnit.SECONDS);    // java 1.9+ feature

        process(future);
        sleep(4);
        
        future.complete(3);
//        future.completeExceptionally(new RuntimeException("Something went wrong"));
    }

    public static void process(CompletableFuture<Integer> future) {
        future
//                .exceptionally(throwable -> handle(throwable))
                .exceptionally(Sample2::handle)
                .thenApply(data -> data * 2)
                .thenApply(data -> data + 1)
                .thenAccept(System.out::println);
    }

    private static int handle(Throwable throwable) {
        System.out.println("ERROR: " + throwable);
        return 100;
    }

    private static void sleep(long t) {
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
