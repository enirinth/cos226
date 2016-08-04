/**
 * Created by Aaron on 7/18/15.
 */
import java.util.Arrays;
public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        if (N <= 3) {
            return;
        }
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        String out = "";
        StdDraw.setPenRadius(0.002);  // make the segments a bit narrower
        Point front, end = null;
        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int m = k + 1; m < N; m++) {
                        boolean ijkIsCollinear = points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]);
                        boolean ijmIsCollinear = points[i].slopeTo(points[j]) == points[i].slopeTo(points[m]);
                        if (ijkIsCollinear && ijmIsCollinear) {
                            points[i].drawTo(points[m]);
                            System.out.println(points[i] + " -> " + points[j] + " -> " + points[k] + " -> " + points[m]);
                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
