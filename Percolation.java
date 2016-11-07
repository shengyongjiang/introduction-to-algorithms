import java.lang.System;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] status ;                  // 1:open / 0:block
    private WeightedQuickUnionUF uf ;
    private int _n ;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        status = new int[n*n+2];

        for (int i = 0; i < n*n+2; i++) {
            status[i] = 0;
        }
        status[0] = 1 ;

        uf = new WeightedQuickUnionUF(n*n+2);
        _n = n ;
    }

    private int getIndex(int row, int col){
        int index = (row-1) * _n + col ;
        return index ;
    }

    public void open(int row, int col){       // open site (row, col) if it is not open already
        int index = getIndex(row,  col) ;
        status[index] = 1 ;

        if(row == 1){
            uf.union(index,0) ;
        }

        if(row ==  _n){
            uf.union(index,_n*_n+1) ;
            status[_n*_n+1] = 1 ;
        }

        //left item
        if(col != 1 && isOpen(row,col-1)){
            uf.union(index,index-1) ;
        }

        //right item
        if(col != _n && isOpen(row,col+1)){
            uf.union(index,index+1) ;
        }

        //up item
        if(row > 1 && isOpen((row-1),col)){
            uf.union(index,index - _n) ;
        }

        //down item
        if(row < _n && isOpen((row+1),col)){
            uf.union(index,index + _n) ;
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        int index = getIndex(row,  col) ;
        return status[index] == 1 ;
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        int index = getIndex(row,  col) ;

        return uf.connected(1,2) ;
    }

    public boolean percolates() {             // does the system percolate?
        return uf.connected(0,_n*_n+1) ;
    }

    public static void main(String[] args) {  // test client (optional)

    }
}