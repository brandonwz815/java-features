package com.oracle.oracledevelopers.completablefuturethepromiseofjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * URL: https://www.youtube.com/watch?v=nDE1bsy10Yo&t=314s
 * <p>
 * This example works
 */
public class MyDefogTechSample {

    private static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) {
        CompletableFuture<Integer> cf1 = create(1);
        CompletableFuture<Integer> cf2 = create(2);
        CompletableFuture<Integer> cf3 = create(5);

        sleep(2);
//        cf1.complete(1);
        cf1.completeExceptionally(new RuntimeException("Something went wrong"));
        cf2.complete(2);
        cf3.complete(5);

        CompletableFuture cf = CompletableFuture.allOf(cf1, cf2, cf3);
//        try {
//            cf.get(4, TimeUnit.SECONDS); // throws an ExecutionException
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }

        cf1.thenAccept(System.out::println);
        System.out.println(ai.get());
    }

    private static CompletableFuture<Integer> create(int j) {
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        cf.orTimeout(j, TimeUnit.SECONDS)
//                .supplyAsync(() -> i)
                .thenApply(data -> {
                    System.out.println("aaa");
                    return ai.addAndGet(data);
                });
        return cf;
    }

    private static void sleep(long t) {
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
