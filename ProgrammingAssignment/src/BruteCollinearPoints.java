import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private Point[] points;	
	private int pointsNum;
	private int segCount = 0;
 	 public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	 {
 		 if(points == null) throw new java.lang.NullPointerException();		
		 pointsNum = points.length;
		 this.points = new Point[pointsNum];
		 Arrays.sort(points);
		 for(int i = 0;i<pointsNum;i++){
			 this.points[i] = points[i];
			 if(points[i] == null)
				 throw new java.lang.NullPointerException();			 
		 }	
		 for(int i = 1;i<pointsNum;i++){
			 if(points[i] == points[i-1])
				 throw new java.lang.IllegalArgumentException();			 
		 }

	 }
	 public  int numberOfSegments()        // the number of line segments
	 {
		 return segCount;
	 }
	 public LineSegment[] segments()                // the line segments
	 {
		 ArrayList<LineSegment> lineList = new ArrayList<LineSegment>();		
		 
		 double iToj,iTok,iTol;	
		 for(int i = 0;i<pointsNum;i++){//i j k l
			 for(int j = i+1;j<pointsNum;j++){
				 for(int k = j+1;k<pointsNum;k++){
					 for(int l = k+1;l<pointsNum;l++){
						 iToj = points[i].slopeTo(points[j]);
						 iTok = points[i].slopeTo(points[k]);
						 iTol = points[i].slopeTo(points[l]);
						 if(iToj == iTok&&iTok == iTol)
						 { 							 
							 LineSegment lineSeg = new LineSegment(points[i],points[l]);
							 lineList.add(lineSeg);
						     segCount++;
						 }
					 }
				 }
			 }
		 }		 
		 LineSegment[] segments = new LineSegment[segCount];
		 return lineList.toArray(segments);
	 }
}
