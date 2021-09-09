/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 7/7/2021
 *  Description: Board Class
 **************************************************************************** */


import java.util.Stack;

public class Board {

    private int[][] tiles;

    private int hmd, mnh, x, y;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        hmd = 0;
        mnh = 0;

        this.tiles = tiles;

        int counter = 1;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {

              if (tiles[i][j] != 0) {
                mnh += Math.abs(i - ((tiles[i][j] - 1) / tiles[i].length)) + Math.abs(j - ((tiles[i][j] - 1) % tiles[i].length));

                if (tiles[i][j] != counter) {
                    hmd++;
                }
               

              } else {
                x = i;
                y = j;
              }
              
              counter++;

            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(tiles.length + "\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hmd;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return mnh;
    }

    // is this board the goal board?
    public boolean isGoal() {

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != (i * tiles.length + j + 1)) {
                    if (tiles[i][j] == 0) continue;
                    return false;
                }
            }
        }
        return true;

    }

    // does this board equal y?
    public boolean equals(Object m) {

      if (m == null) return false;

        if (m.getClass() == this.getClass()) {
            Board b = (Board) m;

            if (this.tiles.length == b.tiles.length && this.tiles[0].length == b.tiles[0].length) {
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[i].length; j++) {
                        if (b.tiles[i][j] != tiles[i][j]) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborBoards = new Stack<Board>();

        if (x > 0) neighborBoards.push(new Board(move(tiles, x - 1, y, x, y)));
        if (x < tiles.length - 1) neighborBoards.push(new Board(move(tiles, x + 1, y, x, y)));
        if (y > 0) neighborBoards.push(new Board(move(tiles, x, y - 1, x, y)));
        if (y < tiles.length - 1) neighborBoards.push(new Board(move(tiles, x, y + 1, x, y)));

        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] tempTiles = new int[tiles.length][tiles.length];

        for (int i = 0; i < tempTiles.length; i++) {
            for (int j = 0; j < tempTiles[i].length; j++) {
                tempTiles[i][j] = tiles[i][j];
            }
        }

        if (tiles[0][0] == 0) swap(tempTiles, 1, 1, 1);
        else if (tiles[0][1] == 0) swap(tempTiles, 0, 1, 0);
        else swap(tempTiles, 0, 0, 1);

        Board newBoard = new Board(tempTiles);

        return newBoard;

    }

    private void swap(int[][] tempTiles, int j1, int i2, int j2) {
        int temp = tempTiles[0][j1];
        tempTiles[0][j1] = tempTiles[i2][j2];
        tempTiles[i2][j2] = temp;
    }


    private int[][] move(int[][] tempTiles, int i1, int j1, int i2, int j2) {

        int[][] newTempTiles = new int[tiles.length][tiles.length];

        for (int i = 0; i < tempTiles.length; i++) {
            for (int j = 0; j < tempTiles[i].length; j++) {
                newTempTiles[i][j] = tempTiles[i][j];
            }
        }

        int temp = newTempTiles[i1][j1];
        newTempTiles[i1][j1] = newTempTiles[i2][j2];
        newTempTiles[i2][j2] = temp;

        return newTempTiles;

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
