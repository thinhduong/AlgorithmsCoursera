import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] _segments;

    public FastCollinearPoints(Point[] points) {
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

        LineSegment[] tmpSegment = new LineSegment[bkPoints.length];
        int countLineSegment = 0;

        for (int i = 0; i< bkPoints.length; i++) {
            Point[] copied = copyArray(bkPoints, i);
            LineSegment line = getInLinePoints(bkPoints[i], copied);

            if (line != null && !isExist(line, tmpSegment, countLineSegment)) {
                tmpSegment[countLineSegment] = line;
                countLineSegment += 1;
            }
        }

        _segments = new LineSegment[countLineSegment];
        for(int i=0; i<countLineSegment; i++) {
            _segments[i] = tmpSegment[i];
        }
    }

    private boolean isExist(LineSegment line, LineSegment[] tmpSegment, int countLineSegment) {
        if (countLineSegment == 0)
            return false;

        for(int i=0; i<countLineSegment; i++){
            if (tmpSegment[i].toString().equals(line.toString()))
                return true;
        }

        return false;
    }

    private LineSegment getInLinePoints(Point root, Point[] copied) {
        Arrays.sort(copied, root.slopeOrder());

        for (int i=0; i<copied.length; i++){
            double slope = root.slopeTo(copied[i]);

            int count = 1;
            while(i + 1 < copied.length && root.slopeTo(copied[i + 1]) == slope) {
                i += 1;
                count += 1;
            }

            if (count >= 3) {
                Point[] tmp = new Point[count + 1];

                for (int j=0; j < count; j++) {
                    tmp[j] = copied[i-j];
                }

                tmp[count] = root;

                return createLine(tmp);
            }
        }

        return null;
    }

    private LineSegment createLine(Point[] points) {
        Point min = points[0];
        Point max = points[0];

        for(int i=1 ;i<points.length; i++) {
            if (min.compareTo(points[i]) == 1) {
                min = points[i];
            }
            else if (max.compareTo(points[i]) == -1) {
                max = points[i];
            }
        }

        return new LineSegment(min, max);
    }

    public int numberOfSegments() {
        return _segments.length;
    }

    public LineSegment[] segments() {
        return _segments;
    }

    private Point[] copyArray(Point[] points, int root) {
        Point[] tmp = new Point[points.length - 1];
        int idx = 0;
        for (int i=0; i<points.length; i++) {
            if (i != root){
                tmp[idx] = points[i];
                idx += 1;
            }
        }
        return tmp;
    }
}
