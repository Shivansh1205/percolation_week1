import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private static final int top = 0;
    private final boolean[][] opened;
    private int openSites;
    private final int bottom;
    private final WeightedQuickUnionUF qf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        size = n;
        opened = new boolean[size][size];
        openSites = 0;
        bottom = size * size + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
    }

    private void checkException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
    }

    private int getQuickFindIndex(int row, int col) {
        return (size * (row - 1)) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkException(row, col);

        if (isOpen(row, col)) return; 

   
        opened[row - 1][col - 1] = true;
        openSites++;

       
        if (row == 1) {
            qf.union(getQuickFindIndex(row, col), top);
        }

        if (row == size) {
            qf.union(getQuickFindIndex(row, col), bottom);
        }


        if (row > 1 && isOpen(row - 1, col)) { 
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) { 
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) { 
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full? (connected to the top)
    public boolean isFull(int row, int col) {
        checkException(row, col);
        return qf.find(top) == qf.find(getQuickFindIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(top) == qf.find(bottom);
    }

    // Test client (optional)
    public static void main(String[] args) {
        
    }
}
