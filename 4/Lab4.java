abstract class Robber{
    public void RobbingClass(){
        System.out.println("MScAI&ML");
    }
    //Default Method
    public static void MachineLearning(){
        System.out.println("I love Machine Learning");
    }

     // Abstract methods with specified constraints
     abstract int RowHouses(int[] money);
     abstract int RoundHouses(int[] money);
     abstract int SquareHouse(int[] money);
     abstract int MuliHouseBuilding(int[][] money);
}

class JAVAProfessionalRobber extends Robber{
    @Override
    int RowHouses(int[] money) {
        if (money == null || money.length == 0) return 0;
        int prevMax = 0, currMax = 0;
        for (int amount : money) {
            int temp = currMax;
            currMax = Math.max(prevMax + amount, currMax);
            prevMax = temp;
        }
        return currMax;
    }

    @Override
    int RoundHouses(int[] money) {
        if (money == null || money.length == 0) return 0;
        if (money.length == 1) return money[0];
        return Math.max(robCircle(money, 0, money.length - 2),
                        robCircle(money, 1, money.length - 1));
    }

    @Override
    int SquareHouse(int[] money) {
        return RowHouses(money); // Same logic as RowHouses since constraints are identical
    }

    @Override
    int MuliHouseBuilding(int[][] houses) {
        if (houses == null || houses.length == 0) return 0;

        int totalMax = 0;
        for (int[] houseType : houses) {
            totalMax += RowHouses(houseType);
        }
        return totalMax;
    }

    //function for RoundHouses to handle circular cases
    private int robCircle(int[] money, int start, int end) {
        int prevMax = 0, currMax = 0;
        for (int i = start; i <= end; i++) {
            int temp = currMax;
            currMax = Math.max(prevMax + money[i], currMax);
            prevMax = temp;
        }
        return currMax;
    }
}

// Main class to test the implementation
public class Lab4 {
    public static void main(String[] args) {
        JAVAProfessionalRobber robber = new JAVAProfessionalRobber();

        // Calling default method
        //robber.MachineLearning();

        // Calling RobbingClass
        robber.RobbingClass();

        // Testing methods with provided test cases
        System.out.println("RowHouses([1,2,3,0]) = " + robber.RowHouses(new int[]{1, 2, 3, 0}));
        System.out.println("RoundHouses([1,2,3,4]) = " + robber.RoundHouses(new int[]{1, 2, 3, 4}));
        System.out.println("SquareHouse([5,10,2,7]) = " + robber.SquareHouse(new int[]{5, 10, 2, 7}));

        int[][] multiHouseBuilding = {
            {5, 3, 8, 2},  // Row Houses
            {10, 12, 7, 6}, // Row Houses
            {4, 9, 11, 5},  // Row Houses
            {8, 6, 3, 7}    // Row Houses
        };
        System.out.println("MultiHouseBuilding([5,3,8,2],[10,12,7,6],[4,9,11,5],[8,6,3,7]) = " +
            robber.MuliHouseBuilding(multiHouseBuilding));
    }
}
