import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class AlphabetWarGame {
    // Strength maps for left and right side letters
    Map<Character, Integer> leftSideStrengths;
    Map<Character, Integer> rightSideStrengths;

    // Default constructor with preset strengths
    AlphabetWarGame() {
        leftSideStrengths = new HashMap<>();
        rightSideStrengths = new HashMap<>();
    
        leftSideStrengths.put('w', 4);
        leftSideStrengths.put('p', 3);
        leftSideStrengths.put('b', 2);
        leftSideStrengths.put('s', 1);
        
        rightSideStrengths.put('m', 4);
        rightSideStrengths.put('q', 3);
        rightSideStrengths.put('d', 2);
        rightSideStrengths.put('z', 1);
    }

    // Constructor to allow custom strengths
    AlphabetWarGame(Map<Character, Integer> leftSide, Map<Character, Integer> rightSide) {
        this.leftSideStrengths = leftSide;
        this.rightSideStrengths = rightSide;
    }
    //Method To iniitally print the respective character and it's strengths 
    void print_word_strengths(){
        System.out.println("Left Side: " + Arrays.asList(leftSideStrengths));
        System.out.println("Right Side: "  + Arrays.asList(rightSideStrengths));
    }
    
    // Method to determine the winner from a single word
    String alphabetWar(String word) {
        System.out.println("");
        print_word_strengths();
        int leftScore = 0;
        int rightScore = 0;
        System.out.println("Word: " + word);
        for (char c : word.toCharArray()) {

            if (leftSideStrengths.containsKey(c)) {
                leftScore += leftSideStrengths.get(c);
            }
            
            else if (rightSideStrengths.containsKey(c)) {
                rightScore += rightSideStrengths.get(c);
            }
        }
        
        return winner(leftScore, rightScore);
    }

    // Method to determine the winner from separate left and right words
    String alphabetWar(String leftWord, String rightWord) {
        System.out.println("");
        print_word_strengths();
        System.out.println("Left word:" + leftWord + ", Right word:" + rightWord);
        int leftScore = calculateScore(leftWord, leftSideStrengths);
        int rightScore = calculateScore(rightWord, rightSideStrengths);
        
        return winner(leftScore, rightScore);
    }

    //method to calculate score for a given word based on strengths
    int calculateScore(String word, Map<Character, Integer> strengths) {
        int score = 0;
        for (char c : word.toCharArray()) {
            if (strengths.containsKey(c)) {
                score += strengths.get(c);
            }
        }
        return score;
    }
    // Determine the winner
    String winner(int lScore, int rScore){

        System.out.println("Left Score: " + lScore + ", Right Score: " + rScore);
        System.out.print("Result: ");
        if (lScore > rScore) {
            return "Left side wins!";
        } else if (rScore > lScore) {
            return "Right side wins!";
        } else {
            return "Let's fight again!";
        }
    }
    
}

public class Part_2{

    public static void main(String[] args) {
        // Test cases using default strengths
        AlphabetWarGame game = new AlphabetWarGame();
        System.out.println(game.alphabetWar("z"));              // Right side wins!
        System.out.println(game.alphabetWar("zdqmwpbs"));       // Let's fight again!
        System.out.println(game.alphabetWar("wwwwwwz"));        // Left side wins!

        // Test case using custom strengths
        Map<Character, Integer> customLeft = new HashMap<>();
        Map<Character, Integer> customRight = new HashMap<>();
        customLeft.put('a', 5);  // Custom strength for 'a'
        customRight.put('b', 5); // Custom strength for 'b'
        AlphabetWarGame customGame = new AlphabetWarGame(customLeft, customRight);
        System.out.println(customGame.alphabetWar("a", "b"));   // Let's fight again!
    }
}
