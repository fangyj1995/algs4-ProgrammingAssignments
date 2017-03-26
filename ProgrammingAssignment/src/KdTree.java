import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;



/**
 * Created by A on 2017/1/30.
 */
public class KdTree {
    private Node root;
    private int n;
    private static class Node {
        private Node lob;//left or bottom
        private Node rot;//right or top
        private Point2D p;
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node

        public Node(Point2D p) {
            this.p = p;
        }
    }

    public KdTree() {
        n = 0;
    } // construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    } // is the set empty?

    public int size() {
        return size(root);
    }// number of points in the set

    private int size(Node node) {
        return n;
    }

    public void insert(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        root = insert(root, p, '|', 0,0,1,1);

    }  // add the point to the set (if it is not already in the set)

    private Node insert(Node node, Point2D p, char ori, double xmin , double ymin ,double xmax , double ymax) {
        if (node == null) {
            Node newNode = new Node(p);
            newNode.rect = new RectHV(xmin, ymin, xmax, ymax);
            n++;
            return newNode;
        }
        if (node.p.equals(p))
            return node;
        // node not null
        if (ori == '-') {//top bottom , compare with y
            if (p.y() < node.p.y())//bottom
                node.lob = insert(node.lob, p, '|', node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
            else//top
                node.rot = insert(node.rot, p, '|', node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
        } else if (ori == '|') {// left right , compare with x
            if (p.x() < node.p.x())//left
                node.lob = insert(node.lob, p, '-', node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            else//right
                node.rot = insert(node.rot, p, '-', node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
        } else
            throw new RuntimeException();

        return node;
    }

    public boolean contains(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        return contains(root, p, '|');
    }  // does the set contain point p?

    private boolean contains(Node node, Point2D p, char ori) {
        if (node == null)
            return false;
        if (node.p.equals(p))
            return true;
        // node not null
        if (ori == '-') {//top bottom , compare with y
            if (p.y() < node.p.y())
                return contains(node.lob, p, '|');
            else
                return contains(node.rot, p, '|');
        } else if (ori == '|') {// left right , compare with x
            if (p.x() < node.p.x())
                return contains(node.lob, p, '-');
            else
                return contains(node.rot, p, '-');
        } else
            throw new RuntimeException();

    }

    public void draw() {
        draw(root, '|');
    }   // draw all points to standard draw

    private void draw(Node node, char ori) {
        if (node == null) return;
        StdDraw.setPenRadius();
        if (ori == '|') {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            draw(node.lob, '-');
            draw(node.rot, '-');
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            draw(node.lob, '|');
            draw(node.rot, '|');
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, queue);
        return queue;
    }  // all points that are inside the rectangle

    private void range(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null || !node.rect.intersects(rect))
            return;
        if (rect.contains(node.p)) {
            queue.enqueue(node.p);
        }
        range(node.lob, rect, queue);
        range(node.rot, rect, queue);
    }


    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        return nearest(root, p, null, '|');
    }   // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D nearest(Node node, Point2D p, Point2D nearest, char ori) {
        if (node == null)
            return nearest;
        double minDist = (nearest == null ? Double.POSITIVE_INFINITY : p.distanceSquaredTo(nearest));
        if (node.rect.distanceSquaredTo(p) >= minDist)
            return nearest;
        double tmpDist = p.distanceSquaredTo(node.p);
        if (nearest == null || tmpDist < minDist) {
            nearest = node.p;
            minDist = tmpDist;
        }
//        nearest = nearest(node.lob, p, nearest, minDist, '-');
//        nearest = nearest(node.rot, p, nearest, minDist, '-');
        if (ori == '|') {
            if (p.x() < node.p.x()) {
                nearest = nearest(node.lob, p, nearest, '-');
                nearest = nearest(node.rot, p, nearest, '-');
            } else {
                nearest = nearest(node.rot, p, nearest, '-');
                nearest = nearest(node.lob, p, nearest, '-');
            }
        } else {//  '-'
            if (p.y() < node.p.y()) {
                nearest = nearest(node.lob, p, nearest, '|');
                nearest = nearest(node.rot, p, nearest, '|');
            } else {
                nearest = nearest(node.rot, p, nearest, '|');
                nearest = nearest(node.lob, p, nearest, '|');
            }
        }
        return nearest;
    }

    public static void main(String[] args)                {
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(0.1,0.4);
        Point2D p2 = new Point2D(0.2,0.3);
        Point2D p3 = new Point2D(0.7,0.5);
        Point2D p4 = new Point2D(0.6,0.1);
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);

        //StdDraw.setPenRadius(0.01);
        //tree.draw();
        //StdDraw.show();
        System.out.print(tree.nearest(new Point2D(0.12,0.34)));
    }  // unit testing of the methods (optional)


}
