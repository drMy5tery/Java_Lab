import java.util.ArrayList;
import java.util.Random;

public class Part_2 {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> counter = new ArrayList<>(); // shared resource
        final int MAX_CAPACITY = 3;

        
        Thread barista = new Thread(() -> {
            synchronized (counter) {
                for (int i = 1; i <= 5; i++) { 
                    if (counter.size() == MAX_CAPACITY) { 
                        try {
                            System.out.println("Counter is full! Barista waiting...");
                            counter.wait(); // barista stops until counter has space
                        } catch (InterruptedException e) {
                            System.out.println("Barista Interrupted!"); 
                        }
                    }

                    String coffee = "Coffee-" + i;
                    counter.add(coffee); 
                    System.out.println("Barista made " + coffee);

                    counter.notifyAll(); // notify everyone that coffee is available

                    try {
                        Thread.sleep(1000); 
                    } catch (Exception ex) {
                        System.out.println("Barista sleep interrupted...");
                    }
                }
            }
        });

        
        Thread customer = new Thread(() -> {
            synchronized (counter) {
                for (int i = 1; i <= 5; i++) { 
                    if (counter.size() == 0) { 
                        try {
                            System.out.println("Counter is empty! Customer waiting...");
                            counter.wait(); 
                        } catch (InterruptedException e) {
                            System.out.println("Customer Interrupted!");
                        }
                    }

                    String coffee = counter.remove(0); // Pick first coffee
                    System.out.println("Customer picked " + coffee);

                    counter.notifyAll(); // Notify that counter has space

                    try {
                        Thread.sleep(1500); // Time to drink coffee
                    } catch (Exception ex) {
                        System.out.println("Customer sleep interrupted...");
                    }
                }
            }
        });

        // reviewer Thread (Observer)
        Thread reviewer = new Thread(() -> {
            synchronized (counter) {
                Random random = new Random();
                for (int i = 1; i <= 3; i++) { // reviewer checks 3 coffees
                    if (counter.size() == 0) { // check if counter is empty
                        try {
                            System.out.println("Counter empty! Reviewer waiting...");
                            counter.wait(); // reviewer waits if no coffee
                        } catch (InterruptedException e) {
                            System.out.println("Reviewer Interrupted!");
                        }
                    }

                    int index = random.nextInt(counter.size()); // random index
                    String coffee = counter.get(index);
                    System.out.println("Reviewer sampled " + coffee + " - Rating: " + (random.nextInt(5) + 1) + "/5");

                    counter.notifyAll(); // notify just in case
                    try {
                        Thread.sleep(2000); // time to review coffee
                    } catch (Exception ex) {
                        System.out.println("Reviewer sleep interrupted...");
                    }
                }
            }
        });

        // Start all threads
        barista.start();
        customer.start();
        reviewer.start();

        // Join all threads
        barista.join();
        customer.join();
        reviewer.join();

        System.out.println("Final Counter: " + counter); // Show what's left on the counter
    }
}
