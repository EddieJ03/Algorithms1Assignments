public class KdTreeVisualizerWithFile {
  public static void main(String[] args) {
    String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            StdOut.printf("%8.6f %8.6f\n", x, y);
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
      kdtree.draw();
      //System.out.println(kdtree.nearest(new Point2D(0.81, 0.30)));
  }
}