/**
 * Created by Aaron on 6/30/15.
 */
public class Percolation {
    private boolean[][] sites;
    private WeightedQuickUnionUF UF, UF2;
    public Percolation(int N) {
        if (N < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        UF = new WeightedQuickUnionUF(N * N + 1);
        UF2 = new WeightedQuickUnionUF(N * N + 2);
        sites = new boolean[N][N];
    }
    public void open(int i, int j) {
        int N = sites[0].length;
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        sites[i-1][j-1] = true;
        if (i - 1 >= 1 && sites[i - 2][j - 1]) {
            UF.union((i - 1) * N + j - 1, (i - 2) * N + j - 1);
            UF2.union((i - 1) * N + j - 1, (i - 2) * N + j - 1);
        }
        if (j - 1 >= 1 && sites[i - 1][j - 2]) {
            UF.union((i - 1) * N + j - 1, (i - 1) * N + j - 2);
            UF2.union((i - 1) * N + j - 1, (i - 1) * N + j - 2);
        }
        if (i + 1 <= N && sites[i][j - 1]) {
            UF.union((i - 1) * N + j - 1, i * N + j - 1);
            UF2.union((i - 1) * N + j - 1, i * N + j - 1);
        }
        if (j + 1 <= N && sites[i - 1][j]) {
            UF.union((i - 1) * N + j - 1, (i - 1) * N + j);
            UF2.union((i - 1) * N + j - 1, (i - 1) * N + j);
        }
        if (i == 1) {
            UF.union((i - 1) * N + j - 1, N * N);
            UF2.union((i - 1) * N + j - 1, N * N);
        }
        if (i == N) {
            UF2.union((i - 1) * N + j - 1, N * N + 1);
        }
    }
    public boolean isOpen(int i, int j) {
        int N = sites[0].length;
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return sites[i-1][j-1];
    }
    public boolean isFull(int i, int j) {
        int N = sites[0].length;
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return UF.connected((i - 1) * N + j - 1, N * N);
    }
    public boolean percolates() {
        int N = sites[0].length;
        return UF2.connected(N * N, N * N + 1);
    }

    public static void main(String[] args) {
        int N = 3;
        Percolation newGrid = new Percolation(N);
        newGrid.open(2, 1);
        newGrid.open(2, 2);
        newGrid.open(3, 2);
        System.out.println(newGrid.percolates());
    }
}
