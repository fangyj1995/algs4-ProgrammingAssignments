import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
   private double[] fractions;
   private double mean;
   private double stddev;
   private double confidenceLo;
   private double confidenceHi;
   
   public PercolationStats(int n, int trials) {
	   // perform trials independent experiments on an n-by-n grid
	   if(n <= 0 || trials <= 0)
		   throw new IllegalArgumentException ();	
	   fractions = new double [trials];
	   for( int i = 0; i < trials ; i++){
		   Percolation pc = new Percolation (n);
		   while(!pc.percolates()){
			   int site = StdRandom.uniform(n*n);//[0~n*n-1]	
			   int row = site / n + 1;
			   int col = site % n + 1;
			   pc.open(row, col);    
		   }
		   double fraction = (double)pc.numberOfOpenSites() / (double)(n*n) ;
		   fractions [i] = fraction;
	   }
	   mean = StdStats.mean(fractions);
	   stddev = StdStats.stddev(fractions);
	   double halfInterval = 1.96 * stddev/Math.sqrt(trials);
	   confidenceLo = mean - halfInterval;
	   confidenceHi = mean + halfInterval;
   }
   public double mean() {                         // sample mean of percolation threshold
	   return mean;
   }
   public double stddev() {
	   // sample standard deviation of percolation threshold
	   return stddev;
   }
   public double confidenceLo() {
	   // low  endpoint of 95% confidence interval
	   return this.confidenceLo;
   }
   public double confidenceHi() {
	   // high endpoint of 95% confidence interval
	   return this.confidenceHi;
   }
   private void printOut(){
	   System.out.println(String.format("%-23s", "mean")+" = " + this.mean());
	   System.out.println(String.format("%-23s", "stddev")+" = " + this.stddev());
	   System.out.println("95% confidence interval"+" = ["+ this.confidenceLo()+", "+ this.confidenceHi() + "]");
   }
   public static void main(String[] args) {
	   // test client (described below)
	   int n  = Integer.parseInt(args[0]);
	   int trials = Integer.parseInt(args[1]);
	   PercolationStats pcStats = new PercolationStats(n , trials);
	   pcStats.printOut();     
   }

}
