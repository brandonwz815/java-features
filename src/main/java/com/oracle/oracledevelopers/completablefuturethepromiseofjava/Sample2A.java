package com.oracle.oracledevelopers.completablefuturethepromiseofjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * URL: https://www.youtube.com/watch?v=9ueIL0SwEWI
 */
public class Sample2A {

    private static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
//        future.completeOnTimeout(500, 3, TimeUnit.SECONDS);  // java 1.9+ feature
//        future.orTimeout(3, TimeUnit.SECONDS);    // java 1.9+ feature
//        future.orTimeout(4, TimeUnit.SECONDS);    // java 1.9+ feature

        process(future);
//        sleep(4);

        future.complete(3);  // ************************************* must be the future on line 15
//        future.completeExceptionally(new RuntimeException("Something went wrong"));
        System.out.println("ai: " + ai.get());
    }

    public static void process(CompletableFuture<Integer> future) {
        CompletableFuture<Integer> future1 = future
                .exceptionally(throwable -> handle(throwable))
                .thenApply(data -> data * 2)
                .thenApply(data -> data + 1)
                .thenApply(data -> ai.addAndGet(data));

        future1.thenAccept(System.out::println);
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
