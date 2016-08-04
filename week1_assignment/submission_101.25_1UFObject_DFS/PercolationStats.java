/**
 * Created by Aaron on 6/30/15.
 */
public class PercolationStats {
    private double[] thresholds;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        thresholds = new double[T];
        for (int k = 0; k < T; k++) {
            Percolation grid = new Percolation(N);
            double threshold = 0.0;
            while (!grid.percolates()) {
                int index = StdRandom.uniform(N * N);
                int i = index / N + 1;
                int j = index % N + 1;
                if (!grid.isOpen(i, j)) {
                    grid.open(i, j);
                    threshold += 1.0;
                }
            }
            thresholds[k] = threshold/(N * N);
        }
    }
    public double mean() {
        return StdStats.mean(thresholds);
    }
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {
        double T = thresholds.length;
        return mean() - 1.96 * stddev() / Math.pow(T, 0.5);
    }
    public double confidenceHi() {
        double T = thresholds.length;
        return mean() + 1.96 * stddev() / Math.pow(T, 0.5);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats PS = new PercolationStats(N, T);
        System.out.println("mean = " + PS.mean());
        System.out.println("stddev = " + PS.stddev());
        System.out.println("95% confidence interval = " + PS.confidenceLo() + ", " + PS.confidenceHi());
    }
}
