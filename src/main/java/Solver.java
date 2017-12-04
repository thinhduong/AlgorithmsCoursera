import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

public class Solver {
    private Board _initial;
    private MinPQ<Board> _minPQ;

    public Solver(Board initial) {
        _initial = initial;
        _minPQ = new MinPQ<Board>();
    }

    public boolean isSolvable() {
        return true;
    }

    public Iterable<Board> solution() {

    }

    public int moves() {

    }

    class Node implements Comparable<Board> {

        public int compareTo(Board o) {
            return 0;
        }
    }
}
