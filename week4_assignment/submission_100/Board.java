import java.util.Iterator;

/**
 * Created by Aaron on 8/6/15.
 */
public class Board {
    //fields
    private char[] cells;
    private int dimension;
    private int hamming;
    private int manhattan;
    private int xOfZero;
    private int yOfZero;

    // private classes
    private class BoardIterator implements Iterator<Board> {
        private int current;
        private boolean hasLeft;
        private boolean hasRight;
        private boolean hasUp;
        private boolean hasDown;
        private int numOfNeighbors;
        public BoardIterator() {
            current = 0;
            hasLeft = true;
            hasRight = true;
            hasUp = true;
            hasDown = true;
            numOfNeighbors = 4;
            int x = 0;
            int y = 0;
            for (int i = 0; i < dimension * dimension; i++) {
                if ((int) (cells[i]) == 0) {
                    x = toX(i);
                    y = toY(i);
                }
            }
            if (x == 0) {
                hasUp = false;
                numOfNeighbors--;
            }
            if (x == dimension - 1) {
                hasDown = false;
                numOfNeighbors--;
            }
            if (y == 0) {
                hasLeft = false;
                numOfNeighbors--;
            }
            if (y == dimension - 1) {
                hasRight = false;
                numOfNeighbors--;
            }
        }
        public boolean hasNext() {
            return current < numOfNeighbors;
        }
        public Board next() {
            if (hasNext()) {
                current++;
                if (hasLeft) {
                    hasLeft = false;
                    return Board.this.moveLeft();
                }
                if (hasRight) {
                    hasRight = false;
                    return Board.this.moveRight();
                }
                if (hasUp) {
                    hasUp = false;
                    return Board.this.moveUp();
                }
                if (hasDown) {
                    hasDown = false;
                    return Board.this.moveDown();
                }
            }
            return null;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported!");
        }
    }
    private class BoardIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new BoardIterator();
        }
    }

    // API as required
    public Board(int[][] blocks) {
        dimension = blocks[0].length;
        cells = new char[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cells[toCellNum(i, j)] = (char) (blocks[i][j]);
                if (blocks[i][j] == 0) {
                    this.xOfZero = i;
                    this.yOfZero = j;
                }
            }
        }
        hamming = calcHamming();
        manhattan = calcManhattan();
    }

    //helper methods
    private int toCellNum(int x, int y) {
        return x * dimension + y;
    }
    private int toX(int i) {
        return i/dimension;
    }
    private int toY(int i) {
        return i % dimension;
    }
    private void exch(int i, int j) {
        this.xOfZero = toX(j);
        this.yOfZero = toY(j);
        char temp = cells[i];
        cells[i] = cells[j];
        cells[j] = temp;
        setHammingAfterExch(i, j);
        setManhattanAfterExch(i, j);
    }
    private Board copy() {
        int[][] blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension * dimension; i++) {
            blocks[toX(i)][toY(i)] = (int) (cells[i]);
        }
        Board newBoard = new Board(blocks);
        newBoard.hamming = this.hamming;
        newBoard.manhattan = this.manhattan;
        newBoard.xOfZero = this.xOfZero;
        newBoard.yOfZero = this.yOfZero;
        return newBoard;

    }
    private int calcHamming() {
        int hammingDistance = 0;
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (cells[i]) != 0 && (int) (cells[i]) - 1 != i) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }
    private int calcManhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (cells[i]) != 0) {
                int x1 = toX((int) (cells[i]) - 1);
                int x2 = toX(i);
                int y1 = toY((int) (cells[i]) - 1);
                int y2 = toY(i);
                manhattanDistance += (Math.abs(x2 - x1) + Math.abs(y2 - y1));
            }
        }
        return manhattanDistance;
    }
    private void setHammingAfterExch(int i, int j) {
        int original = 0;
        int exchanged = 0;
        if ((int) (cells[i]) - 1 != j) {
            original = 1;
        }
        if ((int) (cells[i]) - 1 != i) {
            exchanged = 1;
        }
        this.hamming += (exchanged - original);
    }
    private void setManhattanAfterExch(int i, int j) {
        int original = 0;
        int exchanged = 0;
        int x1 = toX((int) (cells[i]) - 1);
        int x2 = toX(j);
        int y1 = toY((int) (cells[i]) - 1);
        int y2 = toY(j);
        original = (Math.abs(x2 - x1) + Math.abs(y2 - y1));
        x1 = toX((int) (cells[i]) - 1);
        x2 = toX(i);
        y1 = toY((int) (cells[i]) - 1);
        y2 = toY(i);
        exchanged = (Math.abs(x2 - x1) + Math.abs(y2 - y1));
        this.manhattan += (exchanged - original);
    }
    private Board moveLeft() {
        Board newBoard = this.copy();
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (newBoard.cells[i]) == 0) {
                newBoard.exch(i, i - 1);
                return newBoard;
            }
        }
        return null;
    }
    private Board moveRight() {
        Board newBoard = this.copy();
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (newBoard.cells[i]) == 0) {
                newBoard.exch(i, i + 1);
                return newBoard;
            }
        }
        return null;

    }
    private Board moveDown() {
        Board newBoard = this.copy();
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (newBoard.cells[i]) == 0) {
                newBoard.exch(i, i + dimension);
                return newBoard;

            }
        }
        return null;
    }
    private Board moveUp() {
        Board newBoard = this.copy();
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (newBoard.cells[i]) == 0) {
                newBoard.exch(i, i - dimension);
                return newBoard;
            }
        }
        return null;
    }

    public int dimension() {
        return dimension;
    }
    public int hamming() {
        return hamming;
    }
    public int manhattan() {
        return manhattan;
    }
    public boolean isGoal() {
        for (int i = 0; i < dimension * dimension; i++) {
            if ((int) (cells[i]) != 0 && (int) (cells[i]) - 1 != i) {
                return false;
            }
        }
        return true;
    }
    public Board twin() {
        Board twin = this.copy();
        for (int i = 0; i < dimension * dimension - 1; i++) {
            boolean nonZero = (int) (twin.cells[i]) != 0 && (int) (twin.cells[i + 1]) != 0;
            boolean isSameRow = toX(i) == toX(i + 1);
            if (nonZero && isSameRow) {
                twin.exch(i, i + 1);
                return twin;
            }
        }
        return null;
    }
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y instanceof Board) {
            Board that = (Board) y;
            if (this.xOfZero != that.xOfZero || this.yOfZero != that.yOfZero) {
                return false;
            }
            for (int i = 0; i < dimension * dimension; i++) {
                if (this.cells[i] != that.cells[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public Iterable<Board> neighbors() {
        return new BoardIterable();
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = dimension;
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", (int) (cells[toCellNum(i, j)])));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static void main(String[] args) {

        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] blocks2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board newBoard = new Board(blocks);
        Board newBoard2 = new Board(blocks2);
        System.out.println(newBoard);
        System.out.println("isGoal? " + newBoard.isGoal());
        System.out.println("hamming: " + newBoard.hamming());
        System.out.println("manhattan: " + newBoard.manhattan());
        System.out.println("\ntwin: ");
        System.out.println(newBoard.twin() + "\n");
        System.out.println("blocks equals to blocks2? " + newBoard2.equals(newBoard));
        System.out.println("\nneighbors: ");
        for (Board neighbor: newBoard.neighbors()) {
            System.out.println(neighbor);
            System.out.println("hamming: " + neighbor.hamming());
            System.out.println("manhattan: " + neighbor.manhattan());
        }
        System.out.println("blocks equals to blocks2? " + newBoard2.equals(newBoard));

    }
}
