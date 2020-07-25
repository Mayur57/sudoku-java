import java.util.*;

class GridException extends Exception {
    private static final long serialVersionUID = -5415033022553166581L;

    public GridException(String m) {
        super("\n\nAn exception occured: \n" + m);
    }
}

public class sudokuSolver {

    public static int INFINITY = 9999;
    public static int GRID_SIZE = 9;
    public static int SUBGRID_SIZE = 3;
    public static int TRIAL_COUNTER = 0;

    public static int countZeros(int[][] grid) {
        int zeroes = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    zeroes++;
                }
            }
        }
        return zeroes;
    }

    public static void recieveGrid(int[][] grid) {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = input.nextInt();
            }
        }
        input.close();
    }

    public static void parseGrid(int[][] grid) throws GridException {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] > 9 || grid[i][j] < 0) {
                    throw new GridException(
                            "InvalidGridMemberException => One or many members are out of range of the allowed bounds of Sudoku\nFirst invalid member encountered at position =>   "
                                    + "(" + (i + 1) + "," + (j + 1) + ")\n");
                }
            }
        }
    }

    public static void resetGrid(int[][] grid) {
        System.out.print("\n\nThe grid was flushed\n");
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = 0;
            }
        }
        printGrid(grid, "reset");
    }

    public static void printGrid(int[][] grid, String choice) {
        switch (choice) {
            case "input":
                System.out.print("\n\n--------------------------");
                System.out.print("\n>> The input grid is:\n");
                System.out.print("--------------------------\n");
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        System.out.print(grid[i][j] + "  ");
                    }
                    System.out.print("\n");
                }
                System.out.print("\n\nNumber of zeroes: " + countZeros(grid) + "/81");
                break;

            case "solution":
                System.out.print("\n\n--------------------------");
                System.out.print("\n>> The solution grid is:\n");
                System.out.print("--------------------------\n");
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        System.out.print(grid[i][j] + "  ");
                    }
                    System.out.print("\n");
                }
                break;

            case "reset":
                System.out.print("\n\n--------------------------\n");
                System.out.print("\n\n>> The grid was reset:\n");
                System.out.print("--------------------------\n");
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        System.out.print(grid[i][j] + "  ");
                    }
                    System.out.print("\n");
                }
                break;

            default:
        }

    }

    public static boolean safetyCheck(int[][] grid, int rowVal, int colVar, int candidate) {
        // Check if the number is already in the given ROW
        for (int i = 0; i < grid.length; i++) {
            if (grid[rowVal][i] == candidate) {
                return false;
            }
        }

        // Check if the number is already in the given COLUMN
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][colVar] == candidate) {
                return false;
            }
        }

        // Check if the number is already in the given SUBGRID
        int subGridRowStart = rowVal - (rowVal % SUBGRID_SIZE);
        int subGridColumnStart = colVar - (colVar % SUBGRID_SIZE);
        for (int i = subGridRowStart; i < subGridRowStart + SUBGRID_SIZE; i++) {
            for (int j = subGridColumnStart; j < subGridColumnStart + SUBGRID_SIZE; j++) {
                if (grid[i][j] == candidate) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] grid) {
        int rowVal = INFINITY;
        int colVal = INFINITY;
        boolean isEmpty = true;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    rowVal = i;
                    colVal = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // no empty space left
        if (isEmpty) {
            return true;
        }

        // else for each-row backtrack
        for (int candidate = 1; candidate <= GRID_SIZE; candidate++) {
            if (safetyCheck(grid, rowVal, colVal, candidate)) {
                grid[rowVal][colVal] = candidate;
                if (solveSudoku(grid)) {
                    // print(board, n);
                    return true;
                } else {
                    // replace it
                    grid[rowVal][colVal] = 0;
                }
            }
        }
        TRIAL_COUNTER++;
        return false;
    }

    public static void main(String args[]) {
        System.out.print("\n\n--------------------------\n");
        System.out.print("> Sudoku Solver (Java) v1.1\n");
        System.out.print("> By Mayur Bhoi\n");
        System.out.print("--------------------------\n");

        // int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        // recieveGrid(grid);

        int[][] grid = {
            { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
            { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
            { 0, 0, 3, 0, 1, 8, 0, 8, 0 },
            { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
            { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 5, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
            { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
        };

        try {
            parseGrid(grid);
            printGrid(grid, "input");
            if (solveSudoku(grid)) {
                printGrid(grid, "solution");
                System.out.print("\n\n---------------------------------------------------\n");
                System.out.print(">> This grid took " + TRIAL_COUNTER + " trials to obtain a solution.\n");
                System.out.print("---------------------------------------------------\n");
            } else {
                throw new GridException(
                        "InputGridNotSolvableException => Program did not find any solution to the input grid provided. Please check for any mistakes while entering the grid.\nNumber of successful trials before the exception occurred =>  " + TRIAL_COUNTER + "\n");
            }
        } catch (GridException exceptionObject) {
            System.out.print(exceptionObject.getMessage());
        }
    }
}