import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private WeightedQuickUnionUF _uf;
    private boolean[] opened;
    private int _n;
    private int _topNode;
    private int _bottomNode;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        _n = n;
        _uf = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n * n];

        _topNode = _n * _n;
        _bottomNode = _topNode + 1;
    }

    private boolean inRange(int row, int col) {
        return !(row < 1 || col < 1 || row > _n || col > _n);
    }

    private void assertParams(int row, int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException();
        }
    }

    private int toIndex(int row, int col) {
        return (row - 1) * _n + (col - 1);
    }

    public void open(int row, int col) {
        assertParams(row, col);

        int a = toIndex(row, col);
        opened[a] = true;

        for (int i= -1; i<=1; i+=2) {
            if (inRange(row + i, col)) {
                int b = toIndex(row + i, col);
                if (opened[b]) {
                    _uf.union(a, b);
                }
            }

            if (inRange(row, col + i)) {
                int b = toIndex(row, col + i);
                if (opened[b]) {
                    _uf.union(a, b);
                }
            }
        }

        if (row == 1) {
            _uf.union(a, _topNode);
        }
        else if (row == _n) {
            _uf.union(a, _bottomNode);
        }
    }

    public boolean isOpen(int row, int col) {
        assertParams(row, col);

        return opened[toIndex(row, col)];
    }

    public boolean isFull(int row, int col){
        assertParams(row, col);

        return _uf.connected(_topNode, toIndex(row, col));
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < _n * _n; i ++) {
            if (opened[i]) {
                count += 1;
            }
        }

        return count;
    }

    public boolean percolates() {
        return _uf.connected(_topNode, _bottomNode);
    }
}


