import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class BruteCollinearPointsTest {
    @Test
    public void test1() {
        int n = 8;
        Point[] points = new Point[n];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000   , 3000);
        points[4] = new Point(20000  , 21000);
        points[5] = new Point(3000   , 4000);
        points[6] = new Point(14000     , 15000);
        points[7] = new Point(6000      , 7000);

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        LineSegment[] ret = bruteCollinearPoints.segments();

        assertEquals(ret.length, 2);
        assertEquals(ret[0].toString(), "(10000, 0) -> (0, 10000)");
        assertEquals(ret[1].toString(), "(3000, 4000) -> (20000, 21000)");
    }

    @Test
    public void test2() {
        int n = 8;
        Point[] points = new Point[n];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000   , 3000);
        points[4] = new Point(20000  , 21000);
        points[5] = new Point(3000   , 4000);
        points[6] = new Point(14000     , 15000);
        points[7] = new Point(6000      , 7000);

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        LineSegment[] ret = fastCollinearPoints.segments();

        assertEquals(ret.length, 2);
    }

    @Test
    public void test3() {
        int n = 6;
        Point[] points = new Point[n];
        points[0] = new Point(19000  , 10000             );
        points[1] = new Point(18000  , 10000                 );
        points[2] = new Point(32000  , 10000              );
        points[3] = new Point(21000     , 10000              );
        points[4] = new Point(1234     , 5678             );
        points[5] = new Point(14000     , 10000              );

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        LineSegment[] ret = fastCollinearPoints.segments();

        assertEquals(ret.length, 1);
    }

    @Test
    public void test4() {
        int n = 2;
        Point[] points = new Point[n];
        points[0] = new Point(1, 1);

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

    }
}
