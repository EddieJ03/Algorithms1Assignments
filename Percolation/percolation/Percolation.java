//import edu.princeton.cs.algs4.WeightedQuickUnionUF;



import java.io.*;
import java.util.StringTokenizer;

public class Percolation {

    static BufferedReader r;
    static PrintWriter pw;
    static {
      try {
        // Replace file name to test/debug code
        r = new BufferedReader(new FileReader("input3.txt"));
        pw = new PrintWriter(System.out);
      } catch(IOException e) {}
    }

    private boolean[][] opened;
    private final int top;
    private final int bottom;
    private final int size;
    private int numOfOpenSites;
    private WeightedQuickUnionUF qf;

    // isFull is subject to backwash and to avoid create another WeightedQuickUnionUF object WITHOUT the virtual bottom node
    private WeightedQuickUnionUF qfBackWash;

    /**
     * Creates N-by-N grid, with all sites blocked.
     */
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        size = n;
        bottom = size * size + 1;
        top = 0;
        qf = new WeightedQuickUnionUF(size * size + 2);
        qfBackWash = new WeightedQuickUnionUF(size * size + 1);
        opened = new boolean[size][size];
        numOfOpenSites = 0;
    }

    /**
     * Opens site (row i, column j) if it is not already.
     */
    public void open(int i, int j) {

        if (0 < i && i <= size && 0 < j && j <= size) {

            if (!isOpen(i, j)) {
                
                numOfOpenSites++;

                opened[i - 1][j - 1] = true;

                if (i == 1) {
                  //System.out.println("inside first if");
                    qf.union(top, getQFIndex(i, j));
                    qfBackWash.union(top, getQFIndex(i, j));
                }
                if (i == size) {
                  //System.out.println("inside second if");
                    qf.union(bottom, getQFIndex(i, j));
                }

                if (j > 1 && isOpen(i, j - 1)) {
                  //System.out.println("inside third if");
                    qf.union(getQFIndex(i, j), getQFIndex(i, j - 1));
                    qfBackWash.union(getQFIndex(i, j), getQFIndex(i, j - 1));
                }
                if (j < size && isOpen(i, j + 1)) {
                  //System.out.println("inside fourth if");
                    qf.union(getQFIndex(i, j), getQFIndex(i, j + 1));
                    qfBackWash.union(getQFIndex(i, j), getQFIndex(i, j + 1));
                }
                if (i > 1 && isOpen(i - 1, j)) {
                  //System.out.println("inside fifth if with i = " + i + " and j = " + j);
                    qf.union(getQFIndex(i, j), getQFIndex(i - 1, j));
                    qfBackWash.union(getQFIndex(i, j), getQFIndex(i - 1, j));
                }
                if (i < size && isOpen(i + 1, j)) {
                  //System.out.println("inside sixth if");
                    qf.union(getQFIndex(i, j), getQFIndex(i + 1, j));
                    qfBackWash.union(getQFIndex(i, j), getQFIndex(i + 1, j));
                }

            }

        }
        else {
            throw new IllegalArgumentException();
        }

    }

    public int[] getArray() {
      return qfBackWash.getParent();
    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        if (0 < i && i <= size && 0 < j && j <= size) {
            return opened[i - 1][j - 1];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        if (0 < i && i <= size && 0 < j && j <= size) {
          if(isOpen(i, j)) {
            return qfBackWash.find(getQFIndex(i, j)) == qfBackWash.find(top);
          } else {
            return false;
          }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return qf.find(top) == qf.find(bottom);
    }

    private int getQFIndex(int i, int j) {
        return size * (i - 1) + j;
    }
    
    public static void main(String[] args) throws IOException {

      StringTokenizer st = new StringTokenizer(r.readLine());
      int n = Integer.parseInt(st.nextToken()), a = 6, b, c;

      int[] array;

      Percolation p = new Percolation(n);

      for(int i = 0; i < 6; i++) {
        st = new StringTokenizer(r.readLine());
        b = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        p.open(b, c);

        array = p.getArray();

        for(Integer num : array) {
          System.out.print(num + " ");
        }

        System.out.println();

        pw.println("Line " + i + " isFull? " + p.isFull(b, c));
      }

      pw.close();

    }
}
