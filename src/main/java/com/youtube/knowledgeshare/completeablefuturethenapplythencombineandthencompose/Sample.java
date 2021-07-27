package com.youtube.knowledgeshare.completeablefuturethenapplythencombineandthencompose;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * URL: https://www.youtube.com/watch?v=OteJazwJgo4
 * URL: https://stackoverflow.com/questions/43019126/completablefuture-thenapply-vs-thencompose#43025751
 */
public class Sample {

    public static void main(String[] args) {
        // CompletableFuture -> Non-Blocking Asynchronous programming Model in Java
        //   thenApply   (Just like a Stream map)
        //   thenCombine (Just like a Stream reduce)
        //   thenCompose (Just like a Stream flatmap)

        System.out.println("Start");

        CompletableFuture
                .supplyAsync(() -> compute(2))
                .thenApply(d -> compute2(d + 1))
                .thenApply(d -> d / 100)
                .thenAccept(System.out::println);

        CompletableFuture<Integer> completableFuture1 = create(2);
        CompletableFuture<Integer> completableFuture2 = create(3);
        completableFuture1.thenCombine(completableFuture2, Integer::sum)
                .thenAccept(System.out::println);

        /*
         * Brandon: not desirable to use thenApply here.
         * Since create() returns a CompletableFuture object,
         * using thenApply() results in a CompletableFuture<CompletableFuture<Integer>> object.
         * In this case, using thenCompose() to generate a CompletableFuture<Integer> object instead.
         */
        CompletableFuture<CompletableFuture<Integer>> completableFutureCompletableFuture = create(2).thenApply(d -> create(d + 1));
        completableFutureCompletableFuture.thenAccept(System.out::println);

        CompletableFuture<Integer> integerCompletableFuture = create(2).thenCompose(d -> create(d + 1));
        integerCompletableFuture.thenAccept(System.out::println);

        System.out.println("End");
        sleep(5);
    }

    private static int compute(int n) {
        return n * 10;
    }

    private static int compute2(int n) {
        return n * 10;
    }

    private static CompletableFuture<Integer> create(int d) {
        return CompletableFuture.supplyAsync(() -> compute(d));
    }

    private static void sleep(long t) {
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
