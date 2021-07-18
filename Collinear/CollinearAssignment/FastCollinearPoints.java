/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 7/17/2021
 *  Description: Sorting Based Collinear Algorithm
 **************************************************************************** */

// import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdOut;


import java.io.*;
import java.util.*;

public class FastCollinearPoints {

  static BufferedReader r;
	static PrintWriter pw;
	static {
		try {
      // change filename here to test code for different inputs
			r = new BufferedReader(new FileReader("horizontal100.txt"));
			pw = new PrintWriter(System.out);
		} catch (IOException e) {}
	}

     private ArrayList<LineSegment> allSegments;

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }
	    
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

        Point[] jCopy = points.clone();

        //sort first, makes it easier to loop through to find if next element different from current element ( nlog(n) )
        Arrays.sort(jCopy);

        allSegments = new ArrayList<LineSegment>();

        Point[] tempPoints = jCopy.clone();

        for(int i = 0; i < tempPoints.length - 3; i++) {

          Arrays.sort(tempPoints);

          // Arrays.sort with Comparator does not quite work in sorting the array according to slope with respect to given point
          Arrays.sort(tempPoints, tempPoints[i].slopeOrder());

          for (int p = 0, first = 1, last = 2; last < tempPoints.length; last++) {
              // find last collinear to p point
              while (last < tempPoints.length && Double.compare(tempPoints[p].slopeTo(tempPoints[first]), tempPoints[p].slopeTo(tempPoints[last])) == 0) {
                    last++;
              }

              // if found at least 3 elements, make segment if it is not a subsegment(jCopy[p] is less than jCopy[first] ensures uniqueness)
              if (last - first >= 3 && tempPoints[p].compareTo(tempPoints[first]) < 0) {
                  allSegments.add(new LineSegment(tempPoints[p], tempPoints[last - 1]));
              }

              // Try to find next group of three or more points
              // Never change index p since we are finding all line segments with respect to CURRENT point at index p
              first = last;
          }

        }

    }

    public int numberOfSegments() {
        return allSegments.size();
    }

    public LineSegment[] segments() {
        return allSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) throws IOException {

        StringTokenizer st = new StringTokenizer(r.readLine());

        Point[] points = new Point[Integer.parseInt(st.nextToken())];

        String getLine;

        int i = 0;
        while((getLine = r.readLine()) != null) {
            st = new StringTokenizer(getLine);
            points[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            i++;
        }

        // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        LineSegment[] segments = collinear.segments();

        for (LineSegment segment : segments) {
            System.out.println(segment);
            //segment.draw();
        }

        System.out.println("Number of Segments: " + segments.length);


        //StdDraw.show();
    }

}
