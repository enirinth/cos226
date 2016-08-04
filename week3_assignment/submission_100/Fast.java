/**
 * Created by Aaron on 7/18/15.
 */
import java.util.Arrays;
public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.02);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        if (N <= 3) { // corner case: less than 3 points in total
            return;
        }
        Point[] points = new Point[N];
        Point[] basePoints = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            basePoints[i] = new Point(x, y);
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // draw and print out all line segments with ≥ 4 points
        StdDraw.setPenRadius(0.002);  // make the lines a bit narrower
        String outputLine = null;
        Arrays.sort(basePoints);
        int[] edgeMarkers = new int[N];
        for (int i = 0; i < N; i++) {
            Arrays.sort(points); // sort by natural order (compare y coordinate then x)
            Arrays.sort(points, basePoints[i].SLOPE_ORDER);

            // go through the sorted array, mark fronts and ends of all segments
            for (int j = 0; j < N; j++) {
                edgeMarkers[j] = 0;
            }
            final int FRONT = 1;
            final int END = -1;
            for (int j = 1; j < N - 1; j++) {
                boolean equalsToNext = basePoints[i].slopeTo(points[j]) == basePoints[i].slopeTo(points[j + 1]);
                boolean equalsToPrev = basePoints[i].slopeTo(points[j]) == basePoints[i].slopeTo(points[j - 1]);
                if (equalsToNext && !equalsToPrev) {
                    edgeMarkers[j] = FRONT;
                }
                if (equalsToPrev && !equalsToNext) {
                    edgeMarkers[j] = END;
                }
            }
            if (basePoints[i].slopeTo(points[0]) == basePoints[i].slopeTo(points[1])) {
                edgeMarkers[0] = FRONT;
            }
            if (basePoints[i].slopeTo(points[N - 1]) == basePoints[i].slopeTo(points[N - 2])) {
                edgeMarkers[N - 1] = END;
            }

            // go through the sorted array again, print and draw all [front, end] segment
            int distance = 0;
            boolean isInSegment = false;
            for (int j = 0; j < N; j++) {
                if (edgeMarkers[j] == FRONT) {
                    if (basePoints[i].compareTo(points[j]) > 0) { // choose only the permutation where basePoints[i] is the first (by natural order)
                        continue;
                    }
                    isInSegment = true;
                    distance = 0;
                }
                if (isInSegment) {
                    distance++;
                }
                if (edgeMarkers[j] == END) {
                    if (distance >= 3) {
                        //print
                        String output = "";
                        output += (basePoints[i] + " -> ");
                        for (int k = j - distance + 1; k < j; k++) {
                            output += (points[k] + " -> ");
                        }
                        output += points[j];
                        System.out.println(output);
                        //draw
                        basePoints[i].drawTo(points[j]);
                    }
                    isInSegment = false;
                    distance = 0;
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

}
