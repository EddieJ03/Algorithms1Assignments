class Main {
  public static void main(String[] args) {
    int[][] tiles = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };

    int[][] tiles2 = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };

    Board b = new Board(tiles);
    Board b2 = new Board(tiles2);

    System.out.println(b.equals(b2));

    // for(Board bds : b.neighbors()) {
    //   System.out.println(bds);
    // }
  }
}