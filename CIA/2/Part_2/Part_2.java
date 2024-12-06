import java.util.ArrayList;

public class Part_2 {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> max_queue = new ArrayList<>();
        int max_orders = 15;

        // chef thread
        Thread chefThread = new Thread(() -> {
            try {
                synchronized (max_queue) {
                    for (int i = 1; i <= max_orders; i++) {
                        while (max_queue.isEmpty()) {
                            max_queue.wait();
                        }
                        String order = max_queue.remove(0);
                        System.out.println("Picked Order no.: " + order);

                        // Simulate preparation time
                        System.out.println("Cooking: " + order);
                        Thread.sleep(3000);
                        System.out.println("Prepared: " + order);
                        max_queue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Waiter thread
        Thread waiterThread = new Thread(() -> {
            try {
                synchronized (max_queue) {
                    for (int i = 1; i <= max_orders; i++) {
                        while (max_queue.size() >= 10) {
                            max_queue.wait();
                        }
                        String order = "Order No." + i;
                        max_queue.add(order);
                        System.out.println("Order placed: " + order);
                        Thread.sleep(2000);
                        max_queue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start threads
        chefThread.start();
        waiterThread.start();
        chefThread.join();
        waiterThread.join();

        System.out.println("Thread Execution Completed");
    }
}
