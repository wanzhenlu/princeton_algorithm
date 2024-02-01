
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[][] isOpenGrid;
    private int numberOfOpenSites=0;
    private WeightedQuickUnionUF wqufFull;
    private WeightedQuickUnionUF wqrfPerculate;
    private int[][] indexToLabelMapper;

    public Percolation( int n){
        if (n<=0){
            throw new IllegalArgumentException("Negative n is invalid");
        }
        this.n = n;
        this.isOpenGrid = new boolean[this.n][this.n];
        this.indexToLabelMapper = new int[this.n][this.n];
        int label = 1;
        for(int i = 1; i <= this.n; i++){
            for(int j = 1; j <= this.n; j++){
                //initialize to be blocked
                this.isOpenGrid[i-1][j-1] = false;
                this.indexToLabelMapper[i-1][j-1] = label;
                label ++;
            }
        }
        //create dummy top and dummy bottom sites for percolation, as 0, and n^2+1
        wqrfPerculate = new WeightedQuickUnionUF(this.n*this.n + 2); // 0,1,2,...,n^2+1, 0 is ceiling and n^2+1 is floor
        //create dummy top only for isFull, because other sites that are connected to bottom shouldn't be full after percolates
        wqufFull = new WeightedQuickUnionUF(this.n * this.n + 1);
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException("Position index is out of bound");
        }
        //open the site
        if (!isOpen(row, col)) {
            isOpenGrid[row - 1][col - 1] = true;
            numberOfOpenSites++;
        // union with ceiling if it is top row
        if (row==1){
            wqufFull.union(0,getLabel(row, col));
            wqrfPerculate.union(0,getLabel(row, col));
        }
        // union with floor if it is bottom row
        if (row==n){
            wqrfPerculate.union(getLabel(row, col), n*n+1);
        }
        // union neighbors
        int[][] neighbors = new int[][]{new int[]{row - 1, col},
                new int[]{row + 1, col},
                new int[]{row, col - 1},
                new int[]{row, col + 1}};
        for (int[] neighbor : neighbors) {
            if (neighbor[0] < 1 || neighbor[1] < 1
                    || neighbor[0] > this.n || neighbor[1] > this.n) {
                continue;
            }
            int p = getLabel(row, col);
            int q = getLabel(neighbor[0], neighbor[1]);

            if (isOpen(neighbor[0], neighbor[1])) {
                wqrfPerculate.union(p, q);
                wqufFull.union(p, q);
            }
            }
        }
    }


    private int getLabel(int row, int col){
        //the tricky part is to map the grid to list of int array in order to call find and union function
        return indexToLabelMapper[row-1][col-1];
    }
    public boolean isOpen(int row, int col){
        if (row <1 || col <1 || row >this.n || col > this.n){
            throw new IllegalArgumentException("Position index is out of bound");
        }
        return isOpenGrid[row-1][col-1];
    }

    public boolean isFull(int row, int col){
        if (row <1 || col <1 || row >this.n || col > this.n){
            throw new IllegalArgumentException("Position index is out of bound");
        }
        return wqufFull.find(0) == wqufFull.find(getLabel(row, col));
    }

    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }

    public boolean percolates(){
        return wqrfPerculate.find(0) == wqrfPerculate.find(n * n + 1);
    }

    public static void main(String[] args){

        Percolation percolation = new Percolation( 3);
        percolation.open(1,1);
        System.out.println(percolation.isOpen(1,1));
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.isFull(1,1));


//        System.out.println("Does it percolate? " + percolation.percolates());
//
//        percolation.open(1,1);
//        System.out.println("is it open? " + percolation.isOpen(1,1));
//        percolation.open(0,1);
//        System.out.println("is it open? " + percolation.isOpen(0,1));
//        System.out.println("Is it full? " + percolation.isFull(1,1));
//        percolation.open(2,1);
//        System.out.println("Is it full? " + percolation.isFull(2,1));
//        System.out.println("Does it percolate? " + percolation.percolates());
//        System.out.printf("number of open sites are " + percolation.numberOfOpenSites());
    }

}



