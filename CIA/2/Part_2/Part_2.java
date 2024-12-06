import java.util.LinkedList;
import java.util.Queue;

public class Part_2 {
    public static void main(String[] args) {
        int max_queue = 10;
        int max_orders = 15;
        Queue<String> orderQueue = new LinkedList<>();
        Object lock = new Object();
        int[] orderCount = {0};

        Thread chefThread = new Thread(() -> {
            try {
                while (true) {
                    String order;
                    synchronized (lock) {
                        while (orderQueue.isEmpty()) {
                            if (orderCount[0] >= max_orders) return;
                            lock.wait();
                        }
                        order = orderQueue.poll();
                        System.out.println("Chef picked: " + order);
                        lock.notifyAll();
                    }
                    
                    System.out.println("Chef is preparing " + order);
                    Thread.sleep(3000);
                    System.out.println("Chef prepared: " + order);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Waiter thread
        Thread waiterThread = new Thread(() -> {
            try {
                while (orderCount[0] < max_orders) {
                    synchronized (lock) {
                        while (orderQueue.size() >= max_queue) {
                            lock.wait();
                        }
                        String order = "Order NO." + (++orderCount[0]);
                        orderQueue.add(order);
                        System.out.println("Waiter placed: " + order);
                        lock.notifyAll();
                    }
                    // Simulate delivery time
                    Thread.sleep((long) (3000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

    
        chefThread.start();
        waiterThread.start();

        try {
            chefThread.join();
            waiterThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All orders have been processed. Restaurant is closing!");
    }
}
