/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 6/29/2021
 *  Description: Brute Force Algorithm to find 4 Collinear Points Out of A Set of n points
 **************************************************************************** */

// import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BruteCollinearPoints {

    private int numSegments;
    private ArrayList<LineSegment> allSegments;
    private Point[] points;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = points.clone();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            } else {
              for (int j = i + 1; j < points.length; j++) {
                  if (points[j] == null) {
                      throw new IllegalArgumentException();
                  }
                  else if (points[i].compareTo(points[j]) == 0) {
                      throw new IllegalArgumentException();
                  }
              }
            }
        }

        numSegments = 0;

        allSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length - 3; i++) {

            for (int j = i + 1; j < points.length - 2; j++) {

                for (int k = j + 1; k < points.length - 1; k++) {

                    for (int m = k + 1; m < points.length; m++) {

                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[k]) == points[i].slopeTo(points[m])) {
                          
                          allSegments.add(new LineSegment(smallestPoint(points[i], points[j], points[k], points[m]), largestPoint(points[i], points[j], points[k], points[m])));

                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return allSegments.size();
    }

    public LineSegment[] segments() {
        return allSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
      

        Point[] points = new Point[4];

        points[0] = new Point(9000, 9000);
        points[1] = new Point(8000, 8000);
        points[2] = new Point(7000, 7000);
        points[3] = new Point(6000, 6000);

        // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();


        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        LineSegment[] segments = collinear.segments();

        for (LineSegment segment : segments) {
            System.out.println(segment);
            //segment.draw();
        }
        //StdDraw.show();
    }

    private Point smallestPoint(Point p1, Point p2, Point p3, Point p4) {
      if (p1.compareTo(p2) < 0 && p1.compareTo(p3) < 0 && p1.compareTo(p4) < 0) {
        return p1;
      } else if (p2.compareTo(p1) < 0 && p2.compareTo(p3) < 0 && p2.compareTo(p4) < 0) {
        return p2;
      } else if (p3.compareTo(p2) < 0 && p3.compareTo(p1) < 0 && p3.compareTo(p4) < 0) {
        return p3;
      } else {
        return p4;
      }
    }

    private Point largestPoint(Point p1, Point p2, Point p3, Point p4) {
      if (p1.compareTo(p2) > 0 && p1.compareTo(p3) > 0 && p1.compareTo(p4) > 0) {
        return p1;
      } else if (p2.compareTo(p1) > 0 && p2.compareTo(p3) > 0 && p2.compareTo(p4) > 0) {
        return p2;
      } else if (p3.compareTo(p2) > 0 && p3.compareTo(p1) > 0 && p3.compareTo(p4) > 0) {
        return p3;
      } else {
        return p4;
      }
    }

}


