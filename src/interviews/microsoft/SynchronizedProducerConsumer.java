package interviews.microsoft;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedProducerConsumer {
    private static final int QUEUE_CAPACITY = 10;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.execute(new Producer(queue));
        executor.execute(new Producer(queue));

        executor.execute(new Consumer(queue));
        executor.execute(new Consumer(queue));
        executor.execute(new Consumer(queue));

        Thread.sleep(5000);

        executor.shutdownNow();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("All threads stopped");
    }

    static class Producer implements Runnable {
        private int id;
        private final Queue<Integer> queue;
        private final Random random = new Random();

        public Producer(Queue<Integer> queue) {
            this.id = random.nextInt(10);
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int num = random.nextInt(1001);
                    synchronized (lock) {
                        while (queue.size() >= QUEUE_CAPACITY) {
                            lock.wait();
                        }
                        queue.add(num);
                        System.out.println("Producer: " + id + " produced " + num + " (Queue size: " + queue.size() + ")");
                        lock.notifyAll();
                    }
                    Thread.sleep(random.nextInt(500)); // Simulate work
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " producer " + id + " interrupted");
            }
        }
    }

    static class Consumer implements Runnable {
        private int id;
        private final Queue<Integer> queue;
        private final Random random = new Random();

        public Consumer(Queue<Integer> queue) {
            this.id = random.nextInt(10);
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Integer num;

                    synchronized (lock) {
                        while (queue.isEmpty()) {
                            lock.wait();
                        }
                        num = queue.remove();
                        System.out.println("Consumer: " + id + " consumed " + num + " (Queue size: " + queue.size() + ")");
                        lock.notifyAll();
                    }
                    Thread.sleep(random.nextInt(1000)); // Simulate work
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " consumer " + id + " interrupted");
            }
        }
    }
}