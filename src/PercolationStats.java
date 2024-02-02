
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int n;
    private int trails;
    private double[] percList;
    public PercolationStats(int n, int trails){
        if (n<=0 || trails <=0){
            throw new IllegalArgumentException("n or trials is not valid");
        }
        this.n = n;
        this.trails = trails;

        percList = new double[this.trails];
        for (int trail=0; trail < this.trails; trail++){
            // make percolation a local variable, so when constructor returns, memory will release.
            // because it doesn't have to be an instance variable
            Percolation percolation = new Percolation(n);
            while((!percolation.percolates()) && (percolation.numberOfOpenSites()<n*n)){
                int i = StdRandom.uniformInt(n);
                int j = StdRandom.uniformInt(n);
                percolation.open(i+1, j +1);
                if (percolation.percolates()){
                    this.percList[trail] = (double) percolation.numberOfOpenSites()/(n*n);
                }
            }
        }

    }

    public double mean(){
        return StdStats.mean(percList);
    }

    public double stddev(){
        return StdStats.stddev(percList);
    }

    public double confidenceLo(){
        return mean() - 1.96 * stddev()/Math.sqrt(trails);
    }

    public double confidenceHi(){
        return mean() + 1.96 * stddev()/Math.sqrt(trails);
    }

    public static void main(String[] args){
//        int n = Integer.valueOf(args[0]);
//        int trails = Integer.valueOf(args[1]);
        int n = 20;
        int trails = 200;
//        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(n, trails);
        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");

    }

}
