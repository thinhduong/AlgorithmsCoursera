import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[args.length - 1]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        for(int i=0; i< k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
