interface PairSum{
    void pairSum();  // interface function
}

class CalcPairs implements PairSum{
    int[] arr;
    int target;

    CalcPairs(int []arr, int target){
        this.arr = arr;
        this.target = target;
    }
    @Override
    public void pairSum() {
        int arr_lenghth = arr.length;
        System.out.print("Pairs: "); 
        //calculates using two loop where 2nd loop starts from the +1 index of 1st
        for (int i = 0; i < arr_lenghth; i++){
            for (int j = i+1; j < arr_lenghth;j++){
                int sum = arr[i] + arr[j];
                if (sum == target) {
                    System.out.print("("+arr[i]+","+arr[j]+"),");
            }   }
        }
    
    }
}

public class Part_1 {
    public static void main(String[] args) {
        int[] arr1 = {1,5,7,-1,5};
        int[] arr2 = {2,4,3,5,6};
        int target1 = 6;
        int target2 = 8;
        CalcPairs pair1 = new CalcPairs(arr1, target1);
        CalcPairs pair2 = new CalcPairs(arr2, target2);
        pair1.pairSum();
        pair2.pairSum();
    }
}
