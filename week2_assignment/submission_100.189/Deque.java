/**
 * Created by Aaron on 7/9/15.
 */
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
        public Node(Item i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }
    private int size;
    private Node sentinel;

    public Deque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Error trying to add a null item!");
        }
        sentinel.next.prev = new Node(item, sentinel.next, sentinel);
        sentinel.next = sentinel.next.prev;
        size++;
    }
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Error trying to add a null item!");
        }
        sentinel.prev.next = new Node(item, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
        size++;

    }
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Error trying to remove item from an empty deque!");
        }
        Item returnItem = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return returnItem;
    }
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Error trying to remove item from an empty deque!");
        }
        Item returnItem = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return returnItem;
    }
    private class DequeIterator implements Iterator {
        private Node current = sentinel;
        public boolean hasNext() {
            return current.next != sentinel;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Error trying to call next() when encountering the end!");
            }
            current = current.next;
            return current.item;
        }
        public void remove() {
            throw new UnsupportedOperationException("Error! The iterator does not support remove() operation!");
        }
    }
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    public static void main(String[] args) {
        Deque<String> deck = new Deque<String>();
        System.out.println("remove " + deck.removeFirst());
        System.out.println("remove " + deck.removeLast());
        Iterator iterator = deck.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("size = " + deck.size());
    }
}
