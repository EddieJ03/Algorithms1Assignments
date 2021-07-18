/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 7/15/2021
 *  Description: Code a 2d Tree
 **************************************************************************** */

// import edu.princeton.cs.algs4.Point2D;
// import edu.princeton.cs.algs4.Queue;
// import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private static final double XMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMIN = 0.0;
    private static final double YMAX = 1.0;

    private int size;

    private Node root;

    private class Node {
        Point2D p;
        RectHV rect;
        Node left;
        Node right;
        boolean orientation;

        Node(Point2D value, RectHV inRect, boolean orientation) {
            p = value;
            rect = inRect;
            left = null;
            right = null;
            this.orientation = orientation;
        }
    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
      if (point == null) throw new IllegalArgumentException();
        root = insert(root, point, XMIN, YMIN, XMAX, YMAX, 0);
    }

    public boolean contains(Point2D point) {
      if (point == null) throw new IllegalArgumentException();
        return (contains(root, point, 0) != null);
    }

    private int cmp(Point2D a, Point2D b, int depth) {
        if (depth % 2 == 0) {
            // Compare x-coordinates
            int cmpResult = Double.valueOf(a.x()).compareTo(Double.valueOf(b.x()));

            if (cmpResult == 0) {
                return Double.valueOf(a.y()).compareTo(Double.valueOf(b.y()));
            } else {
                return cmpResult;
            }
        } else {
         // Compare y-coordinates
            int cmpResult = Double.valueOf(a.y()).compareTo(Double.valueOf(b.y()));

            if (cmpResult == 0) {
                return Double.valueOf(a.x()).compareTo(Double.valueOf(b.x()));
            } else {
                return cmpResult;
            }
        }
    }

    private Node insert(Node x, Point2D value, double xmin, double ymin, double xmax, double ymax, int depth) {
        if (x == null) {
            size++;
            return new Node(value, new RectHV(xmin, ymin, xmax, ymax), depth % 2 == 0);
        };

        int cmp = cmp(value, x.p, depth);

        // Value equals Point2D to be inserted
        if (cmp < 0) {
            if (depth % 2 == 0) {
                // Vertical Orientation 
                x.left = insert(x.left, value, xmin, ymin, x.p.x(), ymax, depth + 1);
            } else {
                // Horizontal Orientation
                x.left = insert(x.left, value, xmin, ymin, xmax, x.p.y(), depth + 1);
            }
        } else if (cmp > 0) {
            if (depth % 2 == 0) {
                x.right = insert(x.right, value, x.p.x(), ymin, xmax, ymax, depth + 1);
            } else {
                x.right = insert(x.right, value, xmin, x.p.y(), xmax, ymax, depth + 1);
            }
        }

        //if cmp == 0 do nothing

        return x;
    }

    private Point2D contains(Node x, Point2D point, int depth) {
        while (x != null) {

            int cmp = cmp(point, x.p, depth);

            if (cmp < 0) {
                return contains(x.left, point, depth + 1);
            } else if (cmp > 0) {
                return contains(x.right, point, depth + 1);
            } else {
                return x.p;
            }
        }

        return null;
    }

    public void draw() {
        StdDraw.clear();

        drawLine(root, 0);
    }

    private void drawLine(Node x, int depth) {
        if (x != null) {
            drawLine(x.left, depth + 1);

            StdDraw.setPenRadius();
            if (depth % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            x.p.draw();

            drawLine(x.right, depth + 1);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    private void range(Node node, RectHV pointRect, RectHV rect, Queue<Point2D> queue) {

        if (node == null) return;

        // only search if rect intersects the pointRect (pruning)
        if (rect.intersects(pointRect)) {
            final Point2D p = new Point2D(node.p.x(), node.p.y());

            if (rect.contains(p)) {
              queue.enqueue(p);
            }

            range(node.left, node.left == null ? null : node.left.rect, rect, queue);
            range(node.right, node.right == null ? null : node.right.rect, rect, queue);
        }

    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {

      if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> queue = new Queue<Point2D>();

        range(root, new RectHV(0.0, 0.0, 1.0, 1.0), rect, queue);

        return queue;

    }

    // // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {

        if (point == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            return null;
        }
            
        Point2D result = null;

        result = nearest(root, point, result);

        return result;

    }

    private Point2D nearest(Node x, Point2D point, Point2D min) {

        if (x != null) {
          if (min == null) {
                min = x.p;
            }
            System.out.println("KdTree Point: " + x.p);
            // If the current min point is farther from query than the current Node's rectangle, it is possible there exists closer points in the rectangle so search in the rectangle recursively
            if (min.distanceSquaredTo(point) >= x.rect.distanceSquaredTo(point)) {

                if (x.p.distanceSquaredTo(point) < min.distanceSquaredTo(point)) {
                    min = x.p;
                }

                // Check in which order should we iterate
                // point is left of vert, go left. otherwise, go right


                if(x.orientation) {
                  if(point.x() < x.p.x()) {
                    min = nearest(x.left, point, min);
                    min = nearest(x.right, point, min);
                  } else {
                    min = nearest(x.right, point, min);
                    min = nearest(x.left, point, min);
                  }
                } else {
                  if(point.y() < x.p.y()) {
                    min = nearest(x.left, point, min);
                    min = nearest(x.right, point, min);
                  } else {
                     min = nearest(x.right, point, min);
                     min = nearest(x.left, point, min);
                  }
                }               
              

            }
        }

        return min;
    }

    public static void main(String[] args) {

    }

}
