import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Aaron on 8/6/15.
 */
public class Solver {
    // fields
    private Node initial;
    private Node last;
    private int numOfMoves;
    private Stack<Board> solutionTrace;

    // private classes
    private class Node {
        private Board board;
        private Node prev;
        private int moves;
        public Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }
        private class NeighborNodesIterator implements Iterator<Node> {
            private Iterator<Board> boardNeighborIterator;
            public NeighborNodesIterator() {
                boardNeighborIterator = board.neighbors().iterator();
            }
            public boolean hasNext() {
                return boardNeighborIterator.hasNext();
            }
            public Node next() {
                while (hasNext()) {
                    Node next = new Node(boardNeighborIterator.next(), Node.this, Node.this.moves + 1);
                    return next;
                }
                return null;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        private class NeighborNodesIterable implements Iterable<Node> {
            public Iterator<Node> iterator() {
                return new NeighborNodesIterator();
            }
        }
        public Iterable<Node> neighbors() {
            return new NeighborNodesIterable();
        }
    }

    private class HammingComparator implements Comparator<Node> {
        public int compare(Node node1, Node node2) {
            if (node1.board.hamming() + node1.moves < node2.board.hamming() + node2.moves) {
                return -1;
            } else if (node1.board.hamming() + node1.moves > node2.board.hamming() + node2.moves) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    private class ManhattanComparator implements Comparator<Node> {
        public int compare(Node node1, Node node2) {
            if (node1.board.manhattan() + node1.moves < node2.board.manhattan() + node2.moves) {
                return -1;
            } else if (node1.board.manhattan() + node1.moves > node2.board.manhattan() + node2.moves) {
                return 1;
            } else if (node1.board.hamming() < node2.board.hamming()) {
                return -1;
            } else if (node1.board.hamming() > node2.board.hamming()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    private class SolutionIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return solutionTrace.iterator();
        }
    }

    // API as required
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Solver constructor does not take null as argument!");
        }
        this.initial = new Node(initial, null, 0);
        solutionTrace = new Stack<Board>();
        if (!isSolvable()) {
            numOfMoves = -1;
            return;
        }
        numOfMoves = last.moves;
        do {
            solutionTrace.push(last.board);
            last = last.prev;
        } while (last != null);
    }
    public boolean isSolvable() {
        MinPQ<Node> pqOfThis = new MinPQ<Node>(new ManhattanComparator());
        MinPQ<Node> pqOfTwin = new MinPQ<Node>(new ManhattanComparator());
        Node twin = new Node(initial.board.twin(), null, 0);
        pqOfThis.insert(initial);
        pqOfTwin.insert(twin);
        while (true) {
            Node currentOfThis = pqOfThis.delMin();
            Node currentOfTwin = pqOfTwin.delMin();
            if (currentOfTwin.board.isGoal()) {
                return false;
            }
            if (currentOfThis.board.isGoal()) {
                last = currentOfThis;
                return true;
            }
            for (Node neighbor: currentOfThis.neighbors()) {
                if (currentOfThis.prev == null) {
                    pqOfThis.insert(neighbor);
                } else if (!neighbor.board.equals(currentOfThis.prev.board)) {
                    pqOfThis.insert(neighbor);
                }
            }
            for (Node neighbor: currentOfTwin.neighbors()) {
                if (currentOfTwin.prev == null) {
                    pqOfTwin.insert(neighbor);
                } else if (!neighbor.board.equals(currentOfTwin.prev.board)) {
                    pqOfTwin.insert(neighbor);
                }
            }
        }
    }
    public int moves() {
        return numOfMoves;
    }
    public Iterable<Board> solution() {
        if (solutionTrace.isEmpty()) {
            return null;
        }
        return new SolutionIterable();
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        System.out.println("twin" + initial.twin());
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
