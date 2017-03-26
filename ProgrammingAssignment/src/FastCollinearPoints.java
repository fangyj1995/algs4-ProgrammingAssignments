import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] points;	
	private int pointsNum;
	private int segCount = 0;
	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
	{
		 if(points == null) throw new java.lang.NullPointerException();		
		 pointsNum=points.length;
		 this.points=new Point[pointsNum];
		 Arrays.sort(points);
		 for(int i = 0;i<pointsNum;i++)
		 {
			 if(points[i] == null)
				 throw new java.lang.NullPointerException();
			 this.points[i]=points[i];
		 }	
		 for(int i=1;i<pointsNum;i++)
		 {
			 if(points[i] == points[i-1])
				 throw new java.lang.IllegalArgumentException();			 
		 }
		 //System.out.println(Arrays.toString(points));
	}
	public   int numberOfSegments()        // the number of line segments
	{
		 return segCount;
	}
	 public LineSegment[] segments()                // the line segments
	 {
		 ArrayList<LineSegment> lineList = new ArrayList<LineSegment>();		 
		 for(int i = 0;i<pointsNum;i++){
			 int count = 0;
			 Arrays.sort(points, i+1, pointsNum, points[i].slopeOrder());
			 Arrays.sort(points, i+1, pointsNum);
			 //System.out.println(Arrays.toString(points));
			 int j = i+1;		int endPoint = 0;	 int startPoint = i;
			 while(j<pointsNum-1)
			 {
				 //System.out.println(points[i].slopeTo(points[j])+" "+points[i].slopeTo(points[j+1]));	
				 if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[j+1])){
					 //System.out.print(points[i]+" "+points[j]+" ");	
					 //System.out.println(points[i]+" "+points[j+1]);		   
					 count++;
					 if(count == 1 && points[j].compareTo(points[i])<0)
						 startPoint = j;
					 if(count >= 2)
					 {
						 if(points[i].compareTo(points[j+1]) > 0)
							 endPoint = i; 
						 else endPoint = j+1; 
					 } 
				 }			 
				 j++;
			 }
			 //System.out.println(count);
			 if(count >= 2)
			 {
				   LineSegment lineSeg = new LineSegment(points[startPoint],points[endPoint]);
				   lineList.add(lineSeg);
				   segCount++;	
			 }
		 }	
		 LineSegment[] segments = new LineSegment[segCount];
		 return lineList.toArray(segments);
	 }
}
