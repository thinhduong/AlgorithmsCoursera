import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] _segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || (points.length >= 1 && points[0] == null))
            throw new IllegalArgumentException();

        for (int i=1; i<points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        Point[] bkPoints = new Point[points.length];
        System.arraycopy(points, 0, bkPoints, 0, points.length);

        Arrays.sort(bkPoints);

        for (int i=1; i<bkPoints.length; i++) {
            if (bkPoints[i].compareTo(bkPoints[i-1]) == 0)
                throw new IllegalArgumentException();
        }

        int count = 0;
        LineSegment[] tmp = new LineSegment[bkPoints.length];

        for (int i = 0; i<bkPoints.length - 3; i++){
            for(int j=i+1; j<bkPoints.length - 2; j++) {
                for(int k=j+1; k < bkPoints.length -1; k++) {
                    for (int l=k+1; l <bkPoints.length; l++) {
                        if ((bkPoints[i].slopeTo(bkPoints[j]) == bkPoints[j].slopeTo(bkPoints[k]))
                                && (bkPoints[k].slopeTo(bkPoints[l]) == bkPoints[j].slopeTo(bkPoints[k]))) {
                            tmp[count] = new LineSegment(bkPoints[i], bkPoints[l]);
                            count += 1;
                        }
                    }
                }
            }
        }

        _segments = new LineSegment[count];
        for (int i=0; i<count; i++){
            _segments[i] = tmp[i];
        }
    }

    public int numberOfSegments() {
        return _segments.length;
    }

    public LineSegment[] segments(){
        return _segments;
    }
}
