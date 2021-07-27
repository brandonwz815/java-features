package com.oracle.oracledevelopers.completablefuturethepromiseofjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * URL: https://www.youtube.com/watch?v=9ueIL0SwEWI
 */
public class Sample2B {

    private static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) {
        CompletableFuture<Integer> future = process();
//        future.orTimeout(4, TimeUnit.SECONDS);    // java 1.9+ feature

        sleep(4);

        future.complete(3);
//        future.completeExceptionally(new RuntimeException("Something went wrong"));
        System.out.println(ai.get());
    }

    public static CompletableFuture<Integer> process() {
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        cf.exceptionally(throwable -> handle(throwable))
                .thenApply(data -> data * 2)
                .thenApply(data -> data + 1)
                .thenApply(data -> ai.addAndGet(data))
                .thenAccept(System.out::println);
        return cf;
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
