import java.util.*;

public class Part_1{
    // Static variable to store the input array of N numbers
    private static int nums[];

    // Static method to find the top K frequent numbers
    public static List<Integer> topKFrequent(int k) {
        // Sort the array to ease frequency counting
        Arrays.sort(nums);

        // Create a set to hold unique numbers
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        // Create a HashMap to store the frequency of each number
        Map<Integer, Integer> numMap = new TreeMap<>();
        for (int num : numSet) {
            int count = 0;
            for (int n : nums) {
                if (n == num) {
                    count++;
                }
            }
            numMap.put(num, count);
        }

        // Sort the entries in the map by their values (frequency) in descending order
        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(numMap.entrySet());
        System.out.println("Before: " + sortedList);
        sortedList.sort((a, b) -> {
            int freqCompare = b.getValue().compareTo(a.getValue());
            if (freqCompare == 0) {
                return b.getKey().compareTo(a.getKey()); // Sort by number in reverse order if frequencies are equal
            }
            return freqCompare;
        });
        System.out.println("After: " + sortedList);

        // Get the top k frequent numbers
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(sortedList.get(i).getKey());
        }

        return result;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Initialize the static variable with input array
        //nums = new int[]{1, 1, 1, 2, 2, 3, 4, 4, 4, 4, 5};
        int k=4;
        //nums = new int[]{3, 1, 4, 4, 5, 2, 6, 1};
        nums = new int[]{7, 10, 11, 5, 2, 5, 5, 7, 11, 8, 9};
        // Call the static method and print the top 3 frequent numbers
        List<Integer> topK = topKFrequent(k);
        System.out.println("Top " + k + " Frequent Numbers: " + topK);
    }
}
