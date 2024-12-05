import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;   
class Denomination extends Thread{
    
    public int run(int[] coins, int sum){
        for (int coin : coins){
            for (int i = coin; i <= sum ; i++) {
                
            }
        }
    }
}
public class Part_1 {

   
    public static void main(String[] args) {
        Denomination obj = new Denomination();
        int[] coins = {1, 2, 3};
        int sum = 4;
        int max_combination = 0;
        int max_threads = coins.length;
        for(int i = 0; i < max_threads; i++) {
            max_combination+=obj.run((Arrays.copyOfRange(coins, i, max_threads-1)),sum);
        }
        // int sum1 = 4;
        // System.out.println("Total ways (Example 1): " + coinChangeWays(coins1, sum1));

        // int[] coins2 = {2, 5, 3, 6};
        // int sum2 = 10;
        // System.out.println("Total ways (Example 2): " + coinChangeWays(coins2, sum2));
        
    }
}
