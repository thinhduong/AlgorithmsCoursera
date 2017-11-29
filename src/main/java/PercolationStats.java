import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private int _n;
    private int _trials;
    private double[] _samples;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException();

        _n = n;
        _trials = trials;
        _samples = new double[trials];
        Run();
    }

    private void Run() {
        for (int i=0; i<_trials; i++) {
            Percolation percolation = new Percolation(_n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(_n) + 1;
                int col = StdRandom.uniform(_n) + 1;
                percolation.open(row, col);
            }
            _samples[i] = percolation.numberOfOpenSites() * 1.0 / (_n * _n);
        }
    }

    public double mean() {
        return StdStats.mean(_samples);
    }

    public double stddev() {
        return StdStats.stddev(_samples);
    }

    public double confidenceLo() {
        double avg = mean();
        double s = Math.sqrt(stddev());

        return avg - (1.96 * s / Math.sqrt(_trials));
    }

    public double confidenceHi() {
        double avg = mean();
        double s = stddev();

        return avg + (1.96 * s / Math.sqrt(_trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[args.length - 2]);
        int trails = Integer.parseInt(args[args.length - 1]);
        PercolationStats stats = new PercolationStats(n, trails);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
