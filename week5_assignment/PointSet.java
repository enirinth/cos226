import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Aaron on 8/12/15.
 */
public class PointSet {
    private TreeSet<Point2D> set;

    private class RectIterator implements Iterator<Point2D> {
        private Iterator<Point2D> setIterator;
        private RectHV rect;
        private int total;
        private int current;

        public RectIterator(RectHV rect) {
            setIterator = set.iterator();
            this.rect = rect;
            current = 0;
            total = 0;
            for (Point2D p : set) {
                if (rect.contains(p)) {
                    total++;
                }
            }
        }
        public boolean hasNext() {
            return current < total;
        }
        public Point2D next() {
            Point2D p = setIterator.next();
            while (!rect.contains(p)) {
                p = setIterator.next();
            }
            current++;
            return p;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private class RectIterable implements Iterable<Point2D> {
        private RectHV rect;
        public RectIterable(RectHV rect) {
            this.rect = rect;
        }
        public Iterator<Point2D> iterator() {
            return new RectIterator(rect);
        }
    }
    public PointSet() {
        set = new TreeSet<Point2D>();
    }
    public boolean isEmpty() {
        return set.isEmpty();
    }
    public int size() {
        return set.size();
    }
    public void insert(Point2D p) {
        set.add(p);
    }
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }

    }
    public Iterable<Point2D> range(RectHV rect) {
        return new RectIterable(rect);
    }
    public Point2D nearest(Point2D p) {
        Point2D nearest = set.first();
        for (Point2D q : set) {
            if (p.distanceTo(q) < p.distanceTo(nearest)) {
                nearest = q;
            }
        }
        return nearest;
    }
    public static void main(String[] args) {}


}
