
import java.util.Arrays;

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
        // test case 1
        int[] coins1 = {1, 2, 5};
        int targetSum1 = 5;

        // test case 2
        int[] coins2 = {2, 5, 3, 6};
        int targetSum2 = 10;

        System.out.println("Test Case 1: Coins =" + Arrays.toString(coins1) + ", Sum = "+ targetSum1);
        runTestCase(coins1, targetSum1);

        System.out.println("\nTest Case 2: Coins" + Arrays.toString(coins2) + ", Sum = "+ targetSum2);
        runTestCase(coins2, targetSum2);
    }

    private static void runTestCase(int[] coins, int targetSum) throws InterruptedException {
        // create two threads to compute the same combinations concurrently
        CoinCombinationTask task1 = new CoinCombinationTask(coins, targetSum);
        CoinCombinationTask task2 = new CoinCombinationTask(coins, targetSum);

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        System.out.println("starting threads to compute combinations...");
        t1.start();
        t2.start();

        // wait for threads to finish
        t1.join();
        t2.join();

        // combine results (since both threads compute the same result, just take one)
        int result = task1.getResult();

        System.out.println("total number of ways to make sum = " + targetSum + " is: " + result);
    }
}
