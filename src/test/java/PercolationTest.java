import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PercolationTest {

    @Test
    public void test() {
        Percolation percolation = new Percolation(3);

        percolation.open(3, 3);

        assertEquals(percolation.numberOfOpenSites(), 1);
        assertEquals(percolation.percolates(), false);
        assertEquals(percolation.isFull(3, 3), false);

        percolation.open(3, 2);

        assertEquals(percolation.numberOfOpenSites(), 2);
        assertEquals(percolation.percolates(), false);
        assertEquals(percolation.isFull(3, 2), false);

        percolation.open(2, 2);
        percolation.open(1, 2);

        assertEquals(percolation.numberOfOpenSites(), 4);
        assertEquals(percolation.percolates(), true);
        assertEquals(percolation.isFull(3, 3), true);
    }
}
