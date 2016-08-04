import java.util.Iterator;

/**
 * Created by Aaron on 7/9/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queueArr;
    private int size;
    private int front;   //inclusive
    private int back;    //exclusive
    public RandomizedQueue() {
        queueArr = (Item[]) (new Object[1]);
        size = 0;
        front = 0;
        back = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    private void resize(int capacity) {
        Item[] newQueueArr = (Item[]) (new Object[capacity]);
        int j = 0;
        for (int i = front; i < back; i++) {
            if (queueArr[i] != null) {
                newQueueArr[j++] = queueArr[i];
            }
        }
        queueArr = newQueueArr;
        front = 0;
        back = front + size;
    }
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Error trying to add a null item!");
        }
        queueArr[back++] = item;
        size++;
        if (back == queueArr.length) {
            resize(2 * queueArr.length);
        }
    }
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Error trying to remove item from an empty queue!");
        }
        int index = 0;
        do {
            index = StdRandom.uniform(front, back);
        } while (queueArr[index] == null);
        Item returnItem = queueArr[index];
        queueArr[index] = null;
        size--;
        if (size <= queueArr.length/4) {
            resize(queueArr.length/2);
        }
        return returnItem;
    }
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Error trying to sample item from an empty queue!");
        }
        int index = 0;
        do {
            index = StdRandom.uniform(front, back);
        } while (queueArr[index] == null);
        return queueArr[index];
    }
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator {
        private int current = 0;
        private Item[] randomArr;

        public RandomizedQueueIterator() {
            randomArr = (Item[]) (new Object[size]);
            int j = 0;
            for (int i = front; i < back; i++) {
                if (queueArr[i] != null) {
                    randomArr[j++] = queueArr[i];
                }
            }
            StdRandom.shuffle(randomArr);
        }
        public boolean hasNext() {
            return current < randomArr.length;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Error trying to call next() when encountering the end!");
            }
            return randomArr[current++];
        }
        public void remove() {
            throw new UnsupportedOperationException("Error! The iterator does not support remove() operation!");
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> RQ = new RandomizedQueue<Integer>();
        System.out.println(RQ.isEmpty());
        RQ.enqueue(3);
        RQ.enqueue(4);
        RQ.enqueue(5);
        System.out.println(RQ.isEmpty());
        //System.out.println("queueArr's length= " + RQ.len());
        System.out.println("Dequeue: " + RQ.dequeue());
        System.out.println("Dequeue: " + RQ.dequeue());
        System.out.println(RQ.isEmpty());
        System.out.println("Dequeue: " + RQ.dequeue());
        //System.out.println("queueArr's length= " + RQ.len());
        //RQ.enqueue(5);
        //System.out.println("queueArr's length= " + RQ.len());
        //RQ.enqueue(6);
        //System.out.println("queueArr's length= " + RQ.len());
        //RQ.enqueue(7);
        //RQ.enqueue(6);
        //RQ.enqueue(7);
        System.out.println(RQ.isEmpty());
        Iterator iterator = RQ.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
