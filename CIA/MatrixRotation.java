import java.util.Scanner;

public class MatrixRotation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // added system.in
        System.out.println("Enter the number of rows for the matrix:");
        int rows = scanner.nextInt(); // changed to scanner.nextInt()
        System.out.println("Enter the number of columns for the matrix:");
        int cols = scanner.nextInt(); //changed to scanner.nextInt()
        int[][] inputMatrix = readMatrixInput(rows,cols,scanner);

        System.out.println("Input Matrix:");
        displayMatrix(inputMatrix);
        int[][] outputMatrix = rotateMatrix(inputMatrix); // added inputMatrix
        int[][] final_output_Matrix = applyCustomLogic(outputMatrix); // added final matrix
        System.out.println("\nOutput Matrix:");
        displayMatrix(final_output_Matrix); // added OutputMatrix
        scanner.close();
    }

    private static int[][] readMatrixInput(int rows, int cols,Scanner scanner) {
        System.out.println("Enter elements for the matrix:");
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                System.out.print("Matrix[" + (i + 1) + "][" + (j + 1) + "]: ");
                matrix[i][j] = scanner.nextInt(); // interchanged i and j
            }
        }
        return matrix;
    }

    private static int[][] rotateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotatedMatrix = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][i] = matrix[i][j];
                //System.out.println(rotatedMatrix[j][i]);
            }
        }

        // Reverse each row of the rotated matrix
        for (int i = 0; i < cols; i++) {
            reverseArray(rotatedMatrix[i]);
        }

        return rotatedMatrix;
    }
    private static void reverseArray(int[] array) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            // Swap elements at start and end indices
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            // Move indices towards the center
            start++;
            end--;
        }
    }
private static int[][] applyCustomLogic(int[][] matrix) 
{ 
    int rows = matrix.length; 
    int cols = matrix[0].length; 
    int[][] finalMatrix = new int[rows][cols]; 
    for (int i = 0; i < rows; i++)  {
        for (int j = 0; j < cols; j++) { 
            if (matrix[i][j] % 2 == 0) { 
                finalMatrix[i][j] = matrix[i][j] + 2; 
            }
            else{
                finalMatrix[i][j] = matrix[i][j] + 1;
            }
        }
    
    }
    return finalMatrix;
}

    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }}
