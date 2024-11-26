package main.br.ufrn.imd;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Program settings
        int numProducers = 2; // Number of producer threads
        int numConsumers = 2; // Number of consumer threads
        int queueCapacity = 10; // Size of the queue

        // Thread-safe queue
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueCapacity);

        // Executor to manage threads
        ExecutorService executor = Executors.newCachedThreadPool();

        // Start producer threads
        for (int i = 0; i < numProducers; i++) {
            executor.execute(new Producer(queue));
        }

        // Start consumer threads
        for (int i = 0; i < numConsumers; i++) {
            executor.execute(new Consumer(queue));
        }

        // Shutdown the executor gracefully after some time
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }));
    }
}
