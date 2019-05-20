package java_core.thread;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:ben.gu
 * @Date:2019/5/15 4:55 PM
 */
public class CompletableFutureTest {

    @Test
    public void testSupplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("before hello ...");
            return "Hello";
        });

        try {
            System.err.println("future.get():" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.err.println("end...");

    }

    @Test
    public void testComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        future.complete("word");
        try {
            System.err.println("future.get():" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.err.println("end...");

    }

    @Test
    public void testThenAccept() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("before hello ...");
            return "Hello";
        }).thenApply(s -> {
            System.err.println("s:" + s);
            return s;
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.err.println("end...");
    }

    @Test
    public void testAsyncAllOf() {

        CompletableFuture[] futures = new CompletableFuture[10];
       final  AtomicInteger c = new AtomicInteger(10);
        for (int i = 0; i < 10; i++) {

            futures[i] = CompletableFuture.supplyAsync(() -> {

                long time = 0L;
                try {
//                    time = (long) ((Math.random()*1000)+1000);
                    time = c.decrementAndGet()*1000;
                    System.err.println("time:" + time + ",before hello ...");
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Hello:" + time;
            }, Executors.newFixedThreadPool(10)).thenAcceptAsync(s -> {
                System.err.println("异步回调执行,s->"+s);
            });

        }

        CompletableFuture.allOf(futures).join();
        System.err.println("end...");
    }


    @Test
    public void testThenAcceptAllOf() {

        CompletableFuture[] futures = new CompletableFuture[10];
        for (int i = 0; i < 10; i++) {

            futures[i] = CompletableFuture.supplyAsync(() -> {

                long time = 0L;
                try {
                    time = (long) ((Math.random()*1000)+1000);
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("time:" + time + ",before hello ...");
                return "Hello:" + time;
            }).thenAccept(s -> {
                System.err.println("同步回调执行,s->"+s);
            });

        }

        CompletableFuture.allOf(futures).join();
        System.err.println("end...");
    }
}
