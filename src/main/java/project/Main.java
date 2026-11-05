package project;

import project.queue.BlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(3);
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        Runnable producer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    int val = (int) (Math.random() * 100);
                    System.out.println("[Продюсер] "
                            + Thread.currentThread().getName() + " пытается положить: " + val);

                    queue.enqueue(val);

                    System.out.println("[Продюсер] "
                            + Thread.currentThread().getName()
                            + " -> ПОЛОЖИЛ: " + val
                            + " (Размер очереди: " + queue.size() + ")");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        Runnable consumer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    System.out.println("[Потребитель] " +
                            Thread.currentThread().getName() + " ждет элемент...");

                    int val = queue.dequeue();

                    System.out.println("[Потребитель] "
                            + Thread.currentThread().getName()
                            + " <- ЗАБРАЛ: " + val + " (Размер очереди: " + queue.size() + ")");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        threadPool.execute(producer);
        threadPool.execute(producer);
        threadPool.execute(consumer);
        threadPool.execute(consumer);
        threadPool.shutdown();
    }
}