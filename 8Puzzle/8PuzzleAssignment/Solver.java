/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 7/8/2021
 *  Description: Implement A* Search to Solve Puzzle
 **************************************************************************** */

//import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class Solver {
    private boolean isSolvable;
    private int moves;
    private SearchNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();

        minPQ.insert(new SearchNode(initial, 0, null));

        moves = 0;
        isSolvable = true;
        solutionNode = null;
        Stack<Board> neighbors;

        Board currBoard;

        SearchNode current = minPQ.delMin();

        while (!current.getBoard().isGoal()) {

            if (current.getBoard().hamming() == 2 && current.getBoard().twin().isGoal()) {
                isSolvable = false;
                break;
            }

            
            moves = current.getMoves();

            Board prevBoard = moves > 0 ? current.prev().getBoard() : null;

            for (Board b : current.getBoard().neighbors()) {
                if (prevBoard != null && b.equals(prevBoard)) {
                    continue;
                }
                minPQ.insert(new SearchNode(b, moves + 1, current));
            }

            if(minPQ.isEmpty()) {
              break;
            }

            current = minPQ.delMin();
        }

        if (current.getBoard().isGoal()) {
            isSolvable = true;
            solutionNode = current;
        }

        if (minPQ.isEmpty() && !current.getBoard().isGoal()) isSolvable = false;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solutionNode.getMoves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;

        Queue<Board> solution = new Queue<Board>();
        SearchNode node = solutionNode;
        while (node != null) {
            solution.enqueue(node.getBoard());
            node = node.prev();
        }

        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final SearchNode prev;
        private final Board board;
        private final int moves;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }

        public int priority() {
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
          return moves;
        }

        public SearchNode prev() {
            return prev;
        }
    }

    public static void main(String[] args) {

    }
}
