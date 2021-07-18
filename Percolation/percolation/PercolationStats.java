// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;

/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 6/18/2021
 *  Description: PercolationStats Class
 **************************************************************************** */

public class PercolationStats {

    private final double[] trialResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        trialResults = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int k = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(k, j)) {
                    p.open(k, j);
                }
            }

            trialResults[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trialResults.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trialResults.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, t);

        String confidence = "[" + p.confidenceLo() + ", " + p.confidenceHi() + "]";
        StdOut.println("mean                    = " + p.mean());
        StdOut.println("stddev                  = " + p.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
