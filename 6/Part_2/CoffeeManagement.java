// You are building a coffee shop simulation where baristas (producers) prepare coffee orders, and
// customers (consumers) pick them up. The counter can hold a maximum of 3 coffee cups at any
// time. Additionally, there is a coffee reviewer (an observer task) who randomly samples a coffee
// from the counter to rate its quality.
// ● Use multithreading to manage the interaction between baristas, customers, and the
// reviewer using wait() and notify() for synchronization.
// ● Baristas should stop making coffee if the counter is full, and customers should wait if the
// counter is empty.

// ● The reviewer should only attempt to sample when there is at least one coffee available. If
// the counter is empty, the reviewer should also wait.
// ● Define a custom exception CounterEmptyException that is raised if a customer or the
// reviewer tries to take a coffee when none are available.
// Write a program to simulate this enhanced producer-consumer scenario, ensuring all interactions
// are properly synchronized.
// Sample Test Inputs and Expected Output
// Inputs:
// 1. Baristas&#39; tasks (producers):
// ○ Barista 1: Prepares 2 coffees.
// ○ Barista 2: Prepares 3 coffees.
// 2. Customers&#39; tasks (consumers):
// ○ Customer 1: Picks up 1 coffee.
// ○ Customer 2: Picks up 2 coffees.
// ○ Customer 3: Picks up 1 coffee.
// 3. Coffee Reviewer task (observer):
// ○ Samples 1 coffee for review.
class CoffeeShop {

    private final int MAX_CAPACITY = 3;
    private int counter = 0;

    public synchronized void produceCoffee(int baristaId) throws InterruptedException {
        if (counter == MAX_CAPACITY) {
            System.out.println("Counter is full. Barista " + baristaId + " is waiting.");
            wait();
        }
        counter++;
        System.out.println("Barista " + baristaId + " prepared a coffee. Counter: " + counter);
        notifyAll();
    }

    public synchronized void consumeCoffee(int customerId) throws InterruptedException {
        if (counter == 0) {
            System.out.println("Counter is empty. Customer " + customerId + " is waiting.");
            wait();
        }
        counter--;
        System.out.println("Customer " + customerId + " picked up a coffee. Counter: " + counter);
        notifyAll();
    }

    public synchronized void reviewCoffee() throws InterruptedException {
        if (counter == 0) {
            System.out.println("Counter is empty. Reviewer is waiting.");
            wait();
        }
        counter--;
        System.out.println("Reviewer sampled a coffee. Counter: " + counter);
        notifyAll();
    }
}

class Barista extends Thread {

    private final CoffeeShop shop;
    private final int baristaId;
    private final int coffees;

    public Barista(CoffeeShop shop, int baristaId, int coffees) {
        this.shop = shop;
        this.baristaId = baristaId;
        this.coffees = coffees;
    }

    public void run() {
        try {
            for (int i = 0; i < coffees; i++) {
                shop.produceCoffee(baristaId);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Customer extends Thread {

    private final CoffeeShop shop;
    private final int customerId;
    private final int coffees;

    public Customer(CoffeeShop shop, int customerId, int coffees) {
        this.shop = shop;
        this.customerId = customerId;
        this.coffees = coffees;
    }

    public void run() {
        try {
            for (int i = 0; i < coffees; i++) {
                shop.consumeCoffee(customerId);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Reviewer extends Thread {

    private final CoffeeShop shop;

    public Reviewer(CoffeeShop shop) {
        this.shop = shop;
    }

    public void run() {
        try {
            shop.reviewCoffee();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class CoffeeManagement {

    public static void main(String[] args) {
        CoffeeShop shop = new CoffeeShop();

        new Barista(shop, 1, 2).start();
        new Barista(shop, 2, 3).start();

        new Customer(shop, 1, 1).start();
        new Customer(shop, 2, 2).start();
        new Customer(shop, 3, 1).start();

        new Reviewer(shop).start();
    }
}