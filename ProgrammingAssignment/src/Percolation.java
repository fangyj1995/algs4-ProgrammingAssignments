import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private boolean[][] sites;
   private int n;
   //private QuickFindUF uf;
   private WeightedQuickUnionUF uftop;
   private WeightedQuickUnionUF uf;
   private int openCount;
   public Percolation(int n) {
	   if(n <= 0)
		   throw new IllegalArgumentException ();
	   sites = new boolean[n][n];
	   this.n = n;
	   //uf = new QuickFindUF(n*n+2);
	   uf = new WeightedQuickUnionUF (n*n+2);
	   uftop = new WeightedQuickUnionUF (n*n+1);
   }
   public void open(int row, int col) {
	   if(row<1||row>n||col<1||col>n)
		   throw new IndexOutOfBoundsException();
	   
	   if(sites[row-1][col-1] == false){
		   
		   sites[row-1][col-1] = true;
		   openCount ++;
		   
		   int thisSite = (row - 1) * n + col;
		   
		   if(row == 1){//top
			   uf.union(thisSite, 0);
			   uftop.union(thisSite, 0);
		   }
		   else if(sites[row - 2][col - 1]){
			   uf.union(thisSite, (row - 2) * n + col);
			   uftop.union(thisSite, (row - 2) * n + col);
		   }
		   
		   if(row == n){//bottom
			   uf.union(thisSite, n * n + 1);
		   }
		   else if(sites[row][col - 1]){
			   uf.union(thisSite, row * n + col); 
			   uftop.union(thisSite, row * n + col); 
		   }
		   
		   if(col != 1 && sites[row - 1][col - 2]){//left
			   uf.union(thisSite, thisSite-1);
			   uftop.union(thisSite, thisSite-1);
		   }
		   if(col != n && sites[row - 1][col]){//right
			   uf.union(thisSite, thisSite+1);
			   uftop.union(thisSite, thisSite+1);
		   }
		   
	   }
	   else 
		   return;   
   }
   public boolean isOpen(int row, int col) {
	   if(row<1||row>n||col<1||col>n)
		   throw new IndexOutOfBoundsException();
	   return sites[row-1][col-1];
   }
   public boolean isFull(int row, int col) {
	   if(row<1||row>n||col<1||col>n)
		   throw new IndexOutOfBoundsException();
	   if(sites[row-1][col-1]){//open
		   return uftop.connected(0, (row - 1) * n + col);
	   }
	   return false;
   }
   
   public int numberOfOpenSites() {
	   return openCount;
   }
   public boolean percolates() {
	   return uf.connected(0, n * n + 1);
   }
   public static void main(String[] args) {
   }

}
