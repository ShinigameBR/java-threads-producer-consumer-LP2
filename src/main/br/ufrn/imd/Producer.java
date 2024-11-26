package main.br.ufrn.imd;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final Random random = new Random();

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Generate a random number
                int number = random.nextInt(100);
                queue.put(number);
                System.out.println("Produced: " + number);

                // Sleep for a random time between 200ms and 800ms
                Thread.sleep(200 + random.nextInt(600));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}