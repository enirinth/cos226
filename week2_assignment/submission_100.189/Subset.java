/**
 * Created by Aaron on 7/10/15.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomStrQue = new RandomizedQueue<String>();
        double N = 1.0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (k == 0) {
                break;
            } else if (randomStrQue.size() < k) {
                randomStrQue.enqueue(s);
            } else if (StdRandom.uniform() > ((N - k) / N)) {
                randomStrQue.dequeue();
                randomStrQue.enqueue(s);
            }
            N++;
        }
        while (!randomStrQue.isEmpty()) {
            System.out.println(randomStrQue.dequeue());
        }
    }
}
