import java.util.LinkedList;
import java.util.Queue;

class RestaurantOrderSystem {
    private static final int MAX_QUEUE_SIZE = 10;
    static final int TOTAL_ORDERS = 15;

    private final Queue<String> orderQueue = new LinkedList<>();
    private int orderCount = 0;

    public static void main(String[] args) {
        RestaurantOrderSystem system = new RestaurantOrderSystem();

        Thread chefThread = new Thread(new Chef(system), "Chef");
        Thread waiterThread = new Thread(new Waiter(system), "Waiter");

        chefThread.start();
        waiterThread.start();
    }

    public synchronized void placeOrder() throws InterruptedException {
        while (orderQueue.size() >= MAX_QUEUE_SIZE) {
            wait();
        }

        if (orderCount < TOTAL_ORDERS) {
            String order = "Order #" + (++orderCount);
            orderQueue.add(order);
            System.out.println(Thread.currentThread().getName() + " placed: " + order);
            notifyAll();
        }
    }

    public synchronized String pickOrder() throws InterruptedException {
        while (orderQueue.isEmpty()) {
            if (orderCount >= TOTAL_ORDERS) {
                return null;
            }
            wait();
        }

        String order = orderQueue.poll();
        System.out.println(Thread.currentThread().getName() + " picked: " + order);
        notifyAll();
        return order;
    }
}

class Chef implements Runnable {
    private final RestaurantOrderSystem system;

    public Chef(RestaurantOrderSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String order = system.pickOrder();
                if (order == null) break;

                System.out.println(Thread.currentThread().getName() + " is preparing " + order);
                Thread.sleep((long) (1000 + Math.random() * 2000));
                System.out.println(Thread.currentThread().getName() + " prepared " + order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Waiter implements Runnable {
    private final RestaurantOrderSystem system;

    public Waiter(RestaurantOrderSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < RestaurantOrderSystem.TOTAL_ORDERS; i++) {
                system.placeOrder();
                Thread.sleep((long) (1000 + Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
