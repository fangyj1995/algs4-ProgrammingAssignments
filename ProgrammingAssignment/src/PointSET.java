import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by A on 2017/1/30.
 */
public class PointSET {
    private Set<Point2D> points;
    public PointSET() {
        points = new TreeSet<>();
    } // construct an empty set of points

    public boolean isEmpty()   {
        return points.isEmpty();
    } // is the set empty?
    public int size()         {
        return points.size();
    }// number of points in the set
    public void insert(Point2D p)  {
        if(p == null)
            throw new NullPointerException();
        points.add(p);
    }  // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p)  {
        if(p == null)
            throw new NullPointerException();
        return points.contains(p);
    }  // does the set contain point p?
    public void draw() {
        for (Point2D point:points) {
            point.draw();
        }
    }   // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)   {
        if(rect == null)
            throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D point:points) {
            if(rect.contains(point)){
                queue.enqueue(point);
            }
        }
        return queue;
    }  // all points that are inside the rectangle
    public Point2D nearest(Point2D p)          {
        if(p == null)
            throw new NullPointerException();
        double minDist = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D point:points) {
            double tempDist = point.distanceSquaredTo(p);
            if(tempDist < minDist){
                nearest = point ;
                minDist = tempDist;
            }
        }
        return nearest;
    }   // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args)                {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        PointSET pointSET = new PointSET();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    pointSET.insert(p);
                    StdDraw.clear();
                    pointSET.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(50);
        }
    }  // unit testing of the methods (optional)

}
