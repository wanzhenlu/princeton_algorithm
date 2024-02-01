
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int n;
    private int trails;
    private double[] percList;
    private Percolation percolation;

    public PercolationStats(int n, int trails){
        if (n<=0 || trails <=0){
            throw new IllegalArgumentException("n or trials is not valid");
        }
        this.n = n;
        this.trails = trails;
        percList = new double[this.trails];
        for (int trail=0; trail < this.trails; trail++){
            percolation = new Percolation(n);
            while(!percolation.percolates()){
                int[] randomPosition = getRandomPosFromBlockedSites();
                percolation.open(randomPosition[0], randomPosition[1] );
                if (percolation.percolates()){
                    this.percList[trail] = (double) percolation.numberOfOpenSites()/(n*n);
                }
            }
        }

    }

    private int[] getRandomPosFromBlockedSites() {
        //create a labels-to-block-position mapper
        int label = 0;
        int numberOfClosedSites = n * n - percolation.numberOfOpenSites();
        int randomLabel = StdRandom.uniformInt(numberOfClosedSites);
        for (int i=1;i<=n;i++) {
            for (int j = 1; j <= n; j++) {
                if (!percolation.isOpen(i,j)){
                    if(randomLabel ==label){
                        return new int[]{i,j};
                    }
                    label++;
                }
            }
        }
        return new int[]{0,0};
    }
    public double mean(){
        return StdStats.mean(percList);
    }

    public double stddev(){
        return StdStats.stddev(percList);
    }

    public double confidenceLo(){
        return mean() - 2 * stddev();
    }

    public double confidenceHi(){
        return mean() + 2 * stddev();
    }

    public static void main(String[] args){
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(20, 100);
        System.out.println("mean of probablity is " + percolationStats.mean());
        System.out.println("std of prob is " + percolationStats.stddev());
        System.out.println("lower confidence interval is " + percolationStats.confidenceLo());
        System.out.println("higher confidence interval is " + percolationStats.confidenceHi());
        System.out.println("elapsed time is " + stopwatch.elapsedTime());

        Stopwatch stopwatch2 = new Stopwatch();
        PercolationStats percolationStats2 = new PercolationStats(40, 100);
        System.out.println("mean of probablity is " + percolationStats2.mean());
        System.out.println("std of prob is " + percolationStats2.stddev());
        System.out.println("lower confidence interval is " + percolationStats2.confidenceLo());
        System.out.println("higher confidence interval is " + percolationStats2.confidenceHi());
        System.out.println("elapsed time is " + stopwatch2.elapsedTime());
    }

}
