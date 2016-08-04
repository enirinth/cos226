/**
 * Created by Aaron on 7/17/15.
 */
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (Point.this.slopeTo(p1) < Point.this.slopeTo(p2)) {
                return -1;
            } else if (Point.this.slopeTo(p1) == Point.this.slopeTo(p2)) {
                return 0;
            } else {
                return 1;
            }
        }

    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x != that.x && this.y != that.y) {
            return ((double) (that.y - this.y)) / (that.x - this.x);
        } else if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;    //degenerate line segment, returns -infinity
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;    //vertical line segment, returns +infinity
        } else {
            return (0 / 1.0);      //horizontal line segment, returns +0.0
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) {
            return -1;
        }
        if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        }
        if (this.x > that.x) {
            return 1;
        }
        return 0;

    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point a = new Point(17000, 10000);
        Point b = new Point(19000, 10000);
        System.out.println(a.slopeTo(b));
    }
}
