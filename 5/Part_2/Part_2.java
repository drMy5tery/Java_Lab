
import java.util.Arrays;


interface WaterConservationSystem{
    int calculateTrappedWater(int[] blockHeights);
}

abstract class RainySeasonConservation implements WaterConservationSystem {
}

class CityBlockConservation extends RainySeasonConservation{
    @Override
    public int calculateTrappedWater(int[] blockHeights) {
        int left, right, result, left_max, right_max;
        left = 0;
        right = blockHeights.length - 1;
        result = 0;
        left_max = blockHeights[left];
        right_max = blockHeights[right];
        while (left < right) {
            if (left_max <= right_max) {
                left++;
                left_max = Math.max(left_max,blockHeights[left]);
                result += left_max - blockHeights[left];
            }
            else{
                right--;
                right_max = Math.max(right_max,blockHeights[right]);
                result += right_max - blockHeights[right];
            }
        }
        return result;
    }
}
public class Part_2 {
    public static void main(String[] args) {
        CityBlockConservation blockConservation = new CityBlockConservation();
        int[] blockHeights = {3,0,0,2,0,4};
        int trapped_water = blockConservation.calculateTrappedWater(blockHeights);
        System.out.println("Array = " + Arrays.toString(blockHeights) + " Trapped Water = " + trapped_water);
        blockHeights = new int[]{3, 0, 2, 0, 4};
        trapped_water = blockConservation.calculateTrappedWater(blockHeights);
        System.out.println("Array = " + Arrays.toString(blockHeights) + " Trapped Water = " + trapped_water);
    }
}
