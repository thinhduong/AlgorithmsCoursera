import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Solver {
    private Board _initial;
    private MinPQ<Node> _minPQ;
    private Node[] _visited;
    private int _visitedCount;
    private Board[] _solution;

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        _initial = initial;
        _minPQ = new MinPQ<Node>();
        _visited = new Node[1];
        _visitedCount = 0;
        _minPQ.insert(createNode(initial, 0, null));

        solve();
    }

    private void solve() {
        while (!_minPQ.isEmpty()) {
            Node node = _minPQ.delMin();

            if (node._board.isGoal()) {
                visit(node);
                traceBack(node);
                break;
            }
            else if (!isVisited(node._board)) {
                visit(node);
                for (Board neighbor : node._board.neighbors()) {
                    if (!isVisited(neighbor)) {
                        _minPQ.insert(createNode(neighbor, node._moves + 1, node._board));
                    }
                }
            }
        }
    }

    private void traceBack(Node node) {
        Node cur = node;
        Board[] tmp = new Board[_visitedCount];

        int c = 0;
        int j = _visitedCount - 1;

        while (cur._pre != null) {
            tmp[c] = cur._board;
            c += 1;

            for (int i = j; i >= 0 ;i--){
                if (cur._pre.equals(_visited[i]._board)) {
                    cur = _visited[i];
                    j = i;
                    break;
                }
            }
        }

        _solution = new Board[c + 1];
        j = 1;
        _solution[0] = _initial;
        for(int i=c-1; i>=0; i--) {
            _solution[j] = tmp[i];
            j+=1;
        }
    }

    private Node createNode(Board board, int moves, Board pre) {
        Node initialNode = new Node();
        initialNode._board = board;
        initialNode._moves = moves;
        initialNode._pre = pre;
        return initialNode;
    }

    private void visit(Node node) {
        if (_visitedCount == _visited.length) {
            Node[] tmp = new Node[_visited.length * 2];
            System.arraycopy(_visited, 0, tmp, 0, _visited.length);
            _visited = tmp;
        }

        _visited[_visitedCount] = node;

        _visitedCount += 1;
    }

    private boolean isVisited(Board b) {
        for (int i=0; i < _visitedCount; i++) {
            if (b.equals(_visited[i]._board))
                return true;
        }
        return false;
    }

    public boolean isSolvable() {
        return true;
    }

    public Iterable<Board> solution() {
        return new SolverIterable();
    }

    public int moves() {
        return _solution.length - 1;
    }

    private class Node implements Comparable<Node> {
        Board _pre;
        Board _board;
        int _moves;

        public int compareTo(Node o) {
            int v1 = _board.manhattan() + _moves;
            int v2 = o._board.manhattan() + o._moves;

            return v1 - v2;
        }
    }

    private class SolverIterable implements Iterable<Board> {

        public Iterator<Board> iterator() {
            return new SolverIterator();
        }
    }

    private class SolverIterator implements Iterator<Board> {
        private Board[] _copied;
        private int _cur = 0;

        public SolverIterator() {
            _copied = new Board[moves() + 1];
            System.arraycopy(_solution, 0, _copied, 0, moves() + 1);
        }

        public boolean hasNext() {
            return _cur < _copied.length;
        }

        public Board next() {
            Board ret = _copied[_cur];
            _cur += 1;
            return ret;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
