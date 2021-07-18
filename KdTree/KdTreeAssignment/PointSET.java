/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 07/15/2021
 *  Description: Brute Force Implementation of Points
 **************************************************************************** */

//import edu.princeton.cs.algs4.Point2D;
//import edu.princeton.cs.algs4.Queue;
//import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;
    private Queue<Point2D> pointsInRect;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.size() == 0;
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        pointsInRect = new Queue<Point2D>();

        for (Point2D point : points) {
            if (rect.contains(point)) {
                pointsInRect.enqueue(point);
            }
        }

        return pointsInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Point2D nearest = points.first();
        double distance = p.distanceTo(nearest);

        for (Point2D point : points) {
            if (p.distanceTo(point) < distance) {
                nearest = point;
                distance = p.distanceTo(point);
            }
        }

        return nearest;
    }


    public static void main(String[] args) {

    }

}
