import java.util.*;

// customer class to manage customer details
class Customer {
    int id;
    String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name;
    }
}

// product class to represent products
class Product {
    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Price: " + price;
    }
}

// order class to handle order placement
class Order {
    int orderId;
    int customerId;
    String productName;
    Date deliveryDate;

    public Order(int orderId, int customerId, String productName, Date deliveryDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productName = productName;
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Customer ID: " + customerId + ", Product: " + productName + ", Delivery Date: " + deliveryDate;
    }
}

// comparator to sort products by price
class ProductPriceComparator implements Comparator<Product> {
    public int compare(Product p1, Product p2) {
        return Double.compare(p1.price, p2.price);
    }
}

public class Lab_7 {
    public static void main(String[] args) {
        // initialize data structures
        ArrayList<Customer> customerList = new ArrayList<>();
        HashMap<Integer, Product> productMap = new HashMap<>();
        HashSet<Product> productSet = new HashSet<>();
        TreeSet<Product> productTreeSet = new TreeSet<>(new ProductPriceComparator());
        ArrayList<Order> orderList = new ArrayList<>();

        // add sample data
        System.out.println("adding initial data...\n");
        customerList.add(new Customer(1, "a"));
        customerList.add(new Customer(2, "b"));
        customerList.add(new Customer(3, "c"));

        productMap.put(101, new Product(101, "laptop", 1000.00));
        productMap.put(102, new Product(102, "phone", 500.00));
        productMap.put(103, new Product(103, "headphones", 200.00));

        productSet.add(new Product(101, "laptop", 1000.00));
        productSet.add(new Product(102, "phone", 500.00));
        productSet.add(new Product(103, "headphones", 200.00));

        // add to treeset for sorting
        productTreeSet.addAll(productSet);

        orderList.add(new Order(1, 1, "laptop", new Date()));
        orderList.add(new Order(2, 2, "phone", new Date()));
        orderList.add(new Order(3, 3, "headphones", new Date()));

        // menu-driven program
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nmenu:");
            System.out.println("1. display customers");
            System.out.println("2. display products sorted by price");
            System.out.println("3. display orders");
            System.out.println("4. add new customer");
            System.out.println("5. exit");
            System.out.print("enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\ndisplaying all customers:");
                    for (Customer c : customerList) {
                        System.out.println(c);
                    }
                    break;

                case 2:
                    System.out.println("\ndisplaying products sorted by price:");
                    for (Product p : productTreeSet) {
                        System.out.println(p);
                    }
                    break;

                case 3:
                    System.out.println("\ndisplaying all orders:");
                    for (Order o : orderList) {
                        System.out.println(o);
                    }
                    break;

                case 4:
                    System.out.println("\nadding a new customer...");
                    System.out.print("enter customer id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("enter customer name: ");
                    String name = scanner.nextLine();
                    Customer newCustomer = new Customer(id, name);
                    customerList.add(newCustomer);
                    System.out.println("new customer added successfully.");
                    break;

                case 5:
                    System.out.println("exiting the application...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("invalid choice. try again.");
            }
        }
    }
}
