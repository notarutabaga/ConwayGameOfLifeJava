import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int numRows, numCols;

        int[] dimensions = null;
        do {
            System.out.print("Enter dimensions (ROWSxCOLUMNS) between 10 and 20 inclusive or R for random: ");
            String input = scanner.nextLine();
            try {
                dimensions = getDimensions(input);
            } catch (Exception e) {
                System.out.println("\tInvalid input!\n");
            }
        } while(dimensions == null);

        numRows = dimensions[0];
        numCols = dimensions[1];
        Game game = new Game(numRows, numCols);
        game.initGame();

        Game temp = new Game(numRows, numCols);

        while (true) {
            game.printBoard();

            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    boolean current = game.getCurrentIndex(row, col);
                    int numAliveNeighbors = game.getNumAliveNeighbors(row, col);

                    if (current && numAliveNeighbors == 2 || numAliveNeighbors == 3) {
                        temp.setCurrentIndex(row, col, true);
                    } else if (!current && numAliveNeighbors == 3) {
                        temp.setCurrentIndex(row, col, true);
                    } else {
                        temp.setCurrentIndex(row, col, false);
                    }
                }
            }

            game = temp;
            temp = new Game(numRows, numCols);

            Thread.sleep(1000);
        }
    }

    public static int[] getDimensions(String input) throws Exception {
        int[] dimensions = new int[2];
        input = input.trim();

        if (input.equalsIgnoreCase("R")) {
            dimensions[0] = 10 + (int)(Math.random() * ((20 - 10) + 1));
            dimensions[1] = 10 + (int)(Math.random() * ((20 - 10) + 1));
        } else {
            String[] parsedInput = input.split("[xX]");
            if (parsedInput.length > 2) {
                throw new Exception();
            }
            dimensions[0] = Integer.parseInt(parsedInput[0]);
            dimensions[1] = Integer.parseInt(parsedInput[1]);

            if (dimensions[0] < 10 || dimensions[0] > 20 || dimensions[1] < 10 || dimensions[1] > 20) {
                throw new Exception();
            }
        }

        return dimensions;
    }
}

class Game {
    private boolean[][] board;
    private int numRows, numCols;

    public Game(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        board = new boolean[numRows][numCols];
    }

    public int getNumAliveNeighbors(int row, int col) {
        int numAliveNeighbors = 0;

        if (row - 1 >= 0 && col - 1 >= 0 && getCurrentIndex(row - 1, col - 1)) {
            numAliveNeighbors++;
        }

        if (row - 1 >= 0 && getCurrentIndex(row - 1, col)) {
            numAliveNeighbors++;
        }

        if (row - 1 >= 0 && col + 1 < numCols && getCurrentIndex(row - 1, col + 1)) {
            numAliveNeighbors++;
        }

        if (col - 1 >= 0 && getCurrentIndex(row, col - 1)) {
            numAliveNeighbors++;
        }

        if (col + 1 < numCols && getCurrentIndex(row, col + 1)) {
            numAliveNeighbors++;
        }

        if (row + 1 < numRows && col - 1 >= 0 && getCurrentIndex(row + 1, col - 1)) {
            numAliveNeighbors++;
        }

        if (row + 1 < numRows && getCurrentIndex(row + 1, col)) {
            numAliveNeighbors++;
        }

        if (row + 1 < numRows && col + 1 < numCols && getCurrentIndex(row + 1, col + 1)) {
            numAliveNeighbors++;
        }

        return numAliveNeighbors;
    }

    public void initGame() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                board[row][col] = randomBoolean();
            }
        }
    }

    // instead of using Random's nextBool method, this is separate to adjust the true:false ratio
    private boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    public void printBoard() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (board[row][col]) {
                    System.out.print("\u25A0 ");
                } else {
                    System.out.print("\u25A1 ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }

    public boolean getCurrentIndex(int row, int col) {
        return board[row][col];
    }

    public void setCurrentIndex(int row, int col, boolean bool) {
        board[row][col] = bool;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
}