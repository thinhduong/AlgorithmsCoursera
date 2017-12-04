import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Board {
    private int[][] _blocks;
    private BoardIterable _boardIterable;
    private BoardIterator _boardIterator;

    public Board(int[][] blocks) {
        _blocks = blocks;
        _boardIterator = new BoardIterator();
        _boardIterable = new BoardIterable();
    }

    public int dimension() {
        return _blocks.length;
    }

    public int hamming() {
        int count = 0;
        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++) {
                int actual = i * dimension() + j + 1;

                if (i != dimension() - 1 && j != dimension() - 1 && actual != _blocks[i][j]) {
                    count += 1;
                }
            }
        }

        return count;
    }

    public int manhattan() {
        int sum = 0;
        for(int i=0; i<dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int row = (_blocks[i][j] - 1) / dimension();
                int col = (_blocks[i][j] - 1) % dimension();

                sum += Math.abs(i - row) + Math.abs(j - col);
            }
        }

        return sum;
    }

    public boolean isGoal() {
        for(int i=0; i<dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int actual = i * dimension() + j + 1;

                if (actual != _blocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public Board twin(){
        int row1 = StdRandom.uniform(dimension());
        int col1 = StdRandom.uniform(dimension());

        int row2 = StdRandom.uniform(dimension());
        int col2 = StdRandom.uniform(dimension());

        return newBoard(row1, col1, row2, col2);
    }

    private Board newBoard(int row1, int col1, int row2, int col2) {
        int[][] copied = copy();
        swap(copied, row1, col1, row2, col2);

        return new Board(copied);
    }

    private void swap(int[][] arr, int row1, int col1, int row2, int col2) {
        int tmp = arr[row1][col1];
        arr[row1][col1] = arr[row2][col2];
        arr[row2][col2] = tmp;
    }

    private int[][] copy(){
        int[][] tmp = new int[dimension()][];
        for (int i=0; i<dimension(); i++){
            tmp[i] = new int[dimension()];
            System.arraycopy(_blocks[i], 0, tmp[i], 0, dimension());
        }
        return tmp;
    }

    public boolean equals(Object y) {
        Board that = (Board)y;

        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++){
                if (that._blocks[i][j] != _blocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        return _boardIterable;
    }

    private Pos getEmptyPos(){
        for (int i=0; i<dimension(); i++){
            for (int j=0; j < dimension(); j++){
                if (_blocks[i][j] == 0) {
                    Pos pos = new Pos();
                    pos.row = i;
                    pos.col = j;
                    return pos;
                }
            }
        }

        return new Pos();
    }

    private class Pos {
        int row;
        int col;
    }

    private class BoardIterable implements Iterable<Board> {

        public Iterator<Board> iterator() {
            return _boardIterator;
        }
    }

    private class BoardIterator implements Iterator<Board> {
        private Pos[] _pos;
        private int _cur = 0;
        private Pos _emptyPos;

        public BoardIterator() {
            _emptyPos = getEmptyPos();

            int c = 0;
            Pos[] tmp = new Pos[4];

            if (_emptyPos.row > 0) {
                tmp[c] = new Pos();
                tmp[c].row = _emptyPos.row - 1;
                tmp[c].col = _emptyPos.col;
                c += 1;
            }

            if (_emptyPos.row < dimension() - 1) {
                tmp[c] = new Pos();
                tmp[c].row = _emptyPos.row + 1;
                tmp[c].col = _emptyPos.col;
                c += 1;
            }

            if (_emptyPos.col > 0) {
                tmp[c] = new Pos();
                tmp[c].row = _emptyPos.row;
                tmp[c].col = _emptyPos.col - 1;
                c += 1;
            }

            if (_emptyPos.col < dimension() - 1) {
                tmp[c] = new Pos();
                tmp[c].row = _emptyPos.row;
                tmp[c].col = _emptyPos.col + 1;
                c += 1;
            }

            _pos = new Pos[c];
            System.arraycopy(tmp, 0, _pos, 0, c);
        }

        public boolean hasNext() {
            return _cur < _pos.length;
        }

        public Board next() {
            Board tmp = newBoard(_emptyPos.row, _emptyPos.col, _pos[_cur].row, _pos[_cur].col);
            _cur += 1;
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<dimension(); i++) {
            for(int j=0; j<dimension(); j++){
                buffer.append(String.format("%1$2s ", _blocks[i][j]));
            }
            buffer.append(System.lineSeparator());
        }

        return buffer.toString();
    }
}
