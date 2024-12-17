import java.util.*;

// class to calculate combinations for a given range of coins
class CoinCombinationTask implements Runnable {
    int[] coins; // coins array
    int sum;     // target sum
    int result;  // stores the result for this thread

    public CoinCombinationTask(int[] coins, int sum) {
        this.coins = coins;
        this.sum = sum;
        this.result = 0;
    }

    // helper function to count combinations (basic recursion)
    private int countCombinations(int index, int target) {
        if (target == 0) return 1;  // found a valid way
        if (index >= coins.length || target < 0) return 0; // invalid case

        // case 1: include the current coin
        int include = countCombinations(index, target - coins[index]);

        // case 2: exclude the current coin and move to the next
        int exclude = countCombinations(index + 1, target);

        return include + exclude;
    }

    @Override
    public void run() {
        result = countCombinations(0, sum);
    }

    public int getResult() {
        return result;
    }
}

public class Part_1 {
    public static void main(String[] args) throws InterruptedException {
        
        int[] coins1 = {1, 2, 3};
        int targetSum1 = 4;

        
        int[] coins2 = {2, 5, 3, 6};
        int targetSum2 = 10;

        System.out.println("Test Case 1: Coins = {1, 2, 3}, Sum = 4");
        runTestCase(coins1, targetSum1);

        System.out.println("\nTest Case 2: Coins = {2, 5, 3, 6}, Sum = 10");
        runTestCase(coins2, targetSum2);
    }

    private static void runTestCase(int[] coins, int targetSum) throws InterruptedException {
        // split coins for two threads
        int mid = coins.length / 2;

        int[] coins1 = Arrays.copyOfRange(coins, 0, mid);
        int[] coins2 = Arrays.copyOfRange(coins, mid, coins.length);

        // create two threads to process combinations
        CoinCombinationTask task1 = new CoinCombinationTask(coins1, targetSum);
        CoinCombinationTask task2 = new CoinCombinationTask(coins2, targetSum);

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        System.out.println("starting threads to compute combinations...");
        t1.start();
        t2.start();

        // wait for threads to finish
        t1.join();
        t2.join();

        // combine results from both threads
        int result = task1.getResult() + task2.getResult();

        System.out.println("total number of ways to make sum = " + targetSum + " is: " + result);
    }
}
