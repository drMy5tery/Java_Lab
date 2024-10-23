1. **Frequency Counting**:
   - First, the array is analyzed to determine how many times each number appears. This process involves iterating over the entire array to count the occurrences of each unique number.
2. **Sorting Based on Frequency**:
   - After calculating the frequency of each number, the next step is to sort the numbers based on their frequency in descending order. This ensures that the most frequent numbers come first.
   - If two or more numbers have the same frequency, they are further sorted by their value in descending order, ensuring that larger numbers come before smaller ones when their frequencies match.
3. **Selecting the Top K Numbers**:
   - Once the numbers are sorted, the algorithm selects the top `K` numbers from this sorted list. These numbers represent the most frequent ones from the original array.