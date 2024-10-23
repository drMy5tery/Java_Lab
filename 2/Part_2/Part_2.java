
import java.util.Arrays;

class ShareTrader{
    public static int maxProfit = 0;
    public static int stock_prices[];
    public void setStock_prices(int day_n_prices[]){
        stock_prices = day_n_prices; 
    }
    public void displayStockprices() {
        System.out.println("Day n Stock Prices: " + Arrays.toString(stock_prices));
    }
    public static int findMaxProfit(){
      
        // Variables to store the maximum profit after  the first and second transactions
        int firstBuy = Integer.MIN_VALUE;
        int firstSell = 0;
        int secondBuy = Integer.MIN_VALUE;
        int secondSell = 0;

        for (int price : stock_prices) {
          
            //maximum profit after first buy 
            firstBuy = Math.max(firstBuy, -price);

            //maximum profit after first sell
            firstSell = Math.max(firstSell, firstBuy + price);

            //maximum profit after second buy
            secondBuy = Math.max(secondBuy, firstSell - price);

            //maximum profit after second sell
            secondSell = Math.max(secondSell, secondBuy + price);
            System.out.println("First_buy: " + firstBuy + ", First_sell: " + firstSell + ", Second_buy: " + secondBuy + ", Second_sell: " + secondSell);
        }
        maxProfit = secondSell;
        return maxProfit;
    
    }
} 

public class Part_2 {

    public static void main(String[] args) {
        ShareTrader day_n = new ShareTrader();
        //int stock_prices[] = {10, 22, 5, 75, 65, 80};
        int stock_prices[] = {2, 30, 15, 10, 8, 25, 80};
        day_n.setStock_prices(stock_prices);
        day_n.displayStockprices();
        int maxProfit = day_n.findMaxProfit();
        
        System.out.println("Max Profit Earned: " + maxProfit);
    }
    
}
