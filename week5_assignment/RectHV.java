/******************************************************************************
 *  Compilation:  javac RectHV.java
 *  Execution:    none
 *  Dependencies: Point2D.java
 *
 *  Immutable data type for 2D axis-aligned rectangle.
 *
 ******************************************************************************/

/**
 *  The <tt>RectHV</tt> class is an immutable data type to encapsulate a
 *  two-dimensional axis-aligned rectagle with real-value coordinates.
 *  The rectangle is <em>closed</em>&mdash;it includes the points on the boundary.
 *  For additional documentation, see <a href="/algs4/12oop">Section 1.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public final class RectHV {
    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates

    /**
     * Initializes a new rectangle [<em>xmin</em>, <em>xmax</em>]
     * x [<em>ymin</em>, <em>ymax</em>].
     * @param xmin the <em>x</em>-coordinate of the lower-left endpoint
     * @param xmax the <em>x</em>-coordinate of the upper-right endpoint
     * @param ymin the <em>y</em>-coordinate of the lower-left endpoint
     * @param ymax the <em>y</em>-coordinate of the upper-right endpoint
     * @throws IllegalArgumentException if any of <tt>xmin</tt>,
     *    <tt>xmax</tt>, <tt>ymin</tt>, or <tt>ymax</tt>
     *    is <tt>Double.NaN</tt>.
     * @throws IllegalArgumentException if <tt>xmax</tt> &lt;
     *    <tt>xmin</tt> or <tt>ymax</tt> &lt; <tt>ymin</tt>.
     */
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (Double.isNaN(xmin) || Double.isNaN(xmax))
            throw new IllegalArgumentException("x-coordinate cannot be NaN");
        if (Double.isNaN(ymin) || Double.isNaN(ymax))
            throw new IllegalArgumentException("y-coordinates cannot be NaN");
        if (xmax < xmin || ymax < ymin) {
            throw new IllegalArgumentException("Invalid rectangle");
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    /**
     * Returns the minimum <em>x</em>-coordinate of any point in the rectangle.
     * @return the minimum <em>x</em>-coordinate of any point in the rectangle
     */
    public double xmin() {
        return xmin;
    }

    /**
     * Returns the maximum <em>x</em>-coordinate of any point in the rectangle.
     * @return the maximum <em>x</em>-coordinate of any point in the rectangle
     */
    public double xmax() {
        return xmax;
    }

    /**
     * Returns the minimum <em>y</em>-coordinate of any point in the rectangle.
     * @return the minimum <em>y</em>-coordinate of any point in the rectangle
     */
    public double ymin() {
        return ymin;
    }

    /**
     * Returns the maximum <em>y</em>-coordinate of any point in the rectangle.
     * @return the maximum <em>y</em>-coordinate of any point in the rectangle
     */
    public double ymax() {
        return ymax;
    }

    /**
     * Returns the width of the rectangle.
     * @return the width of the rectangle <tt>xmax - xmin</tt>
     */
    public double width() {
        return xmax - xmin;
    }

    /**
     * Returns the height of the rectangle.
     * @return the height of the rectangle <tt>ymax - ymin</tt>
     */
    public double height() {
        return ymax - ymin;
    }

    /**
     * Does this rectangle intersect that rectangle?
     * @param that the other rectangle
     * @return does this rectangle intersect that rectangle at one or more points,
    including on the boundary
     */
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    /**
     * Does the rectangle contain the point <tt>p</tt>?
     * @param p the point
     * @return does this rectangle contain the point <tt>p</tt>, possibly at the boundary?
     */
    public boolean contains(Point2D p) {
        return (p.x() >= xmin) && (p.x() <= xmax)
                && (p.y() >= ymin) && (p.y() <= ymax);
    }

    /**
     * Returns the Euclidean distance between the rectangle and the point <tt>p</tt>.
     * @param p the point
     * @return the Euclidean distance between the point <tt>p</tt> and the closest point
    on the rectangle; 0 if the point is contained in the rectangle
     */
    public double distanceTo(Point2D p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }

    /**
     * Returns the square of the Euclidean distance between the rectangle and the point <tt>p</tt>.
     * @param p the point
     * @return the square of the Euclidean distance between the point <tt>p</tt> and
     *     the closest point on the rectangle; 0 if the point is contained
     *     in the rectangle
     */
    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;
        if      (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if      (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx*dx + dy*dy;
    }

    /**
     * Is this rectangle equal to the other object?
     * @param other the other rectangle
     * @return true if this rectangle equals the other rectangle; false otherwise
     */
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) other;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    /**
     * Returns an integer hash code for this rectangle.
     * @return an integer hash code for this rectangle
     */
    public int hashCode() {
        int hash1 = ((Double) xmin).hashCode();
        int hash2 = ((Double) ymin).hashCode();
        int hash3 = ((Double) xmax).hashCode();
        int hash4 = ((Double) ymax).hashCode();
        return 31*(31*(31*hash1 + hash2) + hash3) + hash4;
    }

    /**
     * Returns a string representation of this rectangle.
     * @return a string representation of the rectangle, using the format
     *     <tt>[xmin, xmax] x [ymin, ymax]</tt>
     */
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }

    /**
     * Draws the rectangle to standard draw.
     */
    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmax, ymin, xmax, ymax);
        StdDraw.line(xmax, ymax, xmin, ymax);
        StdDraw.line(xmin, ymax, xmin, ymin);
    }


}

